package uk.co.mruoc.mysql

import com.wix.mysql.distribution.Version
import org.junit.Test

import static com.wix.mysql.distribution.Version.v5_6_latest
import static org.assertj.core.api.Assertions.assertThat
import static com.wix.mysql.distribution.Version.v5_6_22

class EmbeddedMysqlExtensionTest {

    private static final int DEFAULT_MYSQL_PORT = 3306
    private static final String DEFAULT_USERNAME = "root"
    private static final Version DEFAULT_VERSION = v5_6_22

    private static final int OVERRIDE_PORT = 1234
    private static final String OVERRIDE_USERNAME = "anotherUser"
    private static final Version OVERRIDE_VERSION = v5_6_latest

    private static final String DATABASE_NAME = "databaseName"
    private static final String PASSWORD = "password"

    private EmbeddedMysqlExtension extension = new EmbeddedMysqlExtension()

    @Test
    public void databaseNameShouldDefaultToEmpty() {
        assertThat(extension.databaseName).isEmpty()
    }

    @Test
    public void portShouldDefaultToDefaultMysqlPort() {
        assertThat(extension.port).isEqualTo(DEFAULT_MYSQL_PORT)
    }

    @Test
    public void usernameDefaultToRoot() {
        assertThat(extension.username).isEqualTo(DEFAULT_USERNAME)
    }

    @Test
    public void passwordShouldDefaultToEmpty() {
        assertThat(extension.password).isEmpty()
    }

    @Test
    public void versionShouldDefaultToFivePointSixPointTwoTwo() {
        assertThat(extension.version).isEqualTo(v5_6_22)
    }

    @Test
    public void shouldSetDatabaseName() {
        extension.databaseName = DATABASE_NAME
        assertThat(extension.databaseName).isEqualTo(DATABASE_NAME)
    }

    @Test
    public void shouldSetPort() {
        extension.port = OVERRIDE_PORT
        assertThat(extension.port).isEqualTo(OVERRIDE_PORT)
    }

    @Test
    public void shouldSetUsername() {
        extension.username = OVERRIDE_USERNAME
        assertThat(extension.username).isEqualTo(OVERRIDE_USERNAME)
    }

    @Test
    public void shouldSetPassword() {
        extension.password = PASSWORD
        assertThat(extension.password).isEqualTo(PASSWORD)
    }

    @Test
    public void shouldSetVersion() {
        extension.version = OVERRIDE_VERSION
        assertThat(extension.version).isEqualTo(OVERRIDE_VERSION)
    }

}
