package com.xidian.chatservice.common;

import lombok.Getter;

/**
 * HTTP 状态码枚举类
 * 用于统一管理 API 返回的状态码
 * 
 * 遵循 HTTP 标准状态码：
 * - 2xx: 成功
 * - 4xx: 客户端错误
 * - 5xx: 服务器错误
 */
@Getter
public enum ResultCode {

    /**
     * 成功状态码
     */
    SUCCESS(200, "操作成功"),

    /**
     * 客户端错误状态码 (4xx)
     */
    BAD_REQUEST(400, "请求参数错误"),
    UNAUTHORIZED(401, "未授权，请先登录"),
    FORBIDDEN(403, "禁止访问，权限不足"),
    NOT_FOUND(404, "资源不存在"),
    METHOD_NOT_ALLOWED(405, "请求方法不允许"),
    CONFLICT(409, "资源冲突，请求的资源已存在"),
    UNPROCESSABLE_ENTITY(422, "请求参数验证失败"),

    /**
     * 服务器错误状态码 (5xx)
     */
    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),
    BAD_GATEWAY(502, "网关错误"),
    SERVICE_UNAVAILABLE(503, "服务不可用"),
    GATEWAY_TIMEOUT(504, "网关超时");

    /**
     * 状态码
     */
    private final Integer code;

    /**
     * 状态信息
     */
    private final String message;

    /**
     * 构造函数
     * 
     * @param code 状态码
     * @param message 状态信息
     */
    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 根据状态码获取枚举
     * 
     * @param code 状态码
     * @return ResultCode枚举，如果不存在则返回null
     */
    public static ResultCode getByCode(Integer code) {
        for (ResultCode resultCode : values()) {
            if (resultCode.getCode().equals(code)) {
                return resultCode;
            }
        }
        return null;
    }
}


