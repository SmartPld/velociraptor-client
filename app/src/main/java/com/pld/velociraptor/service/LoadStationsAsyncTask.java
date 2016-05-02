package com.pld.velociraptor.service;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.pld.velociraptor.model.Station;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by a607937 on 09/06/2015.
 */
public class LoadStationsAsyncTask extends AsyncTask<Void, Void, List<Station>> {

    public static final String TAG = "AcceptTripAsyncTask";

    private WeakReference<StationsLoadedCallBack> mCallBack;
    private Context context;
    private JCDecauxClient client;
    private Exception pendingException;

    public LoadStationsAsyncTask(JCDecauxClient jcDecauxClient, Context context, StationsLoadedCallBack callBack) {
        mCallBack = new WeakReference<>(callBack);
        this.context = context;
        this.client = jcDecauxClient;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);

    }

    @Override
    protected List<Station> doInBackground(Void... credentials) {

        List<Station> result = null;
        try{
            result = client.getStations();
        }catch(Exception e){
            pendingException = e;
        }



        return result;
    }

    @Override
    protected void onPostExecute(List<Station> result) {

        StationsLoadedCallBack callBack  = mCallBack.get();
        if (mCallBack == null) {
            return;
        }
        if(pendingException != null){

            return;
        }
        callBack.stationsLoaded(result);

    }


}