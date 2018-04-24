package com.ata.promo;

public final class RedisKeyConstants {
    public static final String DELIMITER = ">";

    public static final String PRODUCTION_HASH = "production_hash";
    public static final String PRODUCTION_LINKS_PREFIX = "production_links";
    public static final String LINK_HASH = "link_hash";
    public static final String CHANNEL_INFO_HASH = "channel_info_hash";
    public static final String CHANNEL_APP_HASH = "channel_app_hash";

    public static final String STAT_LINK_CLK_PREFIX = "stat_link_clk";
    public static final String STAT_LINK_IMP_PREFIX = "stat_link_imp";
    public static final String STAT_CHANNEL_CLK_PREFIX = "stat_channel_clk";
    public static final String STAT_CHANNEL_IMP_PREFIX = "stat_channel_imp";

    private RedisKeyConstants() {
    }
}
