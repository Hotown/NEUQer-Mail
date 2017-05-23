package com.neuqer.mail.common;

import com.alibaba.fastjson.JSONException;
import com.neuqer.mail.exception.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartException;

/**
 * Created by Hotown on 17/5/22.
 */
@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    @ResponseBody
    public Response handle(BaseException error) {
        return new Response(error.getCode(), error.getMessage());
    }


    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            MissingPathVariableException.class,
            MissingServletRequestParameterException.class,
            JSONException.class,
            HttpMessageNotReadableException.class,
            HttpRequestMethodNotSupportedException.class,
            MultipartException.class,
    })
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    @ResponseBody
    public Response handleRequestBody(Throwable error) {
        return new Response(10000, error.getMessage());
    }
}
