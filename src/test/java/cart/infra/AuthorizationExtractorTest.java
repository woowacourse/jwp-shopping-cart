package cart.infra;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;

import cart.dto.AuthInfo;
import cart.service.JwpCartService;

class AuthorizationExtractorTest {
    @MockBean
    private JwpCartService jwpCartService;

    @Test
    @DisplayName("리퀘스트의 헤더에 있는 인증 정보를 추출한다.")
    void extractTest() {
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletRequest.addHeader("Authorization", "Basic ZW1haWxAZW1haWwuY29tOnBhc3N3b3Jk");
        AuthInfo authInfo = AuthorizationExtractor.extract(mockHttpServletRequest);
        assertAll(
            () -> assertThat(authInfo.getEmail()).isEqualTo("email@email.com"),
            () -> assertThat(authInfo.getPassword()).isEqualTo("password")
        );
    }

    @Test
    @DisplayName("인증 정보가 없을 시 Null을 반환한다.")
    void extractTestFail() {
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        AuthInfo authInfo = AuthorizationExtractor.extract(mockHttpServletRequest);
        assertThat(authInfo).isNull();
    }
}
