package com.example.sentinelsample.config;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import com.alibaba.nacos.api.utils.StringUtils;
import com.example.sentinelsample.block.GlobalBlockExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class SentinelConfig {

    private static final String ORIGIN_DEFAULT = "";


    @Bean
    public RequestOriginParser requestOriginParser(){
        return (request ->{
            String origin = request.getHeader("origin");
            if(StringUtils.isEmpty(origin)){
                return ORIGIN_DEFAULT;
            }
            return origin;
        });
    }

    @Primary
    @Bean
    public BlockExceptionHandler blockExceptionHandler(){
        return new GlobalBlockExceptionHandler();
    }
}
