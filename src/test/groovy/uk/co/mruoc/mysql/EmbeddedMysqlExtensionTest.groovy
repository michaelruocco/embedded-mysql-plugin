package uk.co.mruoc.mysql

import org.junit.Test

import static com.googlecode.catchexception.CatchException.caughtException
import static com.googlecode.catchexception.apis.BDDCatchException.then
import static com.googlecode.catchexception.apis.BDDCatchException.when
import static com.wix.mysql.distribution.Version.v5_6_latest
import static com.wix.mysql.distribution.Version.v5_7_latest
import static org.assertj.core.api.Assertions.assertThat

class EmbeddedMysqlExtensionTest {

    private static final def DATABASE_NAME = "databaseName"
    private static final def OVERRIDE_PORT = 3307
    private static final def URL = "jdbc:mysql://localhost:" + OVERRIDE_PORT + "/" + DATABASE_NAME

    private static final def DEFAULT_USERNAME = "root"
    private static final def DEFAULT_VERSION = v5_7_latest
    private static final def DEFAULT_PORT = 3306

    private static final def OVERRIDE_USERNAME = "anotherUser"
    private static final def OVERRIDE_VERSION = v5_6_latest

    private static final def PASSWORD = "password"

    private def extension = new EmbeddedMysqlExtension()

    @Test
    public void usernameDefaultToRoot() {
        assertThat(extension.username).isEqualTo(DEFAULT_USERNAME)
    }

    @Test
    public void passwordShouldDefaultToEmpty() {
        assertThat(extension.password).isEmpty()
    }

    @Test
    public void portShouldDefaultToEmpty() {
        assertThat(extension.port).isEqualTo(DEFAULT_PORT)
    }

    @Test
    public void databaseNameShouldDefaultToEmpty() {
        assertThat(extension.databaseName).isEmpty()
    }

    @Test
    public void versionShouldDefaultToDefaultVersion() {
        assertThat(extension.version).isEqualTo(DEFAULT_VERSION)
    }

    @Test
    public void shouldSetDatabaseNameFromUrl() {
        extension.url = URL
        assertThat(extension.databaseName).isEqualTo(DATABASE_NAME)
    }

    @Test
    public void shouldHandleSettingNullUrl() {
        extension.url = null
        assertThat(extension.port).isEqualTo(DEFAULT_PORT)
        assertThat(extension.databaseName).isEmpty()
    }

    @Test
    public void shouldSetPortFromUrl() {
        extension.url = URL
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

    private static String buildExpectedInvalidVersionMessage(String invalidVersion) {
        StringBuilder s = new StringBuilder()
        s.append("invalid version specified: ")
        s.append(invalidVersion)
        s.append(" possible valid versions are: [ ");
        s.append("v5_5_40, ");
        s.append("v5_6_21, ");
        s.append("v5_6_22, ");
        s.append("v5_6_23, ");
        s.append("v5_6_24, ");
        s.append("v5_6_latest, ");
        s.append("v5_7_10, ");
        s.append("v5_7_latest ]");
        return s.toString()
    }

}
