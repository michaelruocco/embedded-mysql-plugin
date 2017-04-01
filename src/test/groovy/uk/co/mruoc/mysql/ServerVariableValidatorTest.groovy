package uk.co.mruoc.mysql

import org.junit.Test

import static com.googlecode.catchexception.apis.BDDCatchException.when
import static com.googlecode.catchexception.CatchException.caughtException
import static org.assertj.core.api.BDDAssertions.then
import static org.assertj.core.api.Assertions.assertThat

class ServerVariableValidatorTest {

    private static final EMPTY_NAME_MESSAGE = "server variable name should not be empty"
    private static final EMPTY_VALUE_MESSAGE = "value of server variable " + NAME + ": should not be empty"
    private static final INVALID_VALUE_TYPE_MESSAGE = "value of the server variable " + NAME + ": should be of type string, boolean or integer"

    private static final NAME = "name"
    private static final VALUE = "value"

    private final validator = new ServerVariableValidator()

    @Test
    void throwsIllegalArgumentExceptionIfNameIsNull() {
        when(validator).validate(null, VALUE)

        then(caughtException())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(EMPTY_NAME_MESSAGE)
    }

    @Test
    void throwsIllegalArgumentExceptionIfNameIsEmpty() {
        String value = ""

        when(validator).validate(value, VALUE)

        then(caughtException())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(EMPTY_NAME_MESSAGE)
    }

    @Test
    void throwsIllegalArgumentExceptionIfNameIsOnlyWhitespace() {
        String name = "  "

        when(validator).validate(name, VALUE)

        then(caughtException())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(EMPTY_NAME_MESSAGE)
    }

    @Test
    void throwsIllegalArgumentExceptionIfValueIsNull() {
        when(validator).validate(NAME, null)

        then(caughtException())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(EMPTY_VALUE_MESSAGE)
    }

    @Test
    void throwsIllegalArgumentExceptionIfValueIsEmpty() {
        String value = ""

        when(validator).validate(NAME, value)

        then(caughtException())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(EMPTY_VALUE_MESSAGE)
    }

    @Test
    void throwsIllegalArgumentExceptionIfValueIsOnlyWhitespace() {
        String value = "  "

        when(validator).validate(NAME, value)

        then(caughtException())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(EMPTY_VALUE_MESSAGE)
    }

    @Test
    void throwsIllegalArgumentExceptionIfValueIsNotStringBooleanOrIntegerValue() {
        float value = 10

        when(validator).validate(NAME, value)

        then(caughtException())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(INVALID_VALUE_TYPE_MESSAGE)
    }

    @Test
    void returnsTrueForNameWithStringValue() {
        String value = "my-value"

        assertThat(validator.validate(NAME, value)).isTrue()
    }

    @Test
    void returnsTrueForNameWithBooleanValue() {
        boolean value = false

        assertThat(validator.validate(NAME, value)).isTrue()
    }

    @Test
    void returnsTrueForNameWithIntegerValue() {
        int value = 10

        assertThat(validator.validate(NAME, value)).isTrue()
    }

}
