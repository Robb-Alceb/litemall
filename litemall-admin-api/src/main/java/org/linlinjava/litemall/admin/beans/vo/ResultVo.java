package org.linlinjava.litemall.admin.beans.vo;

/**
 * @author ：stephen
 * @date ：Created in 2020/6/4 10:49
 * @description：TODO
 *
 */
public class ResultVo<T> {
    private Integer code;

    private String message;

    private T data;

    public ResultVo() {
    }

    public ResultVo(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResultVo(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
