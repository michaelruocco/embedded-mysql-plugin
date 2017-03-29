package uk.co.mruoc.mysql

class ServerVariableValidator {

    static boolean validate(String name, Object value) {
        if (!(name?.trim())) {
            throw new IllegalArgumentException("Server variable name should not be empty")

        } else if ((value == null) || (value instanceof String && !(value?.trim()))) {
            throw new IllegalArgumentException("Value of the server variable " + name + " should not be empty")

        } else if (!(value instanceof String) && !(value instanceof Boolean) && !(value instanceof Integer)) {
            throw new IllegalArgumentException("Unsupported value for the server variable " + name +
                    ". Value should be of type string, boolean or int.")
        }
    }

}
