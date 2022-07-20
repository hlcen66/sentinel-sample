package com.example.sentinelsample.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.nacos.api.utils.StringUtils;
import com.example.sentinelsample.block.FallbackHandlerException;
import com.example.sentinelsample.block.SentinelHandlerException;
import com.example.sentinelsample.util.ContentType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping(value = { "/sentinel" })
public class TestController {

    private static final ThreadLocal<SimpleDateFormat> sdf = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));


    /**
     * 流控
     * @param param
     * @return
     */
    @GetMapping(value = "/flow",produces = ContentType.APPLICATION_JSON_UTF8)
    @SentinelResource(value = "testFlow",blockHandlerClass = SentinelHandlerException.class,blockHandler = "flowHandler")
    public String testFlow(String param){
        return "success------param:"+param +",time:"+ sdf.get().format(new Date());
    }

    /**
     * 熔断
     * @param param
     * @return
     */
    @GetMapping(value = "/degrade",produces = ContentType.APPLICATION_JSON_UTF8)
    @SentinelResource(value = "testGrade",blockHandlerClass = SentinelHandlerException.class,blockHandler = "deGradeHandler")
    public String testGrade(String param){
        if("error".equals(param)){
            throw new RuntimeException("模拟错误次数熔断");
        }
        return "success------param:"+param +",time:"+ sdf.get().format(new Date());
    }

    /**
     * 授权
     * @param param
     * @return
     */
    @GetMapping(value = "/auth",produces = ContentType.APPLICATION_JSON_UTF8)
    @SentinelResource(value = "testAuth",blockHandlerClass = SentinelHandlerException.class,blockHandler = "authHandler")
    public String base(String param){
        return "success------param:"+param +",time:"+ sdf.get().format(new Date());

    }

    /**
     * 模拟异常兜底
     * @param param
     * @return
     */
    @GetMapping(value = "/exception",produces = ContentType.APPLICATION_JSON_UTF8)
    @SentinelResource(value = "testException",fallbackClass = FallbackHandlerException.class,fallback = "exceptionHandler")
    public String testException(String param){
        throw new RuntimeException("模拟异常");
    }


    /**
     * 既配置了blockhandler,同时也配置了fallback，那么最终会抛出blockHandler的逻辑
     * @param param
     * @return
     */
    @GetMapping(value = "/multi",produces = ContentType.APPLICATION_JSON_UTF8)
    @SentinelResource(value = "testmulti",blockHandlerClass = SentinelHandlerException.class,blockHandler = "flowHandler",
                                                fallbackClass = FallbackHandlerException.class,fallback = "exceptionHandler",
                                                exceptionsToIgnore = IllegalStateException.class)
    public String test(String param){
        if(StringUtils.isEmpty(param)){
            throw new RuntimeException("参数为空...");
        }
        return "success------param:"+param +",time:"+ sdf.get().format(new Date());
    }

}
