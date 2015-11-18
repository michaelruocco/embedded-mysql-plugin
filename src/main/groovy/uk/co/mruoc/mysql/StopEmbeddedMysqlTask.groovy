package uk.co.mruoc.mysql

import com.wix.mysql.EmbeddedMysql
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class StopEmbeddedMysqlTask extends DefaultTask {

    StopEmbeddedMysqlTask() {
        description 'stops an embedded mysql process'
    }

    @TaskAction
    def run() {
        def mysql = (EmbeddedMysql) project.extensions.extraProperties.get("mysqlProcess")
        mysql.stop()
    }

}
