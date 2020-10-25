package com.naison.requestresponse.common;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonInputMessage;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.UUID;

/**
 * @author naison
 * @since 10/25/2020 16:56
 */
@RestControllerAdvice
public class CommonRequest implements RequestBodyAdvice {

    @Override
    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) throws IOException {
        int available = httpInputMessage.getBody().available();
        byte[] bytes = new byte[available];
        int read = httpInputMessage.getBody().read(bytes);
        if (read <= 0) {
            return httpInputMessage;
        }

        JsonNode jsonNode = new ObjectMapper().readValue(bytes, JsonNode.class);
        if (!jsonNode.hasNonNull("requestId")) {
            RequestContextHolder.currentRequestAttributes().setAttribute("requestId", UUID.randomUUID().toString(), RequestAttributes.SCOPE_REQUEST);
        }

        return new MappingJacksonInputMessage(new ByteArrayInputStream(bytes), httpInputMessage.getHeaders());
    }

    @Override
    public Object afterBodyRead(Object o, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return o;
    }

    @Override
    public Object handleEmptyBody(Object o, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return o;
    }
}
