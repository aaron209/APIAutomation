package steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import config.HttpCall;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import library.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.testng.Assert;
import report.ReportGenerator;
import report.ScenarioContext;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class RegressionSteps {

    private String file, scenario;
    private  String token;
    private YamlHelper yamlHelper;
    int actualHttpStatusCode;
    private WebClient.ResponseSpec responseSpec;
    private RequestHeader requestHeader;
    private HttpHeaders responseHeader;
    private ObjectMapper mapper = new ObjectMapper();
    private RequestBody _requestBody = new RequestBody();
    private TokenGeneration tokenGeneration;


    // following is used to create Report
    public static ReportGenerator report = new ReportGenerator();
    public static ScenarioContext scenarioContext;
    private Map<String, String> _requestHeaders;
    private String requestBody;
    private Map<String, String> responseHeaders;
    private String responseBody;


    @Given("Test {int} for {string}")
    public void test_for(int testNo, String scenario) {
        String _testNo = null;
        String _testScenario = null;
        String _reportTitle = null;
        String _reportType = null;
        String _timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String _scenarioName = null;

        try {
            scenarioContext = new ScenarioContext();
            _requestHeaders = new LinkedHashMap<>();
            responseHeaders = new LinkedHashMap<>();

            String[] scenarios = scenario.split(":");
            System.out.println("ran the test");
            file = scenarios[0].trim();
            yamlHelper = new YamlHelper(file);
            DataGroup.initialAllTestData = yamlHelper.getTestData();
            DataGroup.currentTestData = DataGroup.initialAllTestData.get(file).get(testNo);
            scenario = (String) DataGroup.currentTestData.get("scenario");

            _testNo = "" + testNo;
            _testScenario = file + ": " + scenario;
            _reportTitle = file;
            _reportType = file;
            _scenarioName = scenario;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            report.reportTitle = _reportTitle;
            report.reportType = _reportType;
            report.timeStamp = _timeStamp;

            scenarioContext.testId = _testNo;
            scenarioContext.scenarioName = _scenarioName;
            scenarioContext.addScenarioLines(String.format("Test %s For %s", _testNo, _testScenario));

        }

    }
    @And("The customer logs into app with userName {string} and password {string}")
    public void the_customer_logs_into_app_with_username_and_password(String userName, String password){
        String _userName = null;
        String _password = "xxxxxxxxxx";

        try {
            userName = (String) DataGroup.currentTestData.get("userLogin");
            password = (String) DataGroup.currentTestData.get("password");
            tokenGeneration = new TokenGeneration();
            token = tokenGeneration.getToken(userName, password);

            _userName = userName;
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            scenarioContext.addScenarioLines(String.format("The customer logs into app with userName %s and password %s", _userName, _password));
        }

    }
    @When("The customer calls {string} and provides {string} as wells as {string}")
    public void the_customer_calls_and_provides_as_well_as(String applicationName, String fromAccount, String toAccount) {
        String _applicationName = null;
        String _fromAccountId = null;
        String _toAccountId = null;
        try {
            yamlHelper = new YamlHelper(applicationName);
            DataGroup.applicationDefaultTestData = yamlHelper.getDefaultData().get(applicationName);
            //if any token
           // DataGroup.applicationDefaultTestData.put("Header_Authorization", token);
            DataGroup.applicationDefaultTestData.put("applicationName", applicationName);
            DataGroup.finalTestData = yamlHelper.getFinalTestData(DataGroup.applicationDefaultTestData, DataGroup.currentTestData);
            _applicationName = applicationName;
            _fromAccountId = String.valueOf(DataGroup.finalTestData.get(fromAccount));
            _toAccountId = String.valueOf(DataGroup.finalTestData.get(toAccount));
        } catch (Exception e) {
            scenarioContext.setScenarioPassed(false);
            e.printStackTrace();
        } finally {
            scenarioContext.addScenarioLines(String.format("The customer calls %s and provides %s as well as %s", _applicationName, _fromAccountId, _toAccountId));
        }
    }

    @When("The customer uses {string} API in {string} region by {string} method in {string} channel")
    public void the_customer_uses_api_in_region_by_method_in_channel(String apiPath, String region, String method, String channel) {
        String _apiPath = null;
        String _region = null;
        String _method = null;
        String _channel = null;
        String body = null;
        Consumer<HttpHeaders> headers = null;
        String url = null;
        String uri = null;
        String finalUri = null;
        //for query path params
        //String transferType = null;
        //String transferDirection = null;

        try {
            DataGroup.finalTestData.put("Header-client-channel", channel);

            //set request body if it is post request
            body = _requestBody.setRequestBody(apiPath, DataGroup.finalTestData);
            headers = requestHeader.setRequestHeader(DataGroup.finalTestData);
            url = HttpCall.setUrl((String) DataGroup.finalTestData.get("applicationName"), region);
            uri = HttpCall.setUri(apiPath);
           // transferType = String.valueOf(DataGroup.finalTestData.get("transferType"));
           // transferDirection = String.valueOf(DataGroup.finalTestData.get("transferDirection"));
           // finalUri = uri + "?filter[transferType]=" + transferType + "&filter[transferDirection]=" + transferDirection;
            WebClient webClient = WebClient.create(url);
            responseSpec = webClient.method(HttpMethod.resolve(method))
                    .uri(uri) // use finalUri if query param
                    .headers(headers)
                    .retrieve();
            _apiPath = apiPath;
            _region = region;
            _method = method;
            _channel = channel;
            requestBody = body;
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            scenarioContext.setUrl(url + "/" + uri);
            scenarioContext.setMethod(_method);
            scenarioContext.setRequestBody(requestBody);
            scenarioContext.setRequestHeaders(RequestHeader.getHeaderAsMap());
            scenarioContext.addScenarioLines(String.format("The Customer uses %s API in %s region by %s method in %s channel", _apiPath, _region, _method, _channel));

        }
    }

    @Then("The customer validates API response code as {string}")
    public void the_customer_validates_api_response_code_as(String responseCode) {
        String _expectedStatusCode = null;
        String _actualStatusCode = null;
        Integer expectedStatusCode = (Integer) DataGroup.finalTestData.get(responseCode);
        try {
            ResponseEntity<String> entity = responseSpec.toEntity(String.class).block();
            responseHeader = entity.getHeaders();
            responseBody = entity.getBody();
            actualHttpStatusCode = entity.getStatusCode().value();
            _actualStatusCode = "" + actualHttpStatusCode;

            if (!String.valueOf(expectedStatusCode).equalsIgnoreCase(String.valueOf(actualHttpStatusCode))) {
                scenarioContext.setErrorMessage("HttpCode error: expected " + expectedStatusCode + ", but actual code " + actualHttpStatusCode);
            }
        } catch (WebClientResponseException e) {
            actualHttpStatusCode = e.getStatusCode().value();
            responseHeader = e.getHeaders() != null ? e.getHeaders() : null;
            responseBody = e.getResponseBodyAsString() != null ? e.getResponseBodyAsString() : null;
            _actualStatusCode = "" + actualHttpStatusCode;
            if (!String.valueOf(expectedStatusCode).equalsIgnoreCase(String.valueOf(actualHttpStatusCode))) {
                scenarioContext.setErrorMessage("HttpCode error: expected " + expectedStatusCode + ", but actual code " + actualHttpStatusCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (responseHeader != null) {
                for (Map.Entry<String, List<String>> each : responseHeader.entrySet()) {
                    responseHeaders.put(each.getKey(), each.getValue().get(0));
                }
            }
            scenarioContext.setResponseHeaders(responseHeaders);
            scenarioContext.setResponseBody(responseBody);
            scenarioContext.setActualHttpCode(_actualStatusCode);
            _expectedStatusCode = String.valueOf(expectedStatusCode);
            scenarioContext.addScenarioLines(String.format("The customer validate API response code as %s", _expectedStatusCode));
        }
    }

    @Then("The customer validates {string} and value is {string} in response body")
    public void the_customer_validates_and_value_is_in_response_body(String validateField, String expectedValue) {
        if (actualHttpStatusCode == 500)
            return;
        String _validateField = null;
        String _expectedField = null;
        try {
            _validateField = validateField = String.valueOf(DataGroup.finalTestData.get(validateField));
            _expectedField = expectedValue = String.valueOf(DataGroup.finalTestData.get(expectedValue));

            DocumentContext jsonContext = JsonPath.parse(responseBody);
            String actualResult = jsonContext.read(validateField).toString();
            if (!String.valueOf(expectedValue).equalsIgnoreCase((actualResult))) {
                scenarioContext.setErrorMessage("Response body validation failed : " + expectedValue + ", but actual value " + actualResult);
                Assert.assertTrue(false, "Actual value does not match");
            }
        } catch (Exception e) {
            scenarioContext.setErrorMessage("JsonPath.parse error: " + e.getMessage());
        } finally {
            scenarioContext.addScenarioLines(String.format("The customer validates <span style=\"color: #000080;\"><strong>%s</strong></span> and value is <span style=\"color: #993300;\"><strong>%s</strong></span> in response body", _validateField, _expectedField));

        }
    }

    @And("The customer validates {string} and value is {string} in response headers")
    public void the_customer_validates_and_value_is_in_response_headers(String validateHeaderField, String expectedHeaderValue) {
        if (actualHttpStatusCode == 500)
            return;

        String _validateHeaderField = null;
        String _expectedHeaderValue = null;
        try {
            validateHeaderField = String.valueOf(DataGroup.finalTestData.get(validateHeaderField));
            expectedHeaderValue = String.valueOf(DataGroup.finalTestData.get(expectedHeaderValue));
            String actualHeaderValue = responseHeader.get(validateHeaderField).get(0);
            _validateHeaderField = validateHeaderField;
            _expectedHeaderValue = expectedHeaderValue;

            if (!String.valueOf(expectedHeaderValue).equalsIgnoreCase(String.valueOf(actualHeaderValue))) {
                scenarioContext.setErrorMessage("Response header validation failed: expected value " + expectedHeaderValue + ", but actual value " + actualHeaderValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scenarioContext.addScenarioLines(String.format("The customer validates <span style=\"color: #000080;\"><strong>%s</strong></span> and value is <span style=\"color: #993300;\"><strong>%s</strong></span> in response header", validateHeaderField, expectedHeaderValue));
        }
    }

}



