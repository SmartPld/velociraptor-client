package com.pld.velociraptor.tools;


import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * Created by a607937 on 09/06/2015.
 */
public class Tools {

    /**
     * This method allows to get Properties file from Assets
     *
     * @param context  context to get Assets
     * @param filename the properties filename located in Assets
     * @return the Properties object associated to the file
     * @throws IOException
     */
    public static Properties getProperties(Context context, String filename) throws IOException
    {
        InputStream inputStream = context.getAssets().open("app.properties");
        Properties properties = new Properties();
        properties.load(inputStream);
        return properties;
    }


    /**
     * Converts a string into a date
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date convertStringToDate(String date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date convertedDate = new Date();

        convertedDate = dateFormat.parse(date);


        return convertedDate;


    }

}
