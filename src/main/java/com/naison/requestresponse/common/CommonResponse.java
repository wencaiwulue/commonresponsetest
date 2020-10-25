package com.naison.requestresponse.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.naison.requestresponse.modle.Response;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author naison
 * @since 10/25/2020 16:57
 */
@RestControllerAdvice
public class CommonResponse implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (!(body instanceof Response)) {
            try {
                Object requestId = RequestContextHolder.currentRequestAttributes().getAttribute("requestId", RequestAttributes.SCOPE_REQUEST);
                Response<?> commonResponse = new Response<>(body);
                if (requestId != null) {
                    commonResponse.setRequestId(requestId.toString());
                }
                return new ObjectMapper().writeValueAsString(commonResponse);
            } catch (JsonProcessingException e) {
                return body;
            }
        }
        return body;
    }
}
