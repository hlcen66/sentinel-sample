package com.example.sentinelsample.block;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.example.sentinelsample.entity.BaseResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 全局sentinel规则触发处理器
 */

@Component
@Slf4j
public class GlobalBlockExceptionHandler implements BlockExceptionHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse response, BlockException e) throws Exception {
        //Sentinel规则的详细信息
        log.info("BlockExceptionHandler BlockException ============ " + e.getRule());
        BaseResult r = null;
        if (e instanceof FlowException) {
            r = BaseResult.fail(100, "触发限流规则控制，请稍后重试...");
        } else if (e instanceof DegradeException) {
            r = BaseResult.fail(101, "触发降级规则控制，请稍后重试...");
        } else if (e instanceof ParamFlowException) {
            r = BaseResult.fail(102, "触发热点规则控制，请稍后重试...");
        } else if (e instanceof SystemBlockException) {
            r = BaseResult.fail(103, "触发系统规则控制，请稍后重试...");
        } else if (e instanceof AuthorityException) {
            r = BaseResult.fail(104, "触发授权规则控制，请稍后重试...");
        }
        response.setStatus(500);
        response.setCharacterEncoding("utf-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getWriter(), r);
    }
}
