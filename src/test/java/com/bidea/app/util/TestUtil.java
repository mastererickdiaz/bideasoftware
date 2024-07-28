package com.bidea.app.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class TestUtil {

    private static final ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
    }

    public static boolean isJSONValid(String jsonInString) {
        try {
            mapper.readTree(jsonInString);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static String serializeAsJsonString(Object object)
            throws JsonGenerationException, JsonMappingException, IOException {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        StringWriter sw = new StringWriter();
        mapper.writeValue(sw, object);
        return sw.toString();
    }

    public static String serializeAsJsonString(Object object, boolean indent)
            throws JsonGenerationException, JsonMappingException, IOException {
        if (indent == true) {
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        }

        StringWriter stringWriter = new StringWriter();
        mapper.writeValue(stringWriter, object);
        return stringWriter.toString();
    }

    public static <T> T jsonStringToObject(String fileName, Class<T> clazz)
            throws IOException {

              mapper.findAndRegisterModules()
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        T obj = null;
        ClassLoader classLoader = TestUtil.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);
        obj = mapper.readValue(inputStream, clazz);
        return obj;
    }

    public static <T> T jsonStringToObjectArray(String fileName)
            throws JsonParseException, JsonMappingException, IOException {
        T obj = null;
        ClassLoader classLoader = TestUtil.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);
        obj = mapper.readValue(inputStream, new TypeReference<T>() {
        });
        return obj;
    }

    public static <T> T jsonStringToObjectArray(String fileName, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {
        T obj = null;
        ObjectMapper mapper = new ObjectMapper();
        mapper = new ObjectMapper().configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

        ClassLoader classLoader = TestUtil.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);
        obj = mapper.readValue(inputStream, mapper.getTypeFactory().constructCollectionType(List.class, clazz));
        return obj;
    }

    public static <T> List<T> jsonArrayToObjectList(String fileName, Class<T> clazz) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ClassLoader classLoader = TestUtil.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);
        List<T> ts = mapper.readValue(inputStream, new TypeReference<List<T>>() {
        });
        return ts;
    }

}
