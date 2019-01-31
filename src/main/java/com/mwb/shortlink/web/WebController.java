package com.mwb.shortlink.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
@Slf4j
public class WebController {

    @ExceptionHandler
    @ResponseBody
    public Map<String, Object> exception(HttpServletRequest request, Exception ex) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", 500);
        result.put("message", "系统错误");
        log.error("exception err", ex);
        return result;
    }

}
