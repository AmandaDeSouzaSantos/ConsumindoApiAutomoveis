package com.Amanda;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class ConverterDados implements IConversor {

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T conversorObjetos(String json, Class<T> classe) {
        try {
            return mapper.readValue(json, classe);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> List<T> conversorLista(String json, Class<T> classe) {
        try {
            return mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, classe));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

