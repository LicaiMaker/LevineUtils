package com.levine.base.data.bean;

import com.levine.utils.app.data.BaseBean;

public class CustomerBean extends BaseBean {

    String name;

    public CustomerBean(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getBeanType() {
        return name;
    }
}
