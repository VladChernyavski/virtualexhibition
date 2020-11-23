package by.cniitu.virtualexhibition.util;

import by.cniitu.virtualexhibition.to.websocket.UserTo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String getJsonString(Object object){
        String jsonString = null;
        try {
            jsonString = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
        return jsonString;
    }

    public static UserTo getUserToByJson(String jsonString){
        try {
            return objectMapper.readValue(jsonString, UserTo.class);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

}
