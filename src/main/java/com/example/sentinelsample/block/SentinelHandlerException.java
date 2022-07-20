package com.example.sentinelsample.block;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.stereotype.Component;

@Component
public class SentinelHandlerException {

    public static String flowHandler(String param,BlockException e){
        return "blockHandler,触发流控，兜底方法...";
    }

    public static String deGradeHandler(String param, BlockException e){
        return "blockHandler,触发熔断，兜底方法...";
    }

    public static String authHandler(String param, BlockException e){
        return "黑名单，拒绝访问...";
    }

}
