package tech.test.apiservice.dto;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseDTO {
    private HttpHeaders headers;
    private HttpStatus status;
    private String message;
    private Object data;

    public ResponseDTO(){
        this.headers = new HttpHeaders();
        this.status = null;
        this.message = null;
        this.data = null;
    }
}
