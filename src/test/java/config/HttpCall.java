package config;

import java.util.HashMap;
import java.util.Map;

public class HttpCall {

    public static String setUrl(String applicationName, String region){
        Map<String, String> url = new HashMap<>();
        url.put("CreateRecord-QA", "https://reqres.in");
        //for different environment
       // url.put("", "");
        return url.get(applicationName+"-"+region);
    }
    public static String setUri(String apiPath){
        Map<String, String> uri = new HashMap<>();
        uri.put("API","put here resource url");
        uri.put("CreateRecord","api/users");
        return uri.get(apiPath);
    }
}
