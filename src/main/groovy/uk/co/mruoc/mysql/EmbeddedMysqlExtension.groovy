package uk.co.mruoc.mysql

import com.wix.mysql.distribution.Version

import static com.wix.mysql.distribution.Version.v5_6_22

class EmbeddedMysqlExtension {

    private static final int DEFAULT_MYSQL_PORT = 3306;
    private static final String DEFAULT_USERNAME = "root"
    private static final Version DEFAULT_VERSION = v5_6_22

    String databaseName = ""
    int port = DEFAULT_MYSQL_PORT
    String username = DEFAULT_USERNAME
    String password = ""
    Version version = DEFAULT_VERSION

}
