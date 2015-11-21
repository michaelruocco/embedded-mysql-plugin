package uk.co.mruoc.mysql

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig

class StartEmbeddedMysqlTask extends DefaultTask {

    StartEmbeddedMysqlTask() {
        description 'starts an embedded mysql process'
    }

    @TaskAction
    def run() {
        def config = aMysqldConfig(extension.version)
                .withPort(extension.port)
                .withUser(extension.username, extension.password)
                .build();

        def mysql = anEmbeddedMysql(config)
                .addSchema(extension.databaseName)
                .start();

        project.extensions.extraProperties.set('mysqlProcess', mysql)
    }

    def getExtension() {
        return project.extensions.findByName('embeddedMysql') as EmbeddedMysqlExtension
    }

}
