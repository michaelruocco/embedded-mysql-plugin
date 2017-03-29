package uk.co.mruoc.mysql

import org.gradle.api.Plugin
import org.gradle.api.Project

class EmbeddedMysqlPlugin implements Plugin<Project>  {

    @Override
    void apply(Project project) {
        project.extensions.create('embeddedMysql', EmbeddedMysqlExtension)
        project.task('startEmbeddedMysql', type: StartEmbeddedMysqlTask)
        project.task('stopEmbeddedMysql', type: StopEmbeddedMysqlTask)
    }

}
