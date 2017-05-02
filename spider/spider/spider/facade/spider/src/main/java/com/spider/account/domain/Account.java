package com.spider.account.domain;

import java.io.Serializable;

/**
 * Created by jian.Michael on 2017/4/1.
 */
public class Account implements Serializable {

    private String cellPhone;

    private String identity;

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }
}
