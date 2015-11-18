package uk.co.tpplc.mysql

import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

import static org.assertj.core.api.Assertions.assertThat

class StartStopEmbeddedMysqlTaskTest {

    private static final def DATABASE_NAME = "databaseName";
    private static final def PORT = 3306;
    private static final def USERNAME = "root";
    private static final def PASSWORD = "";

    private def project = ProjectBuilder.builder().build()

    @Test
    void shouldStartAndStopMysql() {
        configureExtension()

        executeStartTask()
        assertThat(mysqlRunning()).isTrue()

        executeStopTask()
        assertThat(mysqlRunning()).isFalse()
    }

    private configureExtension() {
        def extension = getExtension()
        extension.databaseName = DATABASE_NAME
        extension.port = PORT
        extension.username = USERNAME
        extension.password = PASSWORD
    }

    private getExtension() {
        project.extensions.create('embeddedMysqlExtension', EmbeddedMysqlExtension)
    }

    private executeStartTask() {
        def task = project.task('startEmbeddedMysql', type: StartEmbeddedMysqlTask)
        task.execute()
    }

    private executeStopTask() {
        def task = project.task('stopEmbeddedMysql', type: StopEmbeddedMysqlTask)
        task.execute()
    }

    private mysqlRunning() {
        try {
            Class.forName("com.mysql.jdbc.Driver")
            Connection connection = DriverManager.getConnection(connectionString, USERNAME, PASSWORD)
            connection.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    private getConnectionString() {
        def s = new StringBuilder()
        s.append('jdbc:mysql://localhost:')
        s.append(PORT)
        s.append('/')
        s.append(DATABASE_NAME)
        return s.toString()
    }

}
