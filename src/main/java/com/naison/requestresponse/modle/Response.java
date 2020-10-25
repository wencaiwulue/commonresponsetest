package com.naison.requestresponse.modle;

import java.util.UUID;

/**
 * @author naison
 * @since 10/25/2020 17:00
 */
public class Response<T> {
    private int code;
    private String requestId;
    private T data;

    public Response() {
    }

    public Response(T data) {
        this.code = 200;
        this.data = data;
        this.requestId = UUID.randomUUID().toString();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
