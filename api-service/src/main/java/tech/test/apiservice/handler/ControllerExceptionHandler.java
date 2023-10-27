package tech.test.apiservice.handler;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {
    private static Map<String, Object> map;

    @Autowired
    private MessageSource messageSource;

    public ControllerExceptionHandler() {
        map = new LinkedHashMap<String, Object>();
    }

    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            HttpMessageNotReadableException.class,
    })
    @Nullable
    public ResponseEntity<Object> handleException(Exception ex) {
        if(ex instanceof MethodArgumentNotValidException ){
            MethodArgumentNotValidException exception = (MethodArgumentNotValidException) ex;
            List<String> errors = exception.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());

            List<String> errorsMessage = new ArrayList<>();
            for(String error: errors){
                errorsMessage.add(messageSource.getMessage(error, null, LocaleContextHolder.getLocale()));
            }

            map.put("code", HttpStatus.BAD_REQUEST.value());
            map.put("messages", errorsMessage);
            map.put("data", null);

            return new ResponseEntity<Object>(map, HttpStatus.BAD_REQUEST);
        }
        // Handle request body empty
        else if (ex instanceof HttpMessageNotReadableException) {
            List<String> messages = new ArrayList<String>();
            String error = ex.getMessage();

            // Cause body non long type
            if (error.contains("not a valid `long`")
                    || error.contains("Cannot deserialize value of type `java.lang.Long` from String")) {
                messages.add(messageSource.getMessage("api.error.id.must.be.number", null,
                        LocaleContextHolder.getLocale()));
                // Cause request body none
            } else {
                System.out.println(ex.getLocalizedMessage());
                messages.add(messageSource.getMessage("api.error.request.body.required", null,
                        LocaleContextHolder.getLocale()));
            }

            map.put("code", HttpStatus.BAD_REQUEST.value());
            map.put("messages", messages);
            map.put("data", null);

            return new ResponseEntity<Object>(map, HttpStatus.BAD_REQUEST);
            // unhandle specific error
        } else {
            ex.printStackTrace();

            map.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
            map.put("messages", "Internal server error: " +
                    ex.getCause().getMessage());
            map.put("data", null);
            ex.printStackTrace();

            return new ResponseEntity<Object>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
