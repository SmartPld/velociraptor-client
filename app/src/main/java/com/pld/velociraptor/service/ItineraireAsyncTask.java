package com.pld.velociraptor.service;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.pld.velociraptor.model.Pos;
import com.pld.velociraptor.model.Station;
import com.pld.velociraptor.model.Trip;
import com.pld.velociraptor.tools.RestClient;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import retrofit.RestAdapter;

public class ItineraireAsyncTask extends AsyncTask<Trip, Integer, Boolean> {
    private static final String TOAST_MSG = "Calcul de l'itinéraire en cours";
    private static final String TOAST_ERR_MAJ = "Impossible de trouver un itinéraire";

    private Context context;
    private GoogleMap gMap;
    private RestClient restClient;
    private TripDrawnCallBack callBack;
    private String editDepart;
    private String editArrivee;
    private final ArrayList<LatLng> lstLatLng = new ArrayList<LatLng>();


    public ItineraireAsyncTask(RestClient restClient, Context context, TripDrawnCallBack callBack, GoogleMap gMap) {
        this.context = context;
        this.gMap= gMap;
        this.callBack = callBack;
        this.restClient = restClient;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onPreExecute() {
        Toast.makeText(context, TOAST_MSG, Toast.LENGTH_LONG).show();
    }

    @Override
    protected Boolean doInBackground(Trip... params) {

        Trip trip = params[0];

        Pos depart = trip.getStation_start().getPos();
        Pos arrivee = trip.getStation_end().getPos();


        editDepart = depart.getLat()+","+depart.getLng();
        editArrivee = arrivee.getLat()+","+arrivee.getLng();

        try {
            //Construction de l'url à appeler
            final StringBuilder url = new StringBuilder("http://maps.googleapis.com/maps/api/directions/xml?sensor=false&language=fr");
            url.append("&origin=");
            url.append(editDepart.replace(' ', '+'));
            url.append("&destination=");
            url.append(editArrivee.replace(' ', '+'));

            //Appel du web service
            final InputStream stream = new URL(url.toString()).openStream();

            //Traitement des données
            final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactory.setIgnoringComments(true);

            final DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            final Document document = documentBuilder.parse(stream);
            document.getDocumentElement().normalize();

            //On récupère d'abord le status de la requête
            final String status = document.getElementsByTagName("status").item(0).getTextContent();
            if(!"OK".equals(status)) {
                return false;
            }

            //On récupère les steps
            final Element elementLeg = (Element) document.getElementsByTagName("leg").item(0);
            final NodeList nodeListStep = elementLeg.getElementsByTagName("step");
            final int length = nodeListStep.getLength();

            for(int i=0; i<length; i++) {
                final Node nodeStep = nodeListStep.item(i);

                if(nodeStep.getNodeType() == Node.ELEMENT_NODE) {
                    final Element elementStep = (Element) nodeStep;

                    //On décode les points du XML
                    decodePolylines(elementStep.getElementsByTagName("points").item(0).getTextContent());
                }
            }

            return true;
        }
        catch(final Exception e) {
            return false;
        }
    }


    @Override
    protected void onPostExecute(final Boolean result) {
        if(!result) {
            Toast.makeText(context, TOAST_ERR_MAJ, Toast.LENGTH_SHORT).show();
        }
        else {
            //On déclare le polyline, c'est-à-dire le trait (ici bleu) que l'on ajoute sur la carte pour tracer l'itinéraire
            final PolylineOptions polylines = new PolylineOptions();
            polylines.color(Color.RED);

            //On construit le polyline
            for(final LatLng latLng : lstLatLng) {
                polylines.add(latLng);
            }

            //On déclare un marker vert que l'on placera sur le départ
            final MarkerOptions markerA = new MarkerOptions();
            markerA.position(lstLatLng.get(0));
            markerA.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

            //On déclare un marker rouge que l'on mettra sur l'arrivée
            final MarkerOptions markerB = new MarkerOptions();
            markerB.position(lstLatLng.get(lstLatLng.size()-1));
            markerB.icon(BitmapDescriptorFactory.defaultMarker(100));

            //On met à jour la carte
            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lstLatLng.get(0), 10));
            gMap.addMarker(markerA);
            gMap.addPolyline(polylines);
            gMap.addMarker(markerB);
        }
    }


    private void decodePolylines(final String encodedPoints) {
        int index = 0;
        int lat = 0, lng = 0;

        while (index < encodedPoints.length()) {
            int b, shift = 0, result = 0;

            do {
                b = encodedPoints.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);

            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;
            shift = 0;
            result = 0;

            do {
                b = encodedPoints.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);

            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            lstLatLng.add(new LatLng((double)lat/1E5, (double)lng/1E5));
        }
    }

}