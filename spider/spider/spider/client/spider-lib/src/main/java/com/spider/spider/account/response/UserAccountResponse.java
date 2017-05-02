package com.spider.spider.account.response;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by jian.Michael on 2017/5/2.
 */
public class UserAccountResponse implements Serializable {

    private String userName;

    private Long customerId;

    private Date createDate;

    private String cellPhone;

    private String type;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
