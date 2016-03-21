package com.epam.spring.core.util;

import com.epam.spring.core.model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Sergiy_Dakhniy
 */
public class JsonUtils {

    private static ObjectMapper mapper = new ObjectMapper();
    static {
        mapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
    }

    public static JsonNode parseFile(MultipartFile multipart) {
        JsonNode json = null;
        try(ByteArrayInputStream stream = new ByteArrayInputStream(multipart.getBytes())) {
            String jsonString = IOUtils.toString(stream, "UTF-8");
            json = mapper.readTree(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    public static <T> T getEntitiesArray(JsonNode node, Class<T> arrayType) throws IOException{
        return mapper.treeToValue(node, arrayType);
    }


}
