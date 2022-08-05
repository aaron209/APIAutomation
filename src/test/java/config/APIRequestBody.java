package config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class APIRequestBody {

    //Attributes for request body with nested attributes
    @JsonProperty("fromAccount")
    APIDetails fromAccount;
    @JsonProperty("toAccount")
    APIDetails toAccount;
    //Attributes without nested attributes
    @JsonProperty("abaId")
    String abaId;

    private Map<String, Object> data;

    public APIRequestBody(Map<String, Object> finalTestData){
        data = finalTestData;
    }

    public String getAbaId(){
        abaId = convertToString(data.get("abaId"));
        return abaId;
    }

    public void setAbaId(String abaId){
        this.abaId = abaId;
    }

    //nested attributes
    public APIDetails getFromAccount() {
        APIDetails from = new APIDetails();
        from.setAccountId(convertToString(data.get("fromAccountId")));
        from.setAffiliateCode(convertToString(data.get("fromAffiliateCode")));
        return from;
    }

    public APIDetails getToAccount() {
        APIDetails to = new APIDetails();
        to.setAccountId(convertToString(data.get("toAccountId")));
        return to;
    }
    private class APIDetails{

        //Attributes under main attributes
        @JsonProperty("accountId")
        String accountId;
        @JsonProperty("affiliateCode")
        String affiliateCode;

        public APIDetails(){

        }

        //Nested attributes
        public APIDetails(String accountId, String affiliateCode){
            this.accountId = accountId;
            this.affiliateCode = affiliateCode;
        }

        public String getAccountId(){
            return  accountId;
        }

        public void setAccountId(String accountId){
            this.accountId = accountId;
        }

        public String getAffiliateCode(){
            return  affiliateCode;
        }

        public void setAffiliateCode(String affiliateCode){
            this.affiliateCode = affiliateCode;
        }
    }

    public static String setRequestBody(Map<String, Object> finalTestData){
        APIRequestBody apiRequestBody = null;
        String requestBody = null;
        try {
            apiRequestBody = new APIRequestBody(finalTestData);
            ObjectMapper mapper = new ObjectMapper();
            requestBody = mapper.writeValueAsString(apiRequestBody);
        }catch(Exception e){
            e.printStackTrace();

        }
        return requestBody;
    }

    private String convertToString(Object obj){
        String convert = String.valueOf(obj);
        if(convert == "null")
            return null;
        return convert;
    }
}
