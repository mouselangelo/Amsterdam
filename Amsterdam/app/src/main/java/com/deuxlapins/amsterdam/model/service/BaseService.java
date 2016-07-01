package com.deuxlapins.amsterdam.model.service;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A basic HTTP service to retrieve contents from a url (as String)
 * Created by chetan on 27/06/16.
 */
public class BaseService {

    private static final String DEBUG_TAG = "BaseService";

    private String url;

    /**
     *
     * @param url
     */
    public BaseService(String url) {
        this.url = url;
    }

    /**
     *
     * @return
     */
    protected String loadDataAsString() throws ServiceException {
        try {
            return downloadURL();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceException("Download failed.");
        }
    }


    private String downloadURL() throws IOException {
        InputStream inputStream = null;
        try {

            URL targetURL = new URL(this.url);

            HttpURLConnection connection = (HttpURLConnection) targetURL.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int responseCode = connection.getResponseCode();
            Log.d(DEBUG_TAG, "HTTP Response Code : "+responseCode);

            inputStream = connection.getInputStream();

            String content = readStream(inputStream);

            Log.d(DEBUG_TAG, "Result : "+content);

            return content;

        } finally {
            try {
                inputStream.close();
            } catch (NullPointerException e) {
                e.printStackTrace();
                // ignore
            }
        }
    }

    private String readStream(InputStream stream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
        StringBuilder result = new StringBuilder();
        String line;
        while( (line = reader.readLine()) != null) {
            result.append(line);
        }
        reader.close();
        return result.toString();
    }

}
