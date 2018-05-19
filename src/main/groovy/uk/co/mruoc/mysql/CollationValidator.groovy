package uk.co.mruoc.mysql

class CollationValidator {

    private static final def COLLATIONS = [
            "big5_chinese_ci", "dec8_swedish_ci", "cp850_general_ci", "hp8_english_ci",
            "koi8r_general_ci", "latin1_swedish_ci", "latin2_general_ci", "swe7_swedish_ci",
            "ascii_general_ci", "ujis_japanese_ci", "sjis_japanese_ci", "hebrew_general_ci",
            "tis620_thai_ci", "euckr_korean_ci", "koi8u_general_ci", "gb2312_chinese_ci",
            "greek_general_ci", "cp1250_general_ci", "gbk_chinese_ci", "latin5_turkish_ci",
            "armscii8_general_ci", "utf8_general_ci", "ucs2_general_ci", "cp866_general_ci",
            "keybcs2_general_ci", "macce_general_ci", "macroman_general_ci", "cp852_general_ci",
            "latin7_general_ci", "utf8mb4_general_ci", "cp1251_general_ci", "utf16_general_ci",
            "utf16le_general_ci", "cp1256_general_ci", "cp1257_general_ci", "utf32_general_ci",
            "binary", "geostd8_general_ci", "cp932_japanese_ci", "eucjpms_japanese_ci",
            "gb18030_chinese_ci"]

    static boolean validate(String collate) {
        if (collate?.trim() && COLLATIONS.contains(collate.toLowerCase()))
            return true
        throw new IllegalArgumentException(buildInvalidCollationMessage(collate))
    }

    private static String buildInvalidCollationMessage(String invalidCharset) {
        StringBuilder s = new StringBuilder()
        s.append("invalid collation specified: ")
        s.append(invalidCharset)
        s.append(" possible valid collations are: [")
        s.append(COLLATIONS.join(", "))
        s.append("]")
        return s.toString()
    }

}
