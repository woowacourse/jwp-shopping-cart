package cart.infratstructure;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = BasicAuthorizationExtractor.class)
class BasicAuthorizationExtractorTest {

    @Autowired
    private BasicAuthorizationExtractor basicAuthorizationExtractor;

    @Test
    void extract() {
        // given
        String email = "abc@gmail.com";
        String password = "abcd1234";
        String authorization = String.join(":", email, password);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "basic " + Base64.encodeBase64String(authorization.getBytes()));

        // when
        AuthInfo authInfo = basicAuthorizationExtractor.extract(request);

        // then
        assertThat(authInfo.getEmail()).isEqualTo(email);
        assertThat(authInfo.getPassword()).isEqualTo(password);
    }
}
