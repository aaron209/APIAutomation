package report;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public class ScenarioContext {
    public String testId;
    public String scenarioName;
    public boolean scenarioPassed;
    public LinkedList<String> scenarioLines;
    public String method;
    public String url;
    public Map<String, String> requestHeaders;
    public String requestBody;
    public Map<String, String> responseHeaders;
    public String responseBody;
    public String actualHttpCode;
    public String errorMessage;

    public String getActualHttpCode(){
        return actualHttpCode;
    }
    public void setActualHttpCode(String actualHttpCode){
        this.actualHttpCode = actualHttpCode;
    }

    public String getErrorMessage(){
        return errorMessage;
    }

    public String setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return errorMessage;
    }
    public ScenarioContext(){
        scenarioLines = new LinkedList<>();
        requestHeaders = new LinkedHashMap<>();
        responseHeaders = new LinkedHashMap<>();
    }

    public String getTestId(){
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getScenarioName() {
        return scenarioName;
    }

    public void setScenarioName(String scenarioName) {
        this.scenarioName = scenarioName;
    }

    public boolean isScenarioPassed(){
        return scenarioPassed;
    }

    public void setScenarioPassed(boolean scenarioPassed){
        this.scenarioPassed = scenarioPassed;
    }

    public LinkedList<String> getScenarioLines(){
        return scenarioLines;
    }

    public void addScenarioLines(String syntaxLine){
        this.scenarioLines.add(syntaxLine);
    }

    public String getMethod(){
        return method;
    }

    public void setMethod(String method){
        this.method= method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public Map<String, String> getRequestHeaders(){
        return requestHeaders;
    }

    public void setRequestHeaders(Map<String, String> requestHeaders){
        this.requestHeaders = requestHeaders;
    }

    public String getRequestBody(){
        return requestBody;
    }

    public void setRequestBody(String requestBody){
        this.requestBody = requestBody;
    }

    public Map<String, String> getResponseHeaders(){
        return responseHeaders;
    }

    public void setResponseHeaders(Map<String , String > responseHeaders){
        this.responseHeaders = responseHeaders;
    }

    public String getResponseBody(){
        return responseBody;
    }

    public void setResponseBody(String responseBody){
        this.responseBody = responseBody;
    }

    public ScenarioContext(String testId, String scenarioName, boolean scenarioPassed, LinkedList<String> scenarioLines, String method, String url, Map<String, String> requestHeaders, String requestBody, Map<String, String> responseHeaders, String responseBody){
        this.testId = testId;
        this.scenarioName = scenarioName;
        this.scenarioPassed = scenarioPassed;
        this.scenarioLines = scenarioLines;
        this.method = method;
        this.url = url;
        this.requestHeaders = requestHeaders;
        this.requestBody = requestBody;
        this.responseHeaders = responseHeaders;
        this.responseBody = responseBody;
    }

}
