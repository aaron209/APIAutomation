package config;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class CreateRecordRequestBody {

    @JsonProperty("name")
    private String name;

    @JsonProperty("salary")
    private String salary;

    @JsonProperty("age")
    private String age;

    private Map<String, Object> data;

    public CreateRecordRequestBody(Map<String, Object> finalTestData) {
        data = finalTestData;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name = convertToString(data.get("name"));
    }

    public void setJob(String salary) {
        this.salary = salary;
    }

    public String getJob() {
        return salary = convertToString(data.get("salary"));
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAge() {
        return age = convertToString(data.get("age"));
    }

    public static String setRequestBody(Map<String, Object> finalTestData) {
        CreateRecordRequestBody body = null;
        String requestBody = null;
        try {
            body = new CreateRecordRequestBody(finalTestData);
            ObjectMapper objectMapper = new ObjectMapper();
            requestBody = objectMapper.writeValueAsString(body);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return requestBody;
    }

    private String convertToString(Object object) {
        String convert = String.valueOf(object);
        if (convert == null)
            return null;
        return convert;
    }

}
