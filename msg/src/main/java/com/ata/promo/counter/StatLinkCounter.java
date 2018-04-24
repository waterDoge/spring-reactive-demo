package com.ata.promo.counter;

import com.ata.promo.msg.ChannelAppMsg;
import com.ata.promo.msg.LinkMsg;

import java.time.LocalDate;

public class StatLinkCounter {
    private Long channelId;
    private String subCode;
    private Long productionId;
    private Long linkId;
    private Long imp;
    private Long clk;
    private Long eff;
    private LocalDate statDate;

    public static StatLinkCounter of(ChannelAppMsg channelAppMsg, LinkMsg linkMsg) {
        return new StatLinkCounter(channelAppMsg, linkMsg, LocalDate.now());
    }

    public static StatLinkCounter ofYesterday(ChannelAppMsg channelAppMsg, LinkMsg linkMsg) {
        return new StatLinkCounter(channelAppMsg, linkMsg, LocalDate.now().minusDays(1));
    }

    public StatLinkCounter() {
    }

    public StatLinkCounter(ChannelAppMsg channelAppMsg, LinkMsg linkMsg, LocalDate statDate) {
        this.channelId = channelAppMsg.getChannelId();
        this.subCode = channelAppMsg.getCode();
        this.productionId = linkMsg.getProductionId();
        this.linkId = linkMsg.getId();
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

    public Long getProductionId() {
        return productionId;
    }

    public void setProductionId(Long productionId) {
        this.productionId = productionId;
    }

    public Long getLinkId() {
        return linkId;
    }

    public void setLinkId(Long linkId) {
        this.linkId = linkId;
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

    public LocalDate getStatDate() {
        return statDate;
    }

    public void setStatDate(LocalDate statDate) {
        this.statDate = statDate;
    }
}
