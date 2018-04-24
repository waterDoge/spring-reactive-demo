package com.ata.promo.counter;

import com.ata.promo.msg.ChannelAppMsg;

import java.time.LocalDate;

public class StatChannelCounter {
    private Long channelId;
    private String subCode;
    private String ext1 = "";
    private String ext2 = "";
    private LocalDate statDate;
    private Long imp;
    private Long clk;
    private Long eff;

    public StatChannelCounter() {
    }

    public StatChannelCounter(ChannelAppMsg msg, String ext1, String ext2) {
        this(msg, ext1, ext2, LocalDate.now());
    }

    public StatChannelCounter(ChannelAppMsg msg, String ext1, String ext2, LocalDate statDate) {
        this.channelId = msg.getChannelId();
        this.subCode = msg.getCode();
        this.ext1 = ext1;
        this.ext2 = ext2;
        this.statDate = statDate;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public String getSubCode() {
        return subCode;
    }

    public void setSubCode(String subCode) {
        this.subCode = subCode;
    }

    public String getExt1() {
        return ext1;
    }

    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }

    public String getExt2() {
        return ext2;
    }

    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }

    public LocalDate getStatDate() {
        return statDate;
    }

    public void setStatDate(LocalDate statDate) {
        this.statDate = statDate;
    }

    public Long getImp() {
        return imp;
    }

    public void setImp(Long imp) {
        this.imp = imp;
    }

    public Long getClk() {
        return clk;
    }

    public void setClk(Long clk) {
        this.clk = clk;
    }

    public Long getEff() {
        return eff;
    }

    public void setEff(Long eff) {
        this.eff = eff;
    }
}
