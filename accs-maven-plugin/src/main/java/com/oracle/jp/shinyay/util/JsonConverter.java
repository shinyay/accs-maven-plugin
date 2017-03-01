package com.oracle.jp.shinyay.util;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.jsontype.impl.StdTypeResolverBuilder;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class JsonConverter {
    private static final ObjectMapper mapper = new  ObjectMapper();
    static {
//        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        StdTypeResolverBuilder typeResolverBuilder =
                new ObjectMapper.DefaultTypeResolverBuilder(ObjectMapper.DefaultTyping.OBJECT_AND_NON_CONCRETE);
        typeResolverBuilder.init(JsonTypeInfo.Id.CLASS, null);
        typeResolverBuilder = typeResolverBuilder.inclusion(JsonTypeInfo.As.PROPERTY);
        typeResolverBuilder.typeProperty("classType");
        mapper.setDefaultTyping(typeResolverBuilder);
        AnnotationIntrospector introspector = new JsonIgnoreAnnotationIgnoreInterceptor();
        mapper.setAnnotationIntrospector(introspector);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private static class JsonIgnoreAnnotationIgnoreInterceptor
            extends JacksonAnnotationIntrospector {
        private static final long serialVersionUID = 1L;
        @Override
        public boolean _isIgnorable(Annotated a) {
            return false;
        }
    }

    public static String createJsonFromJava(Object java, String jsonPath, String filename) throws IOException {
        String json = mapper.writeValueAsString(java);

        if (!Files.isDirectory(Paths.get(jsonPath))) {
            Files.createDirectories(Paths.get(jsonPath));
        }

        writeJsonFile(json, Paths.get(jsonPath, filename));
        return readJsonFile(Paths.get(jsonPath, filename));
    }

    private static void writeJsonFile(String json, Path metaFilePath) throws IOException {
        if (!Files.exists(metaFilePath)) {
            Files.createFile(metaFilePath);
            try (BufferedWriter writer = Files.newBufferedWriter(metaFilePath, StandardCharsets.UTF_8)) {
                writer.write(json);
            }
        }
    }

    private static String readJsonFile(Path metaFilePath) throws IOException{
        return Files.readAllLines(metaFilePath).stream().collect(Collectors.joining(ACCSConstants.LINE_SEPARATOR));
    }
}
