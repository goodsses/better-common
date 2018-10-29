package com.better.common.file.entity;

import com.better.anno.bean.Excel;
import com.better.anno.bean.ExcelProperty;

/**
 * 测试实体类
 */
@Excel(header = {"绑定时间", "奖品名称", "手机号", "收件人", "详细地址"}, sheetName = "奖品地址")
public class AwardMailAddress {

    @ExcelProperty(index = 0)
    private String bindTime;
    @ExcelProperty(index = 1)
    private String awardName;
    @ExcelProperty(index = 2)
    private String mobile;
    @ExcelProperty(index = 3)
    private String addressName;
    @ExcelProperty(index = 4)
    private String detailAddress;

    public String getBindTime() {
        return bindTime;
    }

    public void setBindTime(String bindTime) {
        this.bindTime = bindTime;
    }

    public String getAwardName() {
        return awardName;
    }

    public void setAwardName(String awardName) {
        this.awardName = awardName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }
}
