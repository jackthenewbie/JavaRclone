package com.jvrclone;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;
import com.jvrclone.ResHandler;
public class Ls {
    private ResHandler resHandler;
    private String mode = "operations/list?";
    public Ls(String url) throws Exception {
        this.resHandler = new ResHandler(url, mode);
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
    public String result(String remote, Map<String, String> params) throws Exception {
        return resHandler.sendPost(remote, params);
    }
    public ResHandler getResHandler() {
        return resHandler;
    }
}
