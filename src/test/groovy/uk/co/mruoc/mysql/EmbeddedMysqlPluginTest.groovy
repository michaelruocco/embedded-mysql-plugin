package uk.co.mruoc.mysql

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test

import static org.assertj.core.api.Assertions.assertThat

class EmbeddedMysqlPluginTest {

    private Project project = ProjectBuilder.builder().build()

    @Test
    void addsEmbeddedMysqlExtensionToProject() {
        applyPluginToProject()
        assertThat(project.extensions.embeddedMysql instanceof EmbeddedMysqlExtension).isTrue()
    }

    @Test
    void addsStartEmbeddedMysqlTaskToProject() {
        applyPluginToProject()
        assertThat(project.tasks.startEmbeddedMysql instanceof StartEmbeddedMysqlTask).isTrue()
    }

    @Test
    void addsStopEmbeddedMysqlTaskToProject() {
        applyPluginToProject()
        assertThat(project.tasks.stopEmbeddedMysql instanceof StopEmbeddedMysqlTask).isTrue()
    }

    private void applyPluginToProject() {
        project.plugins.apply(EmbeddedMysqlPlugin)
    }

}
