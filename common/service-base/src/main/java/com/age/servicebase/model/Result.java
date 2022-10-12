package com.age.servicebase.model;

import com.age.servicebase.enums.CodeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class Result <T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 成功标志
     */
    @ApiModelProperty(name = "success", value = "成功标志")
    private boolean success = true;

    /**
     * 返回处理消息
     */
    @ApiModelProperty(name = "message", value = "返回处理消息")
    private String message = "操作成功！";

    /**
     * 返回代码
     */
    @ApiModelProperty(name = "code", value = "返回代码")
    private Integer code = 0;

    /**
     * 返回数据对象 data
     */
    @ApiModelProperty(name = "result", value = "返回数据对象")
    private T result;


    /**
     * 时间戳
     */
    private long timestamp = System.currentTimeMillis();

    public Result() {

    }

    public Result(Integer code, String message, boolean success) {
        this.success = success;
        this.message = message;
        this.code = code;
    }

    public Result<T> success(String message) {
        this.message = message;
        this.code = CodeEnum.OK.getCode();
        this.success = true;

        return this;
    }


    public static Result<Object> ok() {
        Result<Object> r = new Result<Object>();
        r.setSuccess(true);
        r.setCode(CodeEnum.OK.getCode());
        r.setMessage(CodeEnum.OK.getDesc());

        return r;
    }

    public static Result<Object> ok(String msg) {
        Result<Object> r = new Result<Object>();
        r.setSuccess(true);
        r.setCode(CodeEnum.OK.getCode());
        r.setMessage(msg);

        return r;
    }

    public static Result<Object> ok(Object data) {
        Result<Object> r = new Result<Object>();
        r.setSuccess(true);
        r.setCode(CodeEnum.OK.getCode());
        r.setResult(data);

        return r;
    }

    public static Result<Object> error(String msg) {
        return error(CodeEnum.INTERNAL_ERROR.getCode(), msg);
    }

    public static Result<Object> error(int code, String msg) {
        Result<Object> r = new Result<Object>();
        r.setCode(code);
        r.setMessage(msg);
        r.setSuccess(false);
        return r;
    }

    public Result<T> error500(String message) {
        this.message = message;
        this.code = CodeEnum.INTERNAL_ERROR.getCode();
        this.success = false;
        return this;
    }
    public static Result<Object> error(CodeEnum codeEnum) {
        return error(codeEnum.getCode(), codeEnum.getDesc());
    }

}
