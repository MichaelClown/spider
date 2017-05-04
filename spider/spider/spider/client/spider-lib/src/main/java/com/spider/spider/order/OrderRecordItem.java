package com.spider.spider.order;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by jian.Michael on 2017/4/4.
 */
public class OrderRecordItem implements Serializable {

    private Date recordDate;

    private String message;


    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
