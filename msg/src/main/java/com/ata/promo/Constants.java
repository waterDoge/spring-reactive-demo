package com.ata.promo;

public final class Constants {

    public static final String PHONE_PATTERN = "^1(?:[358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8}$";
    public static final String PHONE_PATTERN_NULLABLE = "(?:^\\s*?$|" + PHONE_PATTERN + ")";
    public static final String URL_PATTERN = "(?:https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]";
    public static final String URL_PATTERN_WITH_PLACEHOLDER = "(?:https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_\\{\\}|!:,.;\\{\\}]+[-A-Za-z0-9+&@#/%=~_\\{\\}|]";
    public static final String URL_PLACEHOLDER_CODE = "{code}";
    public static final String URL_PLACEHOLDER_EXT_KEY1 = "{extKey1}";
    public static final String URL_PLACEHOLDER_EXT_KEY2 = "{extKey2}";

    private Constants() {

    }
}
