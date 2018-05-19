package uk.co.mruoc.mysql

class ServerVariableValidator {

    static boolean validate(String name, Object value) {
        validateName(name)
        validateValueType(name, value)
        if (isString(value))
            validateStringValue(name, value)
        return true
    }

    private static void validateName(String name) {
        if (!(name?.trim()))
            throw new IllegalArgumentException("server variable should have a name")
    }

    private static void validateValueType(String name, Object value) {
        if (value == null)
            throw new IllegalArgumentException("server variable " + name + " should have a value")

        if (!isValidType(value))
            throw new IllegalArgumentException("server variable " + name + " should be a string, boolean or integer")
    }

    private static boolean isValidType(Object value) {
        return isString(value) ||(value instanceof Boolean) || (value instanceof Integer)
    }

    private static void validateStringValue(String name, Object value) {
        String s = (String) value
        if (!s?.trim())
            throw new IllegalArgumentException("server variable " + name + " should have a value")
    }

    private static boolean isString(Object value) {
        return value instanceof String
    }


}
