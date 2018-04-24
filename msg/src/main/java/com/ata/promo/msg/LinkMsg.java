package com.ata.promo.msg;

import org.msgpack.annotation.Message;

import java.io.Serializable;

@Message
public class LinkMsg implements Serializable {
    private Long id;
    private Long productionId;
    private String link;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Long getProductionId() {
        return productionId;
    }

    public void setProductionId(Long productionId) {
        this.productionId = productionId;
    }
}
