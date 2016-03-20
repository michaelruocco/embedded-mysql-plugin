package uk.co.mruoc.mysql

import org.junit.Test

import static com.googlecode.catchexception.CatchException.caughtException
import static com.googlecode.catchexception.apis.BDDCatchException.then
import static com.googlecode.catchexception.apis.BDDCatchException.when
import static com.wix.mysql.distribution.Version.v5_6_22
import static com.wix.mysql.distribution.Version.v5_6_latest
import static org.assertj.core.api.Assertions.assertThat

class EmbeddedMysqlExtensionTest {

    private static final def DEFAULT_MYSQL_PORT = 3306
    private static final def DEFAULT_USERNAME = "root"
    private static final def DEFAULT_VERSION = v5_6_22

    private static final def OVERRIDE_PORT = 1234
    private static final def OVERRIDE_USERNAME = "anotherUser"
    private static final def OVERRIDE_VERSION = v5_6_latest

    private static final def DATABASE_NAME = "databaseName"
    private static final def PASSWORD = "password"

    private def extension = new EmbeddedMysqlExtension()

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
    public void versionShouldDefaultToDefaultVersion() {
        assertThat(extension.version).isEqualTo(DEFAULT_VERSION)
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
        extension.version = OVERRIDE_VERSION.name()
        assertThat(extension.version).isEqualTo(OVERRIDE_VERSION)
    }

    @Test
    public void shouldThrowExceptionIfInvalidVersionSpecified() {
        def invalidVersion = "invalid version"
        when(extension).setVersion(invalidVersion)

        then(caughtException())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(buildExpectedInvalidVersionMessage(invalidVersion))
    }

    private String buildExpectedInvalidVersionMessage(String invalidVersion) {
        StringBuilder s = new StringBuilder()
        s.append("invalid version specified: ")
        s.append(invalidVersion)
        s.append(" possible valid versions are: [ ");
        s.append("v5_5_40, ");
        s.append("v5_6_21, ");
        s.append("v5_6_22, ");
        s.append("v5_6_23, ");
        s.append("v5_6_24, ");
        s.append("v5_7_10, ");
        s.append("v5_7_latest, ");
        s.append("v5_6_latest ]");
        return s.toString()
    }

}
