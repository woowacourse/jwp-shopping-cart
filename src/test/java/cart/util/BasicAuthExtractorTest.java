package cart.util;

import cart.controller.dto.request.LoginRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

class BasicAuthExtractorTest {

    @DisplayName("BASE64 인코딩을 이용한 변환 테스트")
    @Test
    void basicLoginEncodeDecode() {
        //given
        String email = "abcd";
        String password = "aaaa";
        String header = "Basic " + Base64.getEncoder().encodeToString((email + ":" + password).getBytes());

        //when
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", header);

        BasicAuthExtractor extractor = new BasicAuthExtractor();
        LoginRequest loginRequest = extractor.extract(request);

        //then
        assertAll(
                () -> assertEquals(email, loginRequest.getEmail()),
                () -> assertEquals(password, loginRequest.getPassword())
        );
    }

    @DisplayName("BASE64 인코딩을 이용한 변환 실패 테스트(다르게 만들어진 헤더)")
    @Test
    void basicLoginEncodeDecodeFail() {
        //given
        String email = "abcd";
        String password = "aaaa";
        String fail = "fail";
        String header = "Basic " + Base64.getEncoder().encodeToString((fail + ":" + "fail").getBytes());

        //when
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", header);

        BasicAuthExtractor extractor = new BasicAuthExtractor();
        LoginRequest loginRequest = extractor.extract(request);

        //then
        assertAll(
                () -> assertNotEquals(email, loginRequest.getEmail()),
                () -> assertNotEquals(password, loginRequest.getPassword())
        );
    }
}
