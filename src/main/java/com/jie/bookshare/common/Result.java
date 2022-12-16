package com.jie.bookshare.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName Result
 * @Description 返回数据的统一json对象
 * @Author wuhaojie
 * @Date 2021/8/7 22:43
 */
@Data
public class Result implements Serializable {
    @ApiModelProperty(value = "是否成功")
    private Boolean success;

    @ApiModelProperty(value = "返回码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private Map<String, Object> data = new HashMap<String, Object>();

    // 即不能在外面创建此类的对象
    private Result(){}

    // 成功
    public static Result ok(){
        Result r = new Result();
        r.setSuccess(true);
        r.setCode(com.jie.bookshare.common.ResultCode.SUCCESS);
        r.setMessage("成功");
        return r;
    }

    // 失败
    public static Result error(){
        Result r = new Result();
        r.setSuccess(false);
        r.setCode(com.jie.bookshare.common.ResultCode.ERROR);
        r.setMessage("失败");
        return r;
    }


    // 实现链式编程
    // 比如 Result.ok().code(code).message(message)...

    public Result success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public Result message(String message){
        this.setMessage(message);
        return this;
    }

    public Result code(Integer code){
        this.setCode(code);
        return this;
    }

    public Result data(String key, Object value){
        this.data.put(key, value);
        return this;
    }

    public Result data(Map<String, Object> map){
        this.setData(map);
        return this;
    }
}
