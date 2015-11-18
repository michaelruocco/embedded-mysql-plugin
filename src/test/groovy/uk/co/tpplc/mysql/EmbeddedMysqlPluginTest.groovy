package uk.co.tpplc.mysql

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test

import static org.assertj.core.api.Assertions.assertThat

class EmbeddedMysqlPluginTest {

    private Project project = ProjectBuilder.builder().build()

    @Test
    public void addsEmbeddedMysqlExtensionToProject() {
        applyPluginToProject()
        assertThat(project.extensions.embeddedMysql instanceof EmbeddedMysqlExtension).isTrue()
    }

    private void applyPluginToProject() {
        project.plugins.apply(EmbeddedMysqlPlugin)
    }

}
