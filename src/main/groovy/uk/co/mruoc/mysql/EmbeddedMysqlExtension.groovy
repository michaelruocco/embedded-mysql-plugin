package uk.co.mruoc.mysql

import com.wix.mysql.config.Charset
import com.wix.mysql.distribution.Version
import org.apache.commons.lang3.StringUtils

import static com.wix.mysql.distribution.Version.v5_7_latest

class EmbeddedMysqlExtension {

    private static final def CHARSETS = [
            "big5",     "dec8",     "cp850",    "hp8",
            "koi8r",    "latin1",   "latin2",   "swe7",
            "ascii",    "ujis",     "sjis",     "hebrew",
            "tis620",   "euckr",    "koi8u",    "gb2312",
            "greek",    "cp1250",   "gbk",      "latin5",
            "armscii8", "utf8",     "ucs2",     "cp866",
            "keybcs2",  "macce",    "macroman", "cp852",
            "latin7",   "utf8mb4",  "cp1251",   "utf16",
            "utf16le",  "cp1256",   "cp1257",   "utf32",
            "binary",   "geostd8",  "cp932",    "eucjpms",
            "gb18030"]

    private static final def COLLATIONS = [
            "big5_chinese_ci",      "dec8_swedish_ci",      "cp850_general_ci",     "hp8_english_ci",
            "koi8r_general_ci",     "latin1_swedish_ci",    "latin2_general_ci",    "swe7_swedish_ci",
            "ascii_general_ci",     "ujis_japanese_ci",     "sjis_japanese_ci",     "hebrew_general_ci",
            "tis620_thai_ci",       "euckr_korean_ci",      "koi8u_general_ci",     "gb2312_chinese_ci",
            "greek_general_ci",     "cp1250_general_ci",    "gbk_chinese_ci",       "latin5_turkish_ci",
            "armscii8_general_ci",  "utf8_general_ci",      "ucs2_general_ci",      "cp866_general_ci",
            "keybcs2_general_ci",   "macce_general_ci",     "macroman_general_ci",  "cp852_general_ci",
            "latin7_general_ci",    "utf8mb4_general_ci",   "cp1251_general_ci",    "utf16_general_ci",
            "utf16le_general_ci",   "cp1256_general_ci",    "cp1257_general_ci",    "utf32_general_ci",
            "binary",               "geostd8_general_ci",   "cp932_japanese_ci",    "eucjpms_japanese_ci",
            "gb18030_chinese_ci"]

    private static final def EMPTY_STRING = ""
    private static final def DEFAULT_MYSQL_PORT = 3306
    private static final def DEFAULT_USERNAME = "user"
    private static final def DEFAULT_VERSION = v5_7_latest

    private def databaseName = EMPTY_STRING
    private def port = DEFAULT_MYSQL_PORT
    private def username = DEFAULT_USERNAME
    private def password = EMPTY_STRING
    private def version = DEFAULT_VERSION
    private def charset = Charset.defaults()
    private def serverVars = new HashMap<String, Object>()

    String getDatabaseName() {
        return databaseName
    }

    int getPort() {
        return port
    }

    void setUrl(String url) {
        if (url == null)
            return
        String cleanUrl = removeJdbcPrefix(url)
        URI uri = URI.create(cleanUrl)
        port = uri.getPort()
        databaseName = removeForwardSlash(uri.getPath())
    }

    void setUsername(String username) {
        this.username = username
    }

    String getUsername() {
        return username
    }

    void setPassword(String password) {
        this.password = password
    }

    String getPassword() {
        return password
    }

    void setVersion(String version) {
        try {
            this.version = Version.valueOf(version)
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(getInvalidVersionMessage(version), e)
        }
    }

    Version getVersion() {
        return version
    }

    Charset getCharset() {
        return charset
    }

    void setServerCharset(String charset) {
        if (charset?.trim() && CHARSETS.contains(charset.toLowerCase())) {
            this.charset = Charset.aCharset(charset, this.charset.getCharset())
        } else {
            throw new IllegalArgumentException(
                    "Unsupported charset, valid values are: " + CHARSETS.join(", ") +
                            ". Default value is " + Charset.defaults().getCharset() + ".")
        }
    }

    void setServerCollate(String collate) {
        if (collate?.trim() && COLLATIONS.contains(collate.toLowerCase())) {
            this.charset = Charset.aCharset(charset.getCharset(), collate)
        } else {
            throw new IllegalArgumentException(
                    "Unsupported collation, valid values are: " + COLLATIONS.join(", ") +
                            ". Default value is " + Charset.defaults().getCollate() + ".")
        }
    }

    String getServerCharset() {
        return charset.charset
    }

    String getServerCollate() {
        return charset.collate
    }

    Map<String, Object> getServerVars() {
        return serverVars
    }

    void setServerVars(Map<String, Object> vars) {
        if (vars) {
            for (e in vars) {
                validateServerVar(e.key, e.value)
            }
            this.serverVars = vars
        }
    }

    private void validateServerVar(String key, Object value) {
        if (!(key?.trim())) {
            throw new IllegalArgumentException("Server variable name should not be empty")

        } else if ((value == null) || (value instanceof String && !(value?.trim()))) {
            throw new IllegalArgumentException("Value of the server variable " + key + " should not be empty")

        } else if (!(value instanceof String) && !(value instanceof Boolean) && !(value instanceof Integer)) {
            throw new IllegalArgumentException("Unsupported value for the server variable " + key +
                    ". Value should be of type string, boolean or int.")
        }
    }

    private String getInvalidVersionMessage(String invalidVersion) {
        StringBuilder s = new StringBuilder()
        s.append("invalid version specified: ")
        s.append(invalidVersion)
        s.append(" possible valid versions are: [")
        for (Version version : Version.values()) {
            s.append(" ")
            s.append(version.name())
            s.append(",")
        }
        return replaceLastCommaWithSquareBracket(s)
    }

    private String replaceLastCommaWithSquareBracket(StringBuilder s) {
        if (s.contains(",")) {
            int commaIndex = s.lastIndexOf(",")
            s.replace(commaIndex, commaIndex, " ]")
            return StringUtils.removeEnd(s.toString(), ",")
        }
        return s.toString();
    }

    private String removeJdbcPrefix(String s) {
        return s.replaceAll("jdbc:", "")
    }

    private String removeForwardSlash(String s) {
        return s.replaceAll("/", "")
    }

}
