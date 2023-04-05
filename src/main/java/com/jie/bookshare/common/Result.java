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

    /**
     * 是否成功
     */
    @ApiModelProperty(value = "是否成功")
    private Boolean success;

    /**
     * 返回码
     */
    @ApiModelProperty(value = "返回码")
    private Integer code;

    /**
     * 返回消息
     */
    @ApiModelProperty(value = "返回消息")
    private String message;

    /**
     * 返回数据
     */
    @ApiModelProperty(value = "返回数据")
    private Map<String, Object> data = new HashMap<String, Object>();

    // 即不能在外面创建此类的对象
    private Result(){}

    // 成功
    public static Result ok(){
        Result r = new Result();
        r.setSuccess(true);
        r.setCode(com.jie.bookshare.common.ResultCode.CODE_SUCCESS);
        r.setMessage("成功");
        return r;
    }

    // 失败
    public static Result error(){
        Result r = new Result();
        r.setSuccess(false);
        r.setCode(com.jie.bookshare.common.ResultCode.CODE_ERROR);
        r.setMessage("失败");
        return r;
    }


    // 实现链式编程
    // 比如 Result.ok().code(code).message(message)...

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
