package com.spider.order.ecommerce.domain;

import java.io.Serializable;

/**
 * 收货地址
 */
public class Address implements Serializable {

    private Integer province;   //省编号

    private Integer city;       //市编号

    private Integer area;       //区编号

    private String companyName; //单位名称

    private String detail;      //详细地址

    private Integer zipCode;    //邮政编码

    public Integer getZipCode() {
        return zipCode;
    }

    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
    }

    public Integer getProvince() {
        return province;
    }

    public void setProvince(Integer province) {
        this.province = province;
    }

    public Integer getCity() {
        return city;
    }

    public void setCity(Integer city) {
        this.city = city;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
