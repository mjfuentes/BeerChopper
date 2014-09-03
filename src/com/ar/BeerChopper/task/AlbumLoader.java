package com.ar.BeerChopper.task;

import android.os.AsyncTask;

import com.ar.BeerChopper.activity.LoginActivity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Matias_2 on 17/06/13.
 */
public class AlbumLoader extends AsyncTask<String,Object,String> {
    private final LoginActivity context;


    public AlbumLoader(LoginActivity con)
    {
        context = con;
    }

    @Override
    protected void onPostExecute(String o) {
        context.setImages(o);
    }

    @Override
    protected String doInBackground(String... params) {
        try {

            HttpClient client = new DefaultHttpClient();
            HttpGet get = new HttpGet("https://api.imgur.com/3/album/" + params[0]+".json");
            get.setHeader("Authorization","Client-ID 3611f0a157828a3");
            HttpResponse resp = client.execute(get);
            HttpEntity entity = resp.getEntity();
            InputStream inputStream = entity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");
            }
            String result = sb.toString();
            return result;

        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

}
