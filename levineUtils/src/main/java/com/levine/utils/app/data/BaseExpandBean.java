package com.levine.utils.app.data;


import java.util.List;

public  class BaseExpandBean<K,V> {

    private K parent;
    private List<V> child;

    public BaseExpandBean(K parent, List<V> child) {
        this.parent = parent;
        this.child = child;
    }

    public K getParent() {
        return parent;
    }

    public List<V> getChild() {
        return child;
    }
}
