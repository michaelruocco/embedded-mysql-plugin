package uk.co.mruoc.mysql

import org.junit.Test

import static com.googlecode.catchexception.CatchException.caughtException
import static com.googlecode.catchexception.apis.BDDCatchException.when
import static org.assertj.core.api.BDDAssertions.then
import static org.assertj.core.api.Assertions.assertThat

class CharsetValidatorTest {

    private final CharsetValidator validator = new CharsetValidator()

    @Test
    void shouldThrowExceptionIfNullCharsetSpecified() {
        String charset = null

        when(validator).validate(charset)

        then(caughtException())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("invalid charset specified: " + charset)
    }

    @Test
    void shouldThrowExceptionIfEmptyCharsetSpecified() {
        String charset = ""

        when(validator).validate(charset)

        then(caughtException())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("invalid charset specified: " + charset)
    }

    @Test
    void shouldThrowExceptionIfInvalidCharsetSpecified() {
        String charset = "invalid"

        when(validator).validate(charset)

        then(caughtException())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("invalid charset specified: " + charset)
    }

    @Test
    void shouldTrueForValidCharset() {
        String charset = "utf8mb4"

        assertThat(validator.validate(charset)).isTrue()
    }

}
