package uk.co.mruoc.mysql

import com.wix.mysql.distribution.Version
import org.apache.commons.lang3.StringUtils

import static com.wix.mysql.distribution.Version.v5_7_latest

class EmbeddedMysqlExtension {

    private static final def EMPTY_STRING = ""
    private static final def DEFAULT_MYSQL_PORT = 3306
    private static final def DEFAULT_USERNAME = "root"
    private static final def DEFAULT_VERSION = v5_7_latest

    private def databaseName = EMPTY_STRING
    private def port = DEFAULT_MYSQL_PORT
    private def username = DEFAULT_USERNAME
    private def password = EMPTY_STRING
    private def version = DEFAULT_VERSION

    public String getDatabaseName() {
        return databaseName
    }

    public int getPort() {
        return port
    }

    public void setUrl(String url) {
        if (url == null)
            return
        String cleanUrl = removeJdbcPrefix(url)
        URI uri = URI.create(cleanUrl)
        port = uri.getPort()
        databaseName = removeForwardSlash(uri.getPath())
    }

    public void setUsername(String username) {
        this.username = username
    }

    public String getUsername() {
        return username
    }

    public void setPassword(String password) {
        this.password = password
    }

    public String getPassword() {
        return password
    }

    public void setVersion(String version) {
        try {
            this.version = Version.valueOf(version)
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(getInvalidVersionMessage(version), e)
        }
    }

    public Version getVersion() {
        return version
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
