package uk.co.mruoc.mysql

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig
import static com.wix.mysql.distribution.Version.v5_6_22

class StartEmbeddedMysqlTask extends DefaultTask {

    StartEmbeddedMysqlTask() {
        description 'starts an embedded mysql process'
    }

    @TaskAction
    def run() {
        def config = aMysqldConfig(v5_6_22)
                .withPort(extension.port)
                .withUser(extension.username, extension.password)
                .build();

        def mysql = anEmbeddedMysql(config)
                .addSchema(extension.databaseName)
                .start();

        project.extensions.extraProperties.set('mysqlProcess', mysql)
    }

    def getExtension() {
        return project.extensions.findByName('embeddedMysqlExtension') as EmbeddedMysqlExtension
    }

}
