package com.allong.centerstation.common;

import com.allong.centerstation.common.enums.EnumResult;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * Copyright (C), 2015-2020, 杭州奥朗信息科技有限公司
 * FileName: Result
 * Author:   YJ
 * Date:     2020/11/19 11:07
 * Description: 统一定义放回结果集合
 * History:
 * <author>          <time>          <version>          <desc>
 * YJ       2020/11/19 11:07        1.0              描述
 */
@Data
public class Result<T> {

    private int code;

    private String message;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date date = new Date();

    private T data;

    public Result(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static class Builder<T> {
        private int code;

        private String message;

        private T data;

        public Builder setCode(int code) {
            this.code = code;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setData(T data) {
            this.data = data;
            return this;
        }

        public Result<T> buildQuerySuccess() {
            if (code == 0) {
                code = EnumResult.QUERY_SUCCESS.getCode();
            }
            if(null ==message){
                message = EnumResult.QUERY_SUCCESS.getDesc();
            }
            return new Result<>(code, message, data);
        }

        public Result<T> buildQueryFailed() {
            if (code == 0) {
                code = EnumResult.QUERY_FAILED.getCode();
            }
            if(null ==message){
                message = EnumResult.QUERY_FAILED.getDesc();
            }
            return new Result<>(code, message, data);
        }


        public Result<T> buildSaveSuccess() {
            if (code == 0) {
                code = EnumResult.SAVE_SUCCESS.getCode();
            }
            if(null ==message){
                message = EnumResult.SAVE_SUCCESS.getDesc();
            }
            return new Result<>(code, message, data);
        }

        public Result<T> buildSaveFailed() {
            if (code == 0) {
                code = EnumResult.SAVE_FAILED.getCode();
            }
            if(null ==message){
                message = EnumResult.SAVE_FAILED.getDesc();
            }
            return new Result<>(code, message, data);
        }

        public Result<T> buildUpdateSuccess() {
            if (code == 0) {
                code = EnumResult.UPDATE_SUCCESS.getCode();
            }
            if(null ==message){
                message = EnumResult.UPDATE_SUCCESS.getDesc();
            }
            return new Result<>(code, message, data);
        }

        public Result<T> buildUpdateFailed() {
            if (code == 0) {
                code = EnumResult.UPDATE_FAILED.getCode();
            }
            if(null ==message){
                message = EnumResult.UPDATE_FAILED.getDesc();
            }
            return new Result<>(code, message, data);
        }


        public Result<T> buildDeleteSuccess() {
            if (code == 0) {
                code = EnumResult.DELETE_SUCCESS.getCode();
            }
            if(null ==message){
                message = EnumResult.DELETE_SUCCESS.getDesc();
            }
            return new Result<>(code, message, data);
        }

        public Result<T> buildDeleteFailed() {
            if (code == 0) {
                code = EnumResult.DELETE_FAILED.getCode();
            }
            if(null ==message){
                message = EnumResult.DELETE_FAILED.getDesc();
            }
            return new Result<>(code, message, data);
        }


        public Result<T> buildOperationSuccess() {
            if (code == 0) {
                code = EnumResult.OPERATION_SUCCESS.getCode();
            }
            if(null ==message){
                message = EnumResult.OPERATION_SUCCESS.getDesc();
            }
            return new Result<>(code, message, data);
        }

        public Result<T> buildOperationFailed() {
            if (code == 0) {
                code = EnumResult.OPERATION_FAILED.getCode();
            }
            if(null ==message){
                message = EnumResult.OPERATION_FAILED.getDesc();
            }
            return new Result<>(code, message, data);
        }

        public Result<T> build(){
            return new Result<>(code, message, data);
        }
    }
}
