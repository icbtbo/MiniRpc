package com.mini.rpc.serialization;

/**
 * @author songjiancheng
 * @version 1.0
 * @Description 序列化错误
 * @date 2022/6/18 9:10 下午
 */
public class SerializationException extends RuntimeException {

    private static final long serialVersionUID = 3365624081242234230L;

    public SerializationException() {
        super();
    }

    public SerializationException(String msg) {
        super(msg);
    }

    public SerializationException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public SerializationException(Throwable cause) {
        super(cause);
    }
}
