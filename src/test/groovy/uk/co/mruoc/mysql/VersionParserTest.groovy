package uk.co.mruoc.mysql

import org.junit.Test

import com.wix.mysql.distribution.Version

import static org.assertj.core.api.Assertions.assertThat
import static org.assertj.core.api.Assertions.catchThrowable

class VersionParserTest {

    private final VersionParser parser = new VersionParser()

    @Test
    void shouldThrowExceptionIfInvalidVersionSpecified() {
        def invalidVersion = "invalid version"

        Throwable thrown = catchThrowable({ parser.parse(invalidVersion) })

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("invalid version specified: " + invalidVersion)
    }

    @Test
    void shouldParseValidVersion() {
        def versionName = Version.v5_7_latest.name()

        def result = parser.parse(versionName)

        assertThat(result).isEqualTo(Version.v5_7_latest)
    }

}
