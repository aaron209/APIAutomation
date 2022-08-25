package library;

import config.APIRequestBody;
import config.CreateRecordRequestBody;
import org.apiguardian.api.API;

import java.util.Map;

// this class is required when the http method is post
public class RequestBody {

    public String setRequestBody(String requestBodyClassName, Map<String, Object> finalTestData){
        if(requestBodyClassName.equalsIgnoreCase("API")){
            return APIRequestBody.setRequestBody(finalTestData);
        }else if(
            requestBodyClassName.equalsIgnoreCase("CreateRecord")){
            return CreateRecordRequestBody.setRequestBody(finalTestData);
        }
        return null;
    }
}
