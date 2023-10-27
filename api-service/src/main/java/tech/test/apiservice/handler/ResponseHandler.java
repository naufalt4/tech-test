package tech.test.apiservice.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import tech.test.apiservice.dto.ResponseDTO;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ResponseHandler extends ResponseEntityExceptionHandler {
    private static Map<String, Object> body;

    public ResponseHandler() {
        body = new LinkedHashMap<String, Object>();
    }

    // Rest API Response
    public static ResponseEntity<Object> generateResponse(ResponseDTO response) {
        List<String> messages = new ArrayList<String>();
        messages.add(response.getMessage());

        body.put("code", response.getStatus().value());
        body.put("messages", messages);
        body.put("data", (response.getData() == null) ? new ArrayList<>() : response.getData());
        return new ResponseEntity<Object>(body, response.getHeaders(), response.getStatus());
    }
}