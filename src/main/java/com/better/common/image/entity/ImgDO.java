package com.better.common.image.entity;

import com.better.anno.bean.TableImageJPGE;
import com.better.anno.bean.TableImageJPGEProperty;

@TableImageJPGE(total = {"编号","名字","年龄","创建时间"})
public class ImgDO {

    @TableImageJPGEProperty(index = 0, notNull = true)
    private String id;

    @TableImageJPGEProperty(index = 1)
    private String name;

    @TableImageJPGEProperty(index = 2)
    private String age;

    @TableImageJPGEProperty(index = 3)
    private String createTime;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
