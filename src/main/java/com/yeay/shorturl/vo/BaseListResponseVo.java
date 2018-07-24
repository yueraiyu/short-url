package com.yeay.shorturl.vo;

import java.util.List;

public class BaseListResponseVo<T> extends BaseResponse {
    private Long count;

    private List<T> data;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
