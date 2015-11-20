package uk.co.mruoc.mysql

import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException
import com.wix.mysql.distribution.Version
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test

import java.sql.DriverManager

import static com.wix.mysql.distribution.Version.v5_6_23
import static org.assertj.core.api.Assertions.assertThat

class StartStopEmbeddedMysqlTaskTest {

    private static final def DATABASE_NAME = "databaseName";
    private static final def PORT = 3307;
    private static final def USERNAME = "root";
    private static final def PASSWORD = "";
    private static final Version VERSION = v5_6_23;

    private def project = ProjectBuilder.builder().build()

    @Test
    void shouldStartAndStopMysql() {
        configureExtension()

        executeStartTask()
        assertThat(mysqlRunning()).isTrue()

        executeStopTask()
        assertThat(mysqlRunning()).isFalse()
    }

    @Test
    void shouldStartSpecifiedMysqlVersion() {
        configureExtension()

        executeStartTask()
        assertThat(mysqlVersion).isEqualTo(format(VERSION))

        executeStopTask()
    }

    private configureExtension() {
        def extension = getExtension()
        extension.databaseName = DATABASE_NAME
        extension.port = PORT
        extension.username = USERNAME
        extension.password = PASSWORD
        extension.version = VERSION
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
            def connection = getConnection()
            try {
                return connection.isValid(1)
            } finally {
                connection.close()
            }
        } catch (CommunicationsException e) {
            return false
        }
    }

    private getMysqlVersion() {
        def connection = getConnection()
        try {
            def meta = connection.getMetaData()
            return meta.getDatabaseProductVersion()
        } finally {
            connection.close()
        }
    }

    private getConnection() {
        return DriverManager.getConnection(connectionString, USERNAME, PASSWORD)
    }

    private getConnectionString() {
        def s = new StringBuilder()
        s.append('jdbc:mysql://localhost:')
        s.append(PORT)
        s.append('/')
        s.append(DATABASE_NAME)
        return s.toString()
    }

    private format(Version version) {
        def name = version.name()
        name = name.replaceAll("_", ".")
        name = name.replaceAll("v", "")
        return name
    }

}
