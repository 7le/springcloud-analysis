package com.cloud.ad.bean;

/**
 * 结果bean
 *
 * @author 7le
 * @since v1.0.0
 */
public class ResultBean<T> {

    final public static ResultBean SUCCESS = new ResultBean(200, "success");
    final public static ResultBean ERROR = new ResultBean(400, "error");

    protected int code;
    protected String msg;
    protected T data;

    public ResultBean() {
    }

    public ResultBean(int code, String msg) {
        this(code, msg, null);
    }

    public ResultBean(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    private ResultBean copyThis() {
        return new ResultBean(code, msg, null);
    }

    public static ResultBean error(String error) {
        return new ResultBean(ERROR.code, error);
    }

    public static ResultBean success(String error) {
        return new ResultBean(SUCCESS.code, error);
    }

    public static ResultBean success(Object object) {
        ResultBean r = ResultBean.SUCCESS.copyThis();
        r.setData(object);
        return r;
    }
}
