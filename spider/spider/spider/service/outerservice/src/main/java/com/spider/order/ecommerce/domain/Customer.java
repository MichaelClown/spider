package com.spider.order.ecommerce.domain;

import java.io.Serializable;

/**
 * 用户
 */
public class Customer implements Serializable {

    private Long customerId;    //用户ID

    private String userName;    //用户昵称

    private String cellPhone;   //手机号

    private String email;       //邮箱

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
