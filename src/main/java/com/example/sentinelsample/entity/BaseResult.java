package com.example.sentinelsample.entity;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Optional;


/**
 * 响应信息主体
 *
 * @author ruoyi
 */
@Data
@Slf4j
@Component
public class BaseResult<T> implements Serializable {
    /**
     * 成功
     */
    public static final int SUCCESS = 0;
    /**
     * 失败
     */
    public static final int FAIL = -1;

    private static final long serialVersionUID = 1L;

    private int code;

    private String message;

    private T data;

    private String[] errorArgs;

    private Long currentTime;

    private String traceId;

    private static boolean internationalization;

    public static BaseResult ok() {
        return restBasResult("", SUCCESS, "success", null);
    }

    public static <T> BaseResult<T> ok(T data) {
        return restBasResult(data, SUCCESS, "success", null);
    }

    public static <T> BaseResult<T> ok(T data, String message) {
        return restBasResult(data, SUCCESS, message, null);
    }

    public static  BaseResult fail() {
        return restBasResult("", FAIL, "fail", null);
    }

    public static  BaseResult fail(String message) {
        return restBasResult("", FAIL, message, null);
    }

    public static <T> BaseResult<T> fail(T data) {
        return restBasResult(data, FAIL, "fail", null);
    }

    public static <T> BaseResult<T> fail(T data, String message) {
        return restBasResult(data, FAIL, message, null);
    }

    public static BaseResult fail(int code, String message) {
        return restBasResult("", code, message, null);
    }

    public static BaseResult fail(int code, String message, String[] errors) {
        return restBasResult("", code, message, errors);
    }

    private static <T> BaseResult<T> restBasResult(T data, int code, String message, String[] errors) {
        BaseResult<T> apiBaseResult = new BaseResult<>();
        apiBaseResult.setCode(code);
        apiBaseResult.setData(data);
        apiBaseResult.setErrorArgs(errors);
        apiBaseResult.setMessage(message);
        apiBaseResult.setCurrentTime(System.currentTimeMillis());
        return apiBaseResult;
    }

    public boolean isSuccess() {
        return SUCCESS == this.code;
    }

}
