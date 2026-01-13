package com.xidian.nacosserviceb.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 统一响应结果类
 * @param <T> 数据泛型
 */
@Data
public class R<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 状态码 */
    private Integer code;

    /** 响应消息 */
    private String msg;

    /** 响应数据 */
    private T data;

    // ========== 状态码常量 ==========
    /** 成功状态码 */
    public static final int SUCCESS_CODE = 200;

    /** 失败状态码 */
    public static final int ERROR_CODE = 500;

    /** 未授权 */
    public static final int UNAUTHORIZED_CODE = 401;

    /** 禁止访问 */
    public static final int FORBIDDEN_CODE = 403;

    /** 资源不存在 */
    public static final int NOT_FOUND_CODE = 404;

    // ========== 构造方法 ==========
    public R() {}

    public R(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public R(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    // ========== 成功响应 ==========
    /**
     * 成功（无数据）
     */
    public static <T> R<T> ok() {
        return new R<>(SUCCESS_CODE, "操作成功");
    }

    /**
     * 成功（带数据）
     */
    public static <T> R<T> ok(T data) {
        return new R<>(SUCCESS_CODE, "操作成功", data);
    }

    /**
     * 成功（自定义消息）
     */
    public static <T> R<T> ok(String msg) {
        return new R<>(SUCCESS_CODE, msg);
    }

    /**
     * 成功（自定义消息和数据）
     */
    public static <T> R<T> ok(String msg, T data) {
        return new R<>(SUCCESS_CODE, msg, data);
    }

    // ========== 失败响应 ==========
    /**
     * 失败（默认消息）
     */
    public static <T> R<T> error() {
        return new R<>(ERROR_CODE, "操作失败");
    }

    /**
     * 失败（自定义消息）
     */
    public static <T> R<T> error(String msg) {
        return new R<>(ERROR_CODE, msg);
    }

    /**
     * 失败（自定义状态码和消息）
     */
    public static <T> R<T> error(Integer code, String msg) {
        return new R<>(code, msg);
    }

    /**
     * 失败（自定义状态码、消息和数据）
     */
    public static <T> R<T> error(Integer code, String msg, T data) {
        return new R<>(code, msg, data);
    }

    // ========== 特定状态响应 ==========
    /**
     * 未授权
     */
    public static <T> R<T> unauthorized(String msg) {
        return new R<>(UNAUTHORIZED_CODE, msg);
    }

    /**
     * 禁止访问
     */
    public static <T> R<T> forbidden(String msg) {
        return new R<>(FORBIDDEN_CODE, msg);
    }

    /**
     * 资源不存在
     */
    public static <T> R<T> notFound(String msg) {
        return new R<>(NOT_FOUND_CODE, msg);
    }

    // ========== 工具方法 ==========
    /**
     * 判断是否成功
     */
    public boolean isSuccess() {
        return SUCCESS_CODE == this.code;
    }

    /**
     * 判断是否失败
     */
    public boolean isError() {
        return !isSuccess();
    }
}