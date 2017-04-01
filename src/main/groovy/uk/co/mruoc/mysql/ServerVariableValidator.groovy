package uk.co.mruoc.mysql

class ServerVariableValidator {

    boolean validate(String name, Object value) {
        if (!(name?.trim())) {
            throw new IllegalArgumentException("server variable name should not be empty")

        } else if ((value == null) || (value instanceof String && !(value?.trim()))) {
            throw new IllegalArgumentException("value of server variable " + name + ": should not be empty")

        } else if (!(value instanceof String) && !(value instanceof Boolean) && !(value instanceof Integer)) {
            throw new IllegalArgumentException("value of the server variable " + name +
                    ": should be of type string, boolean or integer")
        }
        return true
    }

}
