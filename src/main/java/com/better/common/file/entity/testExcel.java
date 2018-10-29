package com.better.common.file.entity;

import com.better.anno.bean.Excel;
import com.better.anno.bean.ExcelProperty;

/**
 * @Auther: 梁晓宇
 * @Date: 2018/10/26 14:16
 * @Description:
 */
@Excel(header = {"编号", "名称"}, sheetName = "sheeeeee2")
public class testExcel {

    @ExcelProperty(index = 0)
    private String id;
    @ExcelProperty(index = 1)
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
