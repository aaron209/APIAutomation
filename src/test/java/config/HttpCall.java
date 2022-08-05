package config;

import java.util.HashMap;
import java.util.Map;

public class HttpCall {

    public static String setUrl(String applicationName, String region){
        Map<String, String> url = new HashMap<>();
        url.put("", "https:");
        //for different environment
        url.put("", "");
        return url.get(applicationName+"-"+region);
    }
    public static String setUri(String apiPath){
        Map<String, String> uri = new HashMap<>();
        uri.put("API","put here resource url");
        return uri.get(apiPath);
    }
}
