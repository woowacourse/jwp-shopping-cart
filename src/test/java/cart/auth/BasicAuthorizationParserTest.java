package cart.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Base64;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class BasicAuthorizationParserTest {

    private final BasicAuthorizationParser basicAuthorizationParser = new BasicAuthorizationParser();

    @CsvSource({
            "Basic, pizza@pizza.com:password, false",
            "AnotherValue, pizza@pizza.com:password, true",
            "Basic, pizza@pizza.com.password, true"
    })
    @ParameterizedTest(name = "입력값이 {0}라면 {1}를 반환한다")
    void 헤더를_입력받아_올바른_Basic_인증_유형이_아닌지_확인한다(
            final String startWith,
            final String credential,
            final boolean result
    ) {
        // given
        final String encodedCredential = new String(Base64.getEncoder().encode((credential.getBytes())));
        final String header = startWith + " " + encodedCredential;

        // expect
        assertThat(basicAuthorizationParser.isNotValid(header)).isEqualTo(result);
    }

    @Test
    void 헤더를_입력받아_Credential을_반환한다() {
        // given
        final String credential = "pizza@pizza.com:password";
        final String encodedCredential = new String(Base64.getEncoder().encode((credential.getBytes())));
        final String header = "Basic " + encodedCredential;

        // when
        final Credential result = basicAuthorizationParser.parse(header);

        // then
        assertAll(
                () -> assertThat(result.getEmail()).isEqualTo("pizza@pizza.com"),
                () -> assertThat(result.getPassword()).isEqualTo("password")
        );
    }
}
