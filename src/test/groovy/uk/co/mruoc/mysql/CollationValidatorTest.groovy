package uk.co.mruoc.mysql

import org.junit.Test

import static org.assertj.core.api.Assertions.assertThat
import static org.assertj.core.api.Assertions.catchThrowable

class CollationValidatorTest {

    private final CollationValidator validator = new CollationValidator()

    @Test
    void shouldThrowExceptionIfNullCollationSpecified() {
        String collate = null

        Throwable thrown = catchThrowable({ validator.validate(collate) })

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("invalid collation specified: " + collate)
    }

    @Test
    void shouldThrowExceptionIfEmptyCollationSpecified() {
        String collate = ""

        Throwable thrown = catchThrowable({ validator.validate(collate) })

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("invalid collation specified: " + collate)
    }

    @Test
    void shouldThrowExceptionIfInvalidCollationSpecified() {
        String collate = "invalid"

        Throwable thrown = catchThrowable({ validator.validate(collate) })

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("invalid collation specified: " + collate)
    }

    @Test
    void shouldTrueForValidCollation() {
        String collate = "utf8mb4_general_ci"

        assertThat(validator.validate(collate)).isTrue()
    }

}
