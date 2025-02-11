package com.jvrclone;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Hello world!
 *
 */
public class JavaRclone {
    public void copy(String target) throws Exception {
        URL url = new URL(target);
        HttpURLConnection connect = (HttpURLConnection) url.openConnection();
        connect.setRequestMethod("POST");
        connect.setUseCaches(false);
        connect.setDoOutput(true);
        //connect.setRequestProperty("_name", "alocal");
        connect.setRequestProperty("operation/list", "\'alocal:\'");
        InputStream is = connect.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
        String line;
        while ((line = rd.readLine()) != null) {
            response.append(line);
            response.append('\r');
        }
        rd.close();
        System.out.println(response.toString());
    }
}
