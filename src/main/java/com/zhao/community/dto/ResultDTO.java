package com.zhao.community.dto;

import lombok.Data;

@Data
public class ResultDTO {

    private Integer code;
    private String message;
    public static ResultDTO errorOf(Integer code,String message){
        ResultDTO resultDTO=new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
//        因为是静态方法，在外部可以直接调用，所以此处必须实例化一个对象来设置；

        return resultDTO;
    }
}
