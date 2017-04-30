package com.spider.order;


import java.io.Serializable;

/**
 * Created by jian.Michael on 2017/3/30.
 */
public class Company implements Serializable {

    private Long companyId;

    private String companyName;

    public Company() {
    }

    public Company(Long companyId, String companyName) {
        this.companyId = companyId;
        this.companyName = companyName;
    }

    public Company(Long companyId) {
        this.companyId = companyId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
