package uk.co.mruoc.mysql

import org.junit.Test

import static org.assertj.core.api.Assertions.assertThat

class SchemaParserTest {

    @Test
    void shouldReturnEmptySetForNull() {
        def set = SchemaParser.parseSchema(null)

        assertThat(set).isEmpty()
    }

    @Test
    void shouldReturnEmptySetForEmptyString() {
        def set = SchemaParser.parseSchema("")

        assertThat(set).isEmpty()
    }

    @Test
    void shouldReturnEmptySetForSpaces() {
        def set = SchemaParser.parseSchema(" ,    ")

        assertThat(set).isEmpty()
    }

    @Test
    void shouldReturnOneSchemaIgnoringEmpty() {
        def set = SchemaParser.parseSchema("  TeST ,    ")

        assertThat(set).contains("TeST")
    }

    @Test
    void shouldReturnOneSchema() {
        def set = SchemaParser.parseSchema("  TeST ")

        assertThat(set).contains("TeST")
    }

    @Test
    void shouldReturnSchemaSet() {
        def set = SchemaParser.parseSchema(" TeST1 ,  TEst2  ")

        assertThat(set).contains("TeST1", "TEst2")
    }

    @Test
    void shouldPreserveTheOrder() {
        def set = SchemaParser.parseSchema(" TeST1 ,  TEst2  ")

        assertThat(SchemaParser.toString(set)).is("TeST1,TEst2")
    }
    
    @Test
    void shouldBeEmpty() {
        def set = SchemaParser.parseSchema(" ,  ")

        assertThat(SchemaParser.toString(set)).is("")
    }

}
