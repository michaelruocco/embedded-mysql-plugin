package uk.co.mruoc.mysql

import org.junit.Test

import static com.googlecode.catchexception.apis.BDDCatchException.when
import static com.googlecode.catchexception.CatchException.caughtException
import static org.assertj.core.api.BDDAssertions.then
import static org.assertj.core.api.Assertions.assertThat

class ServerVariableValidatorTest {

    private static NAME = "name"
    private static VALUE = "value"

    private validator = new ServerVariableValidator()

    @Test
    void throwsIllegalArgumentExceptionIfNameIsNull() {
        when(validator).validate(null, VALUE)

        then(caughtException())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("server variable name should not be empty")
    }

    @Test
    void throwsIllegalArgumentExceptionIfNameIsEmpty() {
        when(validator).validate("", VALUE)

        then(caughtException())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("server variable name should not be empty")
    }

    @Test
    void throwsIllegalArgumentExceptionIfNameIsOnlyWhitespace() {
        when(validator).validate("  ", VALUE)

        then(caughtException())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("server variable name should not be empty")
    }

    @Test
    void throwsIllegalArgumentExceptionIfValueIsNull() {
        when(validator).validate(NAME, null)

        then(caughtException())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("value of server variable " + NAME + ": should not be empty")
    }

    @Test
    void throwsIllegalArgumentExceptionIfValueIsEmpty() {
        when(validator).validate(NAME, "")

        then(caughtException())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("value of server variable " + NAME + ": should not be empty")
    }

    @Test
    void throwsIllegalArgumentExceptionIfValueIsOnlyWhitespace() {
        when(validator).validate(NAME, "  ")

        then(caughtException())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("value of server variable " + NAME + ": should not be empty")
    }

    @Test
    void throwsIllegalArgumentExceptionIfValueIsNotStringBooleanOrIntegerValue() {
        float value = 10;
        when(validator).validate(NAME, value)

        then(caughtException())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("unsupported value for the server variable " + NAME + ": should be of type string, boolean or integer")
    }

    @Test
    void returnsTrueForNameWithStringValue() {
        String value = "my-value"

        assertThat(validator.validate(NAME, value)).isTrue()
    }

    @Test
    void returnsTrueForNameWithBooleanValue() {
        String value = false

        assertThat(validator.validate(NAME, value)).isTrue()
    }

    @Test
    void returnsTrueForNameWithIntegerValue() {
        int value = 10

        assertThat(validator.validate(NAME, value)).isTrue()
    }

}
