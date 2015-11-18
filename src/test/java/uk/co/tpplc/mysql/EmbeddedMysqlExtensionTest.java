package uk.co.tpplc.mysql;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EmbeddedMysqlExtensionTest {

    private static final String DATABASE_NAME = "test-database";
    private static final int PORT = 3306;
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1234";

    private EmbeddedMysqlExtension extension = new EmbeddedMysqlExtension();

    @Test
    public void shouldSetDatabaseName() {
        extension.setDatabaseName(DATABASE_NAME);
        assertThat(extension.getDatabaseName()).isEqualTo(DATABASE_NAME);
    }

    @Test
    public void shouldSetPort() {
        extension.setPort(PORT);
        assertThat(extension.getPort()).isEqualTo(PORT);
    }

    @Test
    public void shouldSetUsername() {
        extension.setUsername(USERNAME);
        assertThat(extension.getUsername()).isEqualTo(USERNAME);
    }

    @Test
    public void shouldSetPassword() {
        extension.setPassword(PASSWORD);
        assertThat(extension.getPassword()).isEqualTo(PASSWORD);
    }

}
