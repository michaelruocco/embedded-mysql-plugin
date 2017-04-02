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

    private final ServerVariableValidator serverVariableValidator = new ServerVariableValidator()
    private final CharsetValidator charsetValidator = new CharsetValidator()

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
        if (charsetValidator.validate(charset))
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
        if (vars == null)
            return

        for (e in vars)
            serverVariableValidator.validate(e.key, e.value)

        this.serverVars = vars
    }

}
