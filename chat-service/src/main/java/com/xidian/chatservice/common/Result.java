package com.xidian.chatservice.common;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Result 类，通用返回结果类
 * 服务端响应的数据最终都会封装成此对象
 * 
 * @param <T> 响应数据的类型
 * 
 * 字段说明：
 * - code: HTTP状态码（200表示成功，4xx表示客户端错误，5xx表示服务器错误）
 * - msg: 错误信息或提示信息
 * - data: 响应数据（前后端交互的数据）
 * - map: 动态数据（可选，用于扩展字段）
 */
@Data
public class Result<T> {

    private Integer code; // HTTP状态码：200成功，4xx客户端错误，5xx服务器错误

    private String msg; // 错误信息或提示信息

    private T data; // 数据：前后端交互的数据

    private Map<String, Object> map = new HashMap<>(); // 动态数据

    /**
     * 静态success方法，当响应成功返回Result对象
     * 
     * @param object 响应数据
     * @return Result对象
     * @param <T> 数据类型
     */
    public static <T> Result<T> success(T object) {
        Result<T> r = new Result<T>();
        r.data = object;
        r.code = ResultCode.SUCCESS.getCode();
        r.msg = ResultCode.SUCCESS.getMessage();
        return r;
    }

    /**
     * 静态success方法，当响应成功但无数据时返回Result对象
     * 
     * @return Result对象
     * @param <T> 数据类型
     */
    public static <T> Result<T> success() {
        Result<T> r = new Result<T>();
        r.code = ResultCode.SUCCESS.getCode();
        r.msg = ResultCode.SUCCESS.getMessage();
        return r;
    }

    /**
     * 静态success方法，当响应成功时返回Result对象（带提示信息）
     * 
     * @param object 响应数据
     * @param msg 提示信息
     * @return Result对象
     * @param <T> 数据类型
     */
    public static <T> Result<T> success(T object, String msg) {
        Result<T> r = new Result<T>();
        r.data = object;
        r.code = ResultCode.SUCCESS.getCode();
        r.msg = msg;
        return r;
    }

    /**
     * 静态error方法，当响应失败返回Result对象（使用默认的500错误码）
     * 
     * @param msg 错误信息
     * @return Result对象
     * @param <T> 数据类型
     */
    public static <T> Result<T> error(String msg) {
        Result<T> r = new Result<>();
        r.msg = msg;
        r.code = ResultCode.INTERNAL_SERVER_ERROR.getCode();
        return r;
    }

    /**
     * 静态error方法，当响应失败返回Result对象（使用指定的ResultCode）
     * 
     * @param resultCode 状态码枚举
     * @return Result对象
     * @param <T> 数据类型
     */
    public static <T> Result<T> error(ResultCode resultCode) {
        Result<T> r = new Result<>();
        r.code = resultCode.getCode();
        r.msg = resultCode.getMessage();
        return r;
    }

    /**
     * 静态error方法，当响应失败返回Result对象（使用指定的ResultCode和自定义消息）
     * 
     * @param resultCode 状态码枚举
     * @param msg 自定义错误信息
     * @return Result对象
     * @param <T> 数据类型
     */
    public static <T> Result<T> error(ResultCode resultCode, String msg) {
        Result<T> r = new Result<>();
        r.code = resultCode.getCode();
        r.msg = msg;
        return r;
    }

    /**
     * 静态error方法，当响应失败返回Result对象（带错误码，兼容旧代码）
     * 
     * @param code 错误码
     * @param msg 错误信息
     * @return Result对象
     * @param <T> 数据类型
     */
    public static <T> Result<T> error(Integer code, String msg) {
        Result<T> r = new Result<>();
        r.code = code;
        r.msg = msg;
        return r;
    }

    /**
     * 添加动态数据到map中
     * 
     * @param key 键
     * @param value 值
     * @return Result对象（支持链式调用）
     */
    public Result<T> add(String key, Object value) {
        this.map.put(key, value);
        return this;
    }
}

