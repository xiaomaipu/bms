package com.imxiaomai.bms.common;

/**
 *
 * @author KuKan
 * @date 2018/1/28
 */
public class ServerFailException extends RuntimeException {

    public ServerFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServerFailException(String message) {
        super(message);
    }
}
