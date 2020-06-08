package com.svulinovic.cityapi.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.svulinovic.cityapi.exception.BadRequestException;
import com.svulinovic.cityapi.exception.ExceptionConstants;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

public class Helper {

    public static void validateRequest(BindingResult result) {
        if (result.hasFieldErrors()) {
            List<FieldError> errors = result.getFieldErrors();
            throw new BadRequestException(ExceptionConstants.MISSING_REQUIRED_PARAMETER + errorsToString(errors));
        }
    }

    public static String errorsToString(List<FieldError> errors) {
        StringBuilder sb = new StringBuilder();
        String delim = "";
        for (FieldError error : errors) {
            sb.append(delim).append(error.getField() + " " + error.getDefaultMessage());
            delim = ", ";
        }
        return " (" + sb.toString() + ")";
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object fromJsonToObject(final String json, Class clazz) {
        try {
            new ObjectMapper().readValue(json, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object fromJsonToListOfObjects(final String json, Class clazz) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            if (json != null)
                return mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, clazz));
            else
                return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
