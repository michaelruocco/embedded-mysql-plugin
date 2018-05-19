package uk.co.mruoc.mysql

import org.junit.Test

import static org.assertj.core.api.Assertions.catchThrowable
import static org.assertj.core.api.Assertions.assertThat

class ServerVariableValidatorTest {

    private static final EMPTY_NAME_MESSAGE = "server variable should have a name"
    private static final EMPTY_VALUE_MESSAGE = "server variable " + NAME + " should have a value"
    private static final INVALID_VALUE_TYPE_MESSAGE = "server variable " + NAME + " should be a string, boolean or integer"

    private static final NAME = "name"
    private static final VALUE = "value"

    private final validator = new ServerVariableValidator()

    @Test
    void throwsIllegalArgumentExceptionIfNameIsNull() {
        Throwable thrown = catchThrowable({ validator.validate(null, VALUE) })

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(EMPTY_NAME_MESSAGE)
    }

    @Test
    void throwsIllegalArgumentExceptionIfNameIsEmpty() {
        String value = ""

        Throwable thrown = catchThrowable({ validator.validate(value, VALUE) })

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(EMPTY_NAME_MESSAGE)
    }

    @Test
    void throwsIllegalArgumentExceptionIfNameIsOnlyWhitespace() {
        String name = "  "

        Throwable thrown = catchThrowable({ validator.validate(name, VALUE) })

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(EMPTY_NAME_MESSAGE)
    }

    @Test
    void throwsIllegalArgumentExceptionIfValueIsNull() {
        Throwable thrown = catchThrowable({ validator.validate(NAME, null) })

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(EMPTY_VALUE_MESSAGE)
    }

    @Test
    void throwsIllegalArgumentExceptionIfValueIsEmpty() {
        String value = ""

        Throwable thrown = catchThrowable({ validator.validate(NAME, value) })

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(EMPTY_VALUE_MESSAGE)
    }

    @Test
    void throwsIllegalArgumentExceptionIfValueIsOnlyWhitespace() {
        String value = "  "

        Throwable thrown = catchThrowable({ validator.validate(NAME, value) })

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(EMPTY_VALUE_MESSAGE)
    }

    @Test
    void throwsIllegalArgumentExceptionIfValueIsNotStringBooleanOrIntegerValue() {
        float value = 10

        Throwable thrown = catchThrowable({ validator.validate(NAME, value) })

        assertThat(thrown)
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
