package com.ata.promo.msg;

import org.msgpack.annotation.Message;

import java.io.Serializable;
@Message
public class ProductionMsg implements Serializable {
    private Long id;
    private String name;
    private String des;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
