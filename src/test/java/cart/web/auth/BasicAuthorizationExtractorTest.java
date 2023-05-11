package cart.web.auth;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.util.Base64Utils;

import javax.servlet.http.HttpServletRequest;

class BasicAuthorizationExtractorTest {
    private final BasicAuthorizationExtractor extractor = BasicAuthorizationExtractor.getInstance();

    @DisplayName("Basic 방식으로 인코딩된 Authorization 헤더를 복호화할 수 있다.")
    @Test
    void extract() {
        //given
        MockHttpServletRequest request = new MockHttpServletRequest();
        String expected = "zuny@naver.com:zuny1234";
        String decodedValue = Base64Utils.encodeToUrlSafeString(expected.getBytes());

        request.addHeader("Authorization", HttpServletRequest.BASIC_AUTH + decodedValue);
        System.out.println(request.getHeader("Authorization"));
        //when
        String actual = extractor.extract(request);

        //then
        Assertions.assertThat(expected)
                .isEqualTo(actual);
    }
}
