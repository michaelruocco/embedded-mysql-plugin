package uk.co.mruoc.mysql

import com.wix.mysql.EmbeddedMysql
import com.wix.mysql.config.Charset
import com.wix.mysql.config.DownloadConfig
import com.wix.mysql.config.MysqldConfig
import com.wix.mysql.distribution.Version

import java.util.concurrent.TimeUnit

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql
import static com.wix.mysql.config.DownloadConfig.aDownloadConfig
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig
import static com.wix.mysql.distribution.Version.v5_7_latest

class EmbeddedMysqlExtension {

    private static final String EMPTY_STRING = ""
    private static final int DEFAULT_MYSQL_PORT = 3306
    private static final String DEFAULT_USERNAME = "user"
    private static final Version DEFAULT_VERSION = v5_7_latest
    private static final Charset DEFAULT_CHARSET = Charset.defaults()
    private static final int DEFAULT_TIMEOUT_SECONDS = 30
    private static final String DEFAULT_TEMP_DIR = "build/mysql-temp/"

    private final ServerVariableValidator serverVariableValidator = new ServerVariableValidator()
    private final CharsetValidator charsetValidator = new CharsetValidator()
    private final CollationValidator collationValidator = new CollationValidator()
    private final VersionParser versionParser = new VersionParser()
    private final UrlParser urlParser = new UrlParser()

    private String databaseName = EMPTY_STRING
    private Set<String> schemaSet = new HashSet<String>()
    private int port = DEFAULT_MYSQL_PORT
    private String username = DEFAULT_USERNAME
    private String password = EMPTY_STRING
    private Version version = DEFAULT_VERSION
    private Charset charset = DEFAULT_CHARSET
    private int timeoutSeconds = DEFAULT_TIMEOUT_SECONDS
    private String tempDir = DEFAULT_TEMP_DIR
    private Map<String, Object> serverVars = new HashMap<String, Object>()
    private String cacheDirectoryPath
    private String baseDownloadUrl

    String getDatabaseName() {
        return databaseName
    }

    String getSchema() {
        return SchemaParser.toString(schemaSet)
    }

    void setSchema(String schema) {
        schemaSet = SchemaParser.parseSchema(schema)
    }

    int getPort() {
        return port
    }

    void setUrl(String url) {
        if (url == null)
            return
        port = urlParser.extractPort(url)
        databaseName = urlParser.extractDatabaseName(url)
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
        this.version = versionParser.parse(version)
    }

    Version getVersion() {
        return version
    }

    void setTimeoutSeconds(int timeoutSeconds) {
        this.timeoutSeconds = timeoutSeconds
    }

    int getTimeoutSeconds() {
        return timeoutSeconds
    }

    void setTempDir(String tempDir) {
        this.tempDir = tempDir
    }

    String getTempDir() {
        return tempDir
    }

    void setServerCharset(String charset) {
        if (charsetValidator.validate(charset))
            this.charset = Charset.aCharset(charset, this.charset.getCollate())
    }

    void setServerCollate(String collate) {
        if (collationValidator.validate(collate))
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

    String getCacheDirectoryPath() {
        return cacheDirectoryPath
    }

    void setCacheDirectoryPath(String cacheDirectoryPath) {
        this.cacheDirectoryPath = cacheDirectoryPath
    }

    String getBaseDownloadUrl() {
        return baseDownloadUrl
    }

    void setBaseDownloadUrl(String baseDownloadUrl) {
        this.baseDownloadUrl = baseDownloadUrl
    }

    EmbeddedMysql buildMysql() {
        def mysqlConfig = buildMysqlConfig()
        def downloadConfig = buildDownloadConfig()

        def mysql = anEmbeddedMysql(mysqlConfig, downloadConfig)

        if (!schemaSet.contains(databaseName))
            mysql.addSchema(databaseName)

        for (String schema : schemaSet)
            mysql.addSchema(schema)

        return mysql.start()
    }

    private MysqldConfig buildMysqlConfig() {
        def builder = aMysqldConfig(version)
                .withPort(port)
                .withUser(username, password)
                .withCharset(charset)
                .withTimeout(timeoutSeconds, TimeUnit.SECONDS)
                .withTempDir(tempDir)

        if (serverVars)
            serverVars.each { k, v -> builder.withServerVariable(k, v) }

        return builder.build()
    }

    private DownloadConfig buildDownloadConfig() {
        def builder = aDownloadConfig()

        if (cacheDirectoryPath) {
            println 'setting cache directory path ' + cacheDirectoryPath
            builder.withCacheDir(cacheDirectoryPath)
        }

        if (baseDownloadUrl) {
            println 'setting base download url ' + baseDownloadUrl
            builder.withBaseUrl(baseDownloadUrl)
        }

        return builder.build()
    }

}
