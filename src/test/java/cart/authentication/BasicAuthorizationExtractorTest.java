package cart.authentication;

import cart.dto.AuthInfo;
import cart.exception.AuthorizationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import java.io.IOException;
import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class BasicAuthorizationExtractorTest {

    BasicAuthorizationExtractor basicAuthorizationExtractor;
    byte[] encodedInfo;

    @BeforeEach
    void setUp() {
        basicAuthorizationExtractor = new BasicAuthorizationExtractor();
        encodedInfo = Base64.getEncoder().encode("roy@gmail.com:1234".getBytes());
    }

    @Test
    @DisplayName("인증 헤더로부터 MemberInfo를 추출한다")
    void extractMemberInfo() throws IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        String encodedString = "Basic" + new String(encodedInfo);
        request.addHeader("Authorization", encodedString);

        String expectedId = "roy@gmail.com";
        String expectedPassword = "1234";

        AuthInfo authInfo = basicAuthorizationExtractor.extract(request);

        assertAll(
                () -> assertThat(authInfo.getEmail()).isEqualTo(expectedId),
                () -> assertThat(authInfo.getPassword()).isEqualTo(expectedPassword)
        );
    }

    @Test
    @DisplayName("토큰에 delimiter(:)가 포함되어 있지 않을 경우 예외가 발생한다")
    void extractMemberInfoWithoutDelimiter() throws IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        encodedInfo = Base64.getEncoder().encode("roy@gmail.com1234".getBytes());
        String encodedString = "Basic" + new String(encodedInfo);
        request.addHeader("Authorization", encodedString);


        assertThatThrownBy(() -> basicAuthorizationExtractor.extract(request))
                .isInstanceOf(AuthorizationException.class)
                .hasMessage("인증 정보가 Delimiter를 포함하지 않습니다.");
    }
}