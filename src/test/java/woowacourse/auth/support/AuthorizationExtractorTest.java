package woowacourse.auth.support;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

class AuthorizationExtractorTest {

    @DisplayName("Request Http Message 에서 Authorization 헤더의 value 를 추출한다.")
    @Test
    void extract() {
        // given
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer thisIsAuthHeader");

        // when then
        assertThat(AuthorizationExtractor.extract(request)).isEqualTo("thisIsAuthHeader");
    }
}
