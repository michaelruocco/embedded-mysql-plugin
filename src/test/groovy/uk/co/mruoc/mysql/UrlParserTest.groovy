package uk.co.mruoc.mysql

import org.junit.Test

import static org.assertj.core.api.Assertions.assertThat

class UrlParserTest {

    private static final def DATABASE_NAME = "databaseName"
    private static final def PORT = 3306

    private static final def URL = "jdbc:mysql://localhost:" + PORT + "/" + DATABASE_NAME

    private final UrlParser parser = new UrlParser()

    @Test
    void shouldExtractPortFromUrl() {
        def port = parser.extractPort(URL)

        assertThat(port).isEqualTo(PORT)
    }

    @Test
    void shouldExtractDatabaseNameFromUrl() {
        def databaseName = parser.extractDatabaseName(URL)

        assertThat(databaseName).isEqualTo(DATABASE_NAME)
    }

    @Test
    void shouldReturnPortMinusOneIfUrlIsInvalid() {
        def invalidUrl = "invalid"

        assertThat(parser.extractPort(invalidUrl)).isEqualTo(-1)
    }

    @Test
    void shouldReturnUrlDatabaseNameIfUrlIsInvalid() {
        def invalidUrl = "test/invalid"

        assertThat(parser.extractDatabaseName(invalidUrl)).isEqualTo("testinvalid")
    }

}
