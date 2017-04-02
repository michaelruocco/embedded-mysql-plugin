package uk.co.mruoc.mysql

import org.junit.Test

import com.wix.mysql.distribution.Version

import static com.googlecode.catchexception.CatchException.caughtException
import static com.googlecode.catchexception.apis.BDDCatchException.when
import static org.assertj.core.api.BDDAssertions.then
import static org.assertj.core.api.Assertions.assertThat

class VersionParserTest {

    private final VersionParser parser = new VersionParser()

    @Test
    void shouldThrowExceptionIfInvalidVersionSpecified() {
        def invalidVersion = "invalid version"

        when(parser).parse(invalidVersion)

        then(caughtException())
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
