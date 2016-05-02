package com.pld.velociraptor.service;

import android.content.Context;
import android.os.AsyncTask;

import com.pld.velociraptor.tools.RestClient;
import com.pld.velociraptor.tools.VeloTokenCredentials;

import java.lang.ref.WeakReference;

/**
 * Created by a607937 on 09/06/2015.
 */
public class LoginUserAsyncTask extends AsyncTask<String, Void, VeloTokenCredentials> {

    public static final String TAG = "LoginUserAsyncTask";

    protected RestClient client;


    private WeakReference<UserLoggedCallBack> mCallBack;
    private Context context;
    private Exception pendingException;

    public LoginUserAsyncTask(RestClient client, Context context, UserLoggedCallBack callBack) {
        mCallBack = new WeakReference<UserLoggedCallBack>(callBack);
        this.context = context;
        this.client = client;
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
    protected VeloTokenCredentials doInBackground(String... credentials) {

        VeloTokenCredentials result = null;

        try{
            result = client.loginUser(credentials[0], credentials[1]);
        }catch(Exception e){
            pendingException = e;

        }



        return result;
    }

    @Override
    protected void onPostExecute(VeloTokenCredentials result) {

        UserLoggedCallBack callBack  = mCallBack.get();
        if (mCallBack == null) {
            return;
        }
        if(pendingException != null){
            callBack.loginError(pendingException);
            return;
        }
        callBack.userLogged(result);

    }


}