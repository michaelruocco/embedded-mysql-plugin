package uk.co.mruoc.mysql

import org.junit.Test

import static org.assertj.core.api.Assertions.assertThatThrownBy
import static org.assertj.core.api.Assertions.catchThrowable
import static org.assertj.core.api.Assertions.assertThat

class CharsetValidatorTest {

    private final CharsetValidator validator = new CharsetValidator()

    @Test
    void shouldThrowExceptionIfNullCharsetSpecified() {
        String charset = null

        assertThatThrownBy({ validator.validate(charset) })
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("invalid charset specified: " + charset)
    }

    @Test
    void shouldThrowExceptionIfEmptyCharsetSpecified() {
        String charset = ""

        Throwable thrown = catchThrowable({ validator.validate(charset) })

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("invalid charset specified: " + charset)
    }

    @Test
    void shouldThrowExceptionIfInvalidCharsetSpecified() {
        String charset = "invalid"

        Throwable thrown = catchThrowable({ validator.validate(charset) })

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("invalid charset specified: " + charset)
    }

    @Test
    void shouldTrueForValidCharset() {
        String charset = "utf8mb4"

        assertThat(validator.validate(charset)).isTrue()
    }

}
