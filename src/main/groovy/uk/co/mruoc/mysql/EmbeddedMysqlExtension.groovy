package uk.co.mruoc.mysql

import com.wix.mysql.config.Charset
import com.wix.mysql.distribution.Version

import static com.wix.mysql.distribution.Version.v5_7_latest

class EmbeddedMysqlExtension {

    private static final def EMPTY_STRING = ""
    private static final def DEFAULT_MYSQL_PORT = 3306
    private static final def DEFAULT_USERNAME = "user"
    private static final def DEFAULT_VERSION = v5_7_latest
    private static final def DEFAULT_CHARSET = Charset.defaults()

    private def databaseName = EMPTY_STRING
    private def port = DEFAULT_MYSQL_PORT
    private def username = DEFAULT_USERNAME
    private def password = EMPTY_STRING
    private def version = DEFAULT_VERSION
    private def charset = DEFAULT_CHARSET
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
        port = UrlParser.extractPort(url)
        databaseName = UrlParser.extractDatabaseName(url)
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
        this.version = VersionParser.parse(version)
    }

    Version getVersion() {
        return version
    }

    Charset getCharset() {
        return charset
    }

    void setServerCharset(String charset) {
        if (CharsetValidator.validate(charset))
            this.charset = Charset.aCharset(charset, this.charset.getCollate())
    }

    void setServerCollate(String collate) {
        if (CollateValidator.validate(collate))
            this.charset = Charset.aCharset(this.charset.getCharset(), collate)
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

}
