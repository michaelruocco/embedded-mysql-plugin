package uk.co.mruoc.mysql

import com.wix.mysql.EmbeddedMysql
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql

class StartEmbeddedMysqlTask extends DefaultTask {

    private static final def MYSQL_PROCESS_PROPERTY_NAME = "mysqlProcess"
    private static final def MYSQL_EXTENSION_NAME = "embeddedMysql"

    def StartEmbeddedMysqlTask() {
        description 'starts an embedded mysql process'
    }

    @TaskAction
    def run() {
        def config = extension.buildConfig()

        def mysql = anEmbeddedMysql(config)
                .addSchema(extension.databaseName)
                .start()

        setMysqlProcessProperty(mysql)
    }

    def getExtension() {
        def extensions = project.extensions
        return extensions.findByName(MYSQL_EXTENSION_NAME) as EmbeddedMysqlExtension
    }

    def setMysqlProcessProperty(EmbeddedMysql embeddedMysql) {
        return (EmbeddedMysql) extraProperties.set(MYSQL_PROCESS_PROPERTY_NAME, embeddedMysql)
    }

    def getExtraProperties() {
        def extensions = project.extensions
        return extensions.extraProperties
    }

}
