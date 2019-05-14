package com.cloud.common.bean;

/**
 * 业务码
 *
 * @author : 7le
 */
public enum SystemConstant {

    /**
     * 通用
     */
    SUCCESS("200","操作成功"),
    SYSTEM_ERROR("500","系统异常"),
    PARAM_ERROR("400","参数异常"),
    JSON_ERROR("400","JSON格式错误"),
    PARAM_EMPTY("400","缺少必填参数"),
    SPECIAL_CHARACTER("400","含有特殊字符"),
    DEVICESERIAL_FORMATE_ERROR("400","设备序列号格式有误"),
    PHONENO_FORMATE_ERROR("400","手机号格式有误"),
    PARAM_TOO_LONG("400","参数长度超过32位"),
    UNSUPPORTED_MEDIA_TYPE("415","不支持当前媒体类型"),
    METHOD_NOT_ALLOWED("405","不支持当前请求方法");

    private String code;

    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    SystemConstant(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getMsg(String code) {
        for (SystemConstant e : SystemConstant.values()) {
            if (code.equals(e.code)) {
                return e.msg;
            }
        }
        return null;
    }
}
