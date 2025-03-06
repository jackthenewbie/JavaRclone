package com.jvrclone;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.Strictness;
import com.google.gson.stream.JsonReader;
import com.jvrclone.ResHandler.*;
/**
 * Hello world!
 *
 */
public class JavaRclone {
    
    private HashMap<Class<?>, ResHandler> resHandlers = new HashMap<>();
    private Ls ls;
    private String url;
    public JavaRclone(String url) throws Exception {
        this.url = url;
    }
    public void InitLS() throws Exception {
        this.ls = new Ls(url);
        resHandlers.put(this.ls.getClass(), this.ls.getResHandler());
    }
    /**
     * List the remote at first level
     * 
     * @param remote Remote name must include the colon
     * @return result of the list at first level
     *
     */
    public String ls(String remote) throws Exception {
        if(this.ls == null){
            this.ls = new Ls(url);
            resHandlers.put(this.ls.getClass(), this.ls.getResHandler());
        }
        Map<String, String> params = Map.of(
                "fs", remote,
                "opt.filesOnly", "false",
                "remote", "");
        resHandlers.put(this.ls.getClass(), this.ls.getResHandler());
        return this.ls.result(remote, params);
    }
    /**
     * Supplement your params under this built:
     * <blockquote>
     * 
     * <pre>
     * Map&lt;String, String&gt; params = Map.of(
     *         "fs", "drive:",
     *         "opt.filesOnly", "false",
     *         "remote", "");
     * </pre>
     * </blockquote>
     * <p>
     * 
     * <a href="https://rclone.org/rc/#operations-list">Rclone API documentation operations/list</a>
     * @param remote Remote name must include the colon
     * @param params Map of parameters, check docs for additional parameters
     * @return
     * @throws Exception
     */
    public String ls(String remote, Map<String, String> params) throws Exception {
        if(this.ls == null){
            this.ls = new Ls(url);
            resHandlers.put(this.ls.getClass(), this.ls.getResHandler());
        }
            
        
        return this.ls.result(remote, params);
    }
    public void setConnection(HttpURLConnection connect, Class<?> operation){
        ResHandler resHandler = resHandlers.get(operation);
        resHandler.connect = connect;
    }
    public HttpURLConnection getConnection(Class<?> operation){
        ResHandler resHandler = resHandlers.get(operation);
        return resHandler.connect;
    }
    public void enableHTTPAuth(String user, String pass, Class<?> operation){
        ResHandler resHandler = resHandlers.get(operation);
        resHandler.auth = Base64.getEncoder()
                .encodeToString((user + ":" + pass).getBytes(StandardCharsets.UTF_8)); // Java 8
    }
}
