package uk.co.mruoc.mysql

class CharsetValidator {

    private static final def CHARSETS = [
            "big5", "dec8", "cp850", "hp8",
            "koi8r", "latin1", "latin2", "swe7",
            "ascii", "ujis", "sjis", "hebrew",
            "tis620", "euckr", "koi8u", "gb2312",
            "greek", "cp1250", "gbk", "latin5",
            "armscii8", "utf8", "ucs2", "cp866",
            "keybcs2", "macce", "macroman", "cp852",
            "latin7", "utf8mb4", "cp1251", "utf16",
            "utf16le", "cp1256", "cp1257", "utf32",
            "binary", "geostd8", "cp932", "eucjpms",
            "gb18030"]

    boolean validate(String charset) {
        if (charset?.trim() && CHARSETS.contains(charset.toLowerCase()))
            return true
        throw new IllegalArgumentException(buildInvalidCharsetMessage(charset))
    }

    private static String buildInvalidCharsetMessage(String invalidCharset) {
        StringBuilder s = new StringBuilder()
        s.append("invalid charset specified: ")
        s.append(invalidCharset)
        s.append(" possible valid charsets are: [")
        s.append(CHARSETS.join(", "))
        s.append("]")
        return s.toString()
    }

}
