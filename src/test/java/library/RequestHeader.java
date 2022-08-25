package library;

import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public class RequestHeader {

    private static Map<String, String> headersMap = null;
    public static Consumer<HttpHeaders> setRequestHeader(Map<String, Object> finalTestData){
        headersMap= new LinkedHashMap<>();
        Map<String, String> headers = new HashMap<>();
        String correlationID =null;
        MultiValueMap<String, String> values = new HttpHeaders();
        for(Map.Entry<String, Object> header : finalTestData.entrySet()) {
            String key = header.getKey();
            Object value = header.getValue();
            String updateKey = null;
            if (key.startsWith("Header_")) {
                updateKey = key.replace("Header_", "");
//                if (updateKey.equals("correlId")) {
//                    correlationID = UUID.randomUUID().toString();
//                    value = correlationID;
//                }
                headers.put(updateKey, String.valueOf(value));
            }
        }
        //headers.put("correlId", correlationID);
        headersMap.putAll(headers);
        values.setAll(headers);
        Consumer<HttpHeaders> consumer = it -> it.addAll(values);
        return consumer;
    }
    public static Map<String, String> getHeaderAsMap(){
        return headersMap;
    }

}
