package uk.co.mruoc.mysql

class SchemaParser {
    private static final String SCHEMA_DELIMITER = ','

    static Set<String> parseSchema(String schemaStr) {
        Set<String> result = new HashSet<>()

        if (!schemaStr?.trim())
            return result

        if (schemaStr.contains(SCHEMA_DELIMITER)) {
            for (String schema : schemaStr.split(SCHEMA_DELIMITER)) {
                if (schema?.trim()) {
                    result.add(schema.trim())
                }
            }
        } else {
            result.add(schemaStr.trim())
        }

        return result
    }

    static String toString(Set<String> schemaSet) {
        return schemaSet.join(SCHEMA_DELIMITER)
    }
}
