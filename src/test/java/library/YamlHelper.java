package library;

import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class YamlHelper {

    private Map<String, Map<String, Map<String, Object>>> initialTestData = null;
    private Map<String, Map<String, Object>> defaultData = null;
    private String fileName;

    public YamlHelper(String file){

        fileName = "src/test/java/testData/"+file+".yaml";
    }
    public Map<String, Map<String, Map<String, Object>>> getTestData() throws Exception{
        Yaml yaml = new Yaml();
        InputStream inputStream = new FileInputStream(fileName);
        initialTestData = yaml.load(inputStream);
        return initialTestData;
    }

    public Map<String, Map<String, Object>> getDefaultData() throws FileNotFoundException {
        Yaml yaml = new Yaml();
        InputStream inputStream = new FileInputStream(fileName);
        defaultData = yaml.load(inputStream);
        return defaultData;

    }
    public Map<String, Object> getFinalTestData(Map<String, Object> applicationDefaultTestData, Map<String, Object> currentTestData){
        Map<String, Object> finalTestMap = new HashMap<>();
        for(Map.Entry<String,Object> current : currentTestData.entrySet()){
            boolean uniqueKey = true;
            for(Map.Entry<String, Object> _default : applicationDefaultTestData.entrySet()){
                if(current.getKey().equals(_default.getKey())){
                    _default.setValue(current.getValue());
                    finalTestMap.put(_default.getKey(), _default.getValue());
                    uniqueKey = false;
                    break;
                }else{
                    finalTestMap.put(_default.getKey(), _default.getValue());
                    if(uniqueKey){
                        finalTestMap.put(current.getKey(), current.getValue());
                    }
                }
            }
        }
       return finalTestMap;
    }
}
