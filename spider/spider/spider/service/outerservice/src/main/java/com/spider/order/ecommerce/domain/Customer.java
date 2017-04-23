package com.spider.order.ecommerce.domain;

import java.io.Serializable;

/**
 * 用户
 */
public class Customer implements Serializable {

    private String userName;    //用户昵称

    private String cellPhone;   //手机号

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }
}
