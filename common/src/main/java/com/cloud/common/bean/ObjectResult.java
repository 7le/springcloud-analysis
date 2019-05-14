package com.cloud.common.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * 返回结果类
 *
 * @author 7le
 */
@Data
public class ObjectResult<T> implements Serializable {

    private static final long serialVersionUID = -9146805371831100892L;

    final public static ObjectResult SUCCESS = new ObjectResult("200", "操作成功");
    final public static ObjectResult ERROR = new ObjectResult("400", "操作失败");
    final public static ObjectResult EXCEPTION = new ObjectResult("500", "服务异常");

    private String code;

    private String msg;

    private T data;

    public ObjectResult() {
    }

    public ObjectResult(String code, String msg) {
        this(code, msg, null);
    }

    public ObjectResult(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    private ObjectResult copyThis() {
        return new ObjectResult(code, msg, null);
    }

    public static ObjectResult error(String msg) {
        return new ObjectResult(ERROR.code, msg);
    }

    public static ObjectResult success(String msg) {
        return new ObjectResult(SUCCESS.code, msg);
    }

    public static ObjectResult success(Object object) {
        ObjectResult r = ObjectResult.SUCCESS.copyThis();
        r.setData(object);
        return r;
    }
}

