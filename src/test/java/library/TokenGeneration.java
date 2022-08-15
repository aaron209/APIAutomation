package library;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class TokenGeneration {

    public String getToken( String userLogin, String password){
        String token = null;
        token = getCustomerToken(userLogin, password);

        return token;
    }
    public String getCustomerToken(String userLogin, String password){
        String url = "https://...........com";
        String uri = "/GenerateUserToken?user="+userLogin+"&password="+password;

        String customerToken = null;
        customerToken = getLongLiveToken(userLogin);

        if(customerToken == null){
            customerToken = callApiToken(url, uri);
        }
        return  customerToken;
    }

    private String callApiToken(String url, String uri) {
        String customerToken = null;
        try{
            WebClient webClient = WebClient.builder().baseUrl(url).build();

            Mono<String> response = webClient.get().uri(uri).retrieve().bodyToMono(String.class);

            customerToken = response.block();
        }catch (Exception e){
            customerToken = null;
        }
        return  customerToken;
    }

    private String getLongLiveToken(String userLogin) {
        YamlHelper yamlHelper = new YamlHelper("LongLiveToken");
        String longLiveToken = null;
        try {
            longLiveToken = (String) yamlHelper.getTestData().get("Token").get(userLogin).get("LongLiveToken");
        }catch (Exception e){
            longLiveToken = null;
        }
        return  longLiveToken;
    }
}
