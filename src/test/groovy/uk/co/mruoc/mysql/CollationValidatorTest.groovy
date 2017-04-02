package uk.co.mruoc.mysql

import org.junit.Test

import static com.googlecode.catchexception.CatchException.caughtException
import static com.googlecode.catchexception.apis.BDDCatchException.when
import static org.assertj.core.api.Assertions.assertThat
import static org.assertj.core.api.BDDAssertions.then

class CollationValidatorTest {

    private final CollationValidator validator = new CollationValidator()

    @Test
    void shouldThrowExceptionIfNullCollationSpecified() {
        String collate = null

        when(validator).validate(collate)

        then(caughtException())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("invalid collation specified: " + collate)
    }

    @Test
    void shouldThrowExceptionIfEmptyCollationSpecified() {
        String collate = ""

        when(validator).validate(collate)

        then(caughtException())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("invalid collation specified: " + collate)
    }

    @Test
    void shouldThrowExceptionIfInvalidCollationSpecified() {
        String collate = "invalid"

        when(validator).validate(collate)

        then(caughtException())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("invalid collation specified: " + collate)
    }

    @Test
    void shouldTrueForValidCollation() {
        String collate = "utf8mb4_general_ci"

        assertThat(validator.validate(collate)).isTrue()
    }

}
