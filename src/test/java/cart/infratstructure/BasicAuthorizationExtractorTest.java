//package cart.infratstructure;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.Mockito.when;
//
//import javax.servlet.http.HttpServletRequest;
//import org.apache.commons.codec.binary.Base64;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//@ExtendWith(SpringExtension.class)
//@ExtendWith(MockitoExtension.class)
//class BasicAuthorizationExtractorTest {
//
//    @Autowired
//    private BasicAuthorizationExtractor basicAuthorizationExtractor; // 의존성 주입 되지 않아 null 할당
//    @Mock
//    private HttpServletRequest request;
//
//    @Test
//    void extract() {
//        // given
//        String email = "abc@gmail.com";
//        String password = "abcd1234";
//        String headerValue = String.join(":", email, password);
//        String encoded = Base64.encodeBase64String(headerValue.getBytes());
//        when(request.getHeader("Authorization")).thenReturn(encoded);
//
//        // when
//        AuthInfo authInfo = basicAuthorizationExtractor.extract(request); // NPE
//
//        // then
//        assertThat(authInfo.getEmail()).isEqualTo(email);
//        assertThat(authInfo.getPassword()).isEqualTo(password);
//    }
//}
