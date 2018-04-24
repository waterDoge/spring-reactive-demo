package com.ata.promo.msg;

import org.msgpack.annotation.Message;

import java.io.Serializable;
import java.util.Collection;
@Message
public class ChannelInfoMsg implements Serializable {
    private Long id;
    private String appKey;
    private Collection<ChannelAppMsg> apps;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public Collection<ChannelAppMsg> getApps() {
        return apps;
    }

    public void setApps(Collection<ChannelAppMsg> apps) {
        this.apps = apps;
    }
}
