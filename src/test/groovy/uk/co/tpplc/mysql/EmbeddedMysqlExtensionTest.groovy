package uk.co.tpplc.mysql

import org.junit.Test

import static org.assertj.core.api.Assertions.assertThat

class EmbeddedMysqlExtensionTest {

    private static final String DATABASE_NAME = "test-database"
    private static final int PORT = 3306
    private static final String USERNAME = "root"
    private static final String PASSWORD = "1234"

    private EmbeddedMysqlExtension extension = new EmbeddedMysqlExtension()

    @Test
    void shouldSetDatabaseName() {
        extension.databaseName = DATABASE_NAME
        assertThat(extension.databaseName).isEqualTo(DATABASE_NAME)
    }

    @Test
    void shouldSetPort() {
        extension.port = PORT
        assertThat(extension.port).isEqualTo(PORT)
    }

    @Test
    void shouldSetUsername() {
        extension.username = USERNAME
        assertThat(extension.username).isEqualTo(USERNAME)
    }

    @Test
    void shouldSetPassword() {
        extension.password = PASSWORD
        assertThat(extension.password).isEqualTo(PASSWORD)
    }

}
