package com.pld.velociraptor.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thibault on 01/05/2016.
 */
public class JCDecauxResponse {
    List<String> fields;
    List<JCDecauxStation> values;

    public JCDecauxResponse(List<String> fields, List<JCDecauxStation> values) {
        this.fields = fields;
        this.values = values;
    }

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    public List<JCDecauxStation> getValues() {
        return values;
    }

    public void setValues(List<JCDecauxStation> values) {
        this.values = values;
    }

    public List<Station> getStations(){

        List<Station> stations = new ArrayList<>();
        for(JCDecauxStation jcDecauxStation : values){

            stations.add(new Station(jcDecauxStation.getNumber() == null ? 0 :Integer.parseInt(jcDecauxStation.getNumber()),
                    jcDecauxStation.getName(),
                    true,
                    jcDecauxStation.getAddress(),
                    jcDecauxStation.getAddress2(),
                    jcDecauxStation.getCommune(),
                    0,
                    false,
                    jcDecauxStation.getPole(),
                    new Pos(Double.parseDouble(jcDecauxStation.getLat()), Double.parseDouble(jcDecauxStation.getLng())),
                    jcDecauxStation.getGrid() == null ? 0 :Integer.parseInt(jcDecauxStation.getGrid()),
                    jcDecauxStation.getAltitude() == null ? 0 :Integer.parseInt(jcDecauxStation.getAltitude()),
                    jcDecauxStation.getId() == null ? 0 :Integer.parseInt(jcDecauxStation.getId()),
                    jcDecauxStation.getAvailable_bike_stands() == null ? 0 :Integer.parseInt(jcDecauxStation.getAvailable_bike_stands()),
                    jcDecauxStation.getAvailable_bikes() == null ? 0 :        Integer.parseInt(jcDecauxStation.getAvailable_bikes())));

        }

        return stations;

    }
}
