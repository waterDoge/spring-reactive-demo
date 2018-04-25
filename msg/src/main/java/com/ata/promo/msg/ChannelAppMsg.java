package com.ata.promo.msg;

import org.msgpack.annotation.Message;

import java.io.Serializable;

@Message
public class ChannelAppMsg implements Serializable {
    private Long id;
    private Long channelId;
    private String code;
    private String link;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "ChannelAppMsg{" +
                "id=" + id +
                ", channelId=" + channelId +
                ", code='" + code + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
