package cart.auth.extractor;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import cart.dto.auth.AuthInfo;
import cart.exception.AuthorizationException;
import java.util.Base64;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

class BasicAuthorizationExtractorTest {
    public static final String EMAIL = "test@email.com";
    public static final String PASSWORD = "12345678";
    public static final AuthInfo AUTH_INFO = AuthInfo.builder()
            .email(EMAIL)
            .password(PASSWORD)
            .build();
    public static final String DELIMITER = ":";
    public static final String credentials = EMAIL + DELIMITER + PASSWORD;
    public static final String BASIC_TYPE = "Basic ";
    public static final String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

    private final AuthorizationExtractor<AuthInfo> extractor = new BasicAuthorizationExtractor();

    MockHttpServletRequest request;

    @BeforeEach
    void setUp() {
        request = new MockHttpServletRequest();
    }

    @Test
    @DisplayName("Basic 타입의 인증 정보에서 사용자 정보(AuthInfo)를 추출한다.")
    void extract_success() {
        request.addHeader("Authorization", BASIC_TYPE + encodedCredentials);
        AuthInfo authInfo = extractor.extract(request);

        assertThat(authInfo)
                .usingRecursiveComparison()
                .comparingOnlyFields("email")
                .comparingOnlyFields("password")
                .isEqualTo(AUTH_INFO);
    }

    @Test
    @DisplayName("사용자 인증 정보가 없으면 예외를 발생시킨다.")
    void extract_null() {
        assertThatThrownBy(
                () -> extractor.extract(request)
        ).isInstanceOf(AuthorizationException.class)
                .hasMessage("사용자 인증 정보가 없습니다.");
    }

    @Test
    @DisplayName("Basic 방식이 아닌 인증 정보가 들어오면 예외를 발생시킨다.")
    void extract_notBasic() {
        request.addHeader("Authorization", "Bearer " + encodedCredentials);

        assertThatThrownBy(
                () -> extractor.extract(request)
        ).isInstanceOf(AuthorizationException.class)
                .hasMessage("Basic 타입의 인증 정보가 필요합니다.");
    }
}
