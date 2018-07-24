package com.yeay.shorturl.vo;


public class BaseResponseVo<T> extends BaseResponse {
    private Long count;

    private T data;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
