package stu.back.basic.utils;

import lombok.Data;

@Data
public class JsonResult {
    private Boolean success = true;
    private String message = "操作成功";
    private Integer code = 200 ;
    private Object data;

    public static JsonResult me(){
        return new JsonResult();
    }
    public static JsonResult me(Object data){
        return new JsonResult().setData(data);
    }

    public JsonResult setSuccess(Boolean success){
        this.success = success;
        return this;
    }

    public JsonResult setMessage(String message){
        this.message=message;
        return this;
    }

    public JsonResult setData(Object data){
        this.data=data;
        return this;
    }

    public JsonResult setCode(Integer code){
        this.code=code;
        return this;
    }
}

