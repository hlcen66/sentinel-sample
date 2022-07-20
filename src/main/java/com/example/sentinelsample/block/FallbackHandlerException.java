package com.example.sentinelsample.block;

import org.springframework.stereotype.Component;

@Component
public class FallbackHandlerException {

    public static String exceptionHandler(String param,Throwable e){
        return "fallback-----,兜底，异常信息为:"+e.getMessage();
    }

}
