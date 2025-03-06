package com.jvrclone;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.Strictness;
import com.google.gson.stream.JsonReader;

public class ResHandler {
    protected String url;
    protected String requestBody;
    protected HttpURLConnection connect;
    protected String auth = "";
    public ResHandler(String url, String mode) throws Exception {
        this.url = url;
        this.connect = (HttpURLConnection) (new URI(this.url.toString() + mode)).toURL().openConnection();
    }
    public String printPretty(JsonElement jsonElement) 
    {
        return new GsonBuilder().setPrettyPrinting().create().toJson(jsonElement);
    }
    public String getEncodeParams(Map<String, String> params) 
    {
        return params.entrySet().stream().map
        (
            entry -> entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8)
            )
            .collect(Collectors.joining("&"));
        }
        /*
        * Send a post request with default headers return a response formated string
        */
        public String sendPost(String mode, Map<String, String> params) throws Exception 
        {
        this.requestBody = getEncodeParams(params);
        this.connect.setRequestMethod("POST");
        // connect.setRequestProperty("Content-Type",
        // "application/x-www-form-urlencoded");
        // connect.setRequestProperty("Accept", "application/json");
        this.connect.setUseCaches(false);
        this.connect.setDoOutput(true);
        this.connect.setRequestProperty("Authorization", "Basic " + this.auth);
        OutputStream os = this.connect.getOutputStream();
        os.write(requestBody.getBytes());
        JsonElement myRes = getResponseString();
        String response = printPretty(myRes);
        // myRes.get("list").getAsJsonArray();
        return response;
    }
    public JsonElement getResponseString() throws Exception {
        InputStream stream;
        if (this.connect.getResponseCode() >= 400) 
        {
            stream = this.connect.getErrorStream();
        } else 
        {
            stream = this.connect.getInputStream();
        }
        BufferedReader rd = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonReader reader = new JsonReader(rd);
        reader.setStrictness(Strictness.LENIENT);
        JsonElement json = gson.fromJson(reader, JsonElement.class);
        reader.close();
        return json;
    }
}
