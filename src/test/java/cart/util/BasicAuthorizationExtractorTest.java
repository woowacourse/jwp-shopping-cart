package cart.util;

import static cart.util.BasicAuthorizationExtractor.extract;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import cart.exception.AuthException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class BasicAuthorizationExtractorTest {

    private static final String AUTH_TYPE = "BASIC";
    private static final String CREDENTIAL = "ZW1haWxAZW1haWwuY29tOjEyMzQ=";
    private static final String CREDENTIALS = AUTH_TYPE + " " + CREDENTIAL;
    private static final String REALM = "Temp realm";
    private static final String AUTH_EXCEPTION_MESSAGE = "인증 정보가 필요합니다.";

    @Test
    void 인증_정보가_존재하지_않으면_예외_발생() {
        String credentials = AUTH_TYPE + "";

        assertThatThrownBy(() -> extract(credentials, REALM))
                .isInstanceOf(AuthException.class)
                .hasMessage(AUTH_EXCEPTION_MESSAGE);
    }

    @ParameterizedTest
    @EmptySource
    @NullSource
    void 인증_정보가_빈_값이거나_null이면_예외_발생(final String credentials) {
        assertThatThrownBy(() -> extract(credentials, REALM))
                .isInstanceOf(AuthException.class)
                .hasMessage(AUTH_EXCEPTION_MESSAGE);
    }

    @ParameterizedTest
    @ValueSource(strings = {"BASIK", "BASI C", "bearer", "basi c"})
    void 인증_타입이_BASIC이_아닌_값이면_예외_발생(final String authType) {
        assertThatThrownBy(() -> extract(authType, REALM))
                .isInstanceOf(AuthException.class)
                .hasMessage(AUTH_EXCEPTION_MESSAGE);
    }

    @Test
    void 인증_타입이_BASIC_이면_정상_실행() {
        assertThatNoException().isThrownBy(
                () -> extract(CREDENTIALS, REALM)
        );
    }
}
