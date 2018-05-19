package uk.co.mruoc.mysql

import com.wix.mysql.EmbeddedMysql
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class StopEmbeddedMysqlTask extends DefaultTask {

    private static final def MYSQL_PROCESS_PROPERTY_NAME = "mysqlProcess"

    StopEmbeddedMysqlTask() {
        description 'stops an embedded mysql process'
    }

    @TaskAction
    def run() {
        if (hasMysqlProcessProperty()) {
            def mysql = getMysqlProcessProperty()
            mysql.stop()
        }
    }

    def hasMysqlProcessProperty() {
        return extraProperties.has(MYSQL_PROCESS_PROPERTY_NAME)
    }

    def getMysqlProcessProperty() {
        return (EmbeddedMysql) extraProperties.get(MYSQL_PROCESS_PROPERTY_NAME)
    }

    def getExtraProperties() {
        def extensions = project.extensions
        return extensions.extraProperties
    }

}
