package com.example.gcoaquira.aplicacionuptbus;

import android.os.AsyncTask;

import com.example.gcoaquira.aplicacionuptbus.utils.Config;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class MiUbicacionRequest extends AsyncTask<String, String, String> {

    @Override
    protected String doInBackground(String... uri) {
        try {
            URL url = new URL(Config.servidor_api + "buses_conductors/" + uri[0]);
            HttpURLConnection client = (HttpURLConnection) url.openConnection();
            client.setRequestMethod("PUT");
            client.setDoOutput(true);
            client.setDoInput(true);
            DataOutputStream wr = new DataOutputStream(client.getOutputStream());

            HashMap<String, String> postDataParams = new HashMap<>();
            postDataParams.put("lat", uri[1]);
            postDataParams.put("lng", uri[2]);
            StringBuilder result = new StringBuilder();
            boolean first = true;
            for (Map.Entry<String, String> entry : postDataParams.entrySet()) {
                if (first)
                    first = false;
                else
                    result.append("&");
                result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }
            wr.write(result.toString().getBytes());
            BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null)
                sb.append(line);
            return sb.toString();
        } catch (Exception c) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        try {
            JSONObject obj = new JSONObject(result);
            int success = obj.getInt("success");

            /*
            if(status == 200)
                eventBus.post(new LoginResultEvent(obj.getInt("status"), obj.getString("user"), obj.getString("token"), obj.getString("travelTypes")));
            else
                eventBus.post(new LoginResultEvent(status,obj.getString("error")));
            */
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
