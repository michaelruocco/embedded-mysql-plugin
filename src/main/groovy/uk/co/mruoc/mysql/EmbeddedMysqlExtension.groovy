package uk.co.mruoc.mysql

class EmbeddedMysqlExtension {

    private static final int DEFAULT_MYSQL_PORT = 3306;
    private static final String DEFAULT_USERNAME = "root"

    String databaseName = ""
    int port = DEFAULT_MYSQL_PORT
    String username = DEFAULT_USERNAME
    String password = ""

}
