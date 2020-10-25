package com.naison.requestresponse.controller;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author naison
 * @since 10/25/2020 17:22
 */
@RestController
public class HelloController {
    @PostMapping("/hello/{name}")
    public String hello(@RequestBody JsonNode jsonNode, @PathVariable String name, HttpServletRequest request) {
        String requestId = request.getAttribute("requestId").toString();
        System.out.println(requestId);
        return "hello " + name;
    }
}
