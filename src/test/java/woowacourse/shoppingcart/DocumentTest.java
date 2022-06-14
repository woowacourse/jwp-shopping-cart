package woowacourse.shoppingcart;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.auth.support.RequestTokenContext;

@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriHost = "13.125.246.196")
public class DocumentTest {

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private RequestTokenContext requestTokenContext;

    @BeforeEach
    void setUp() {
        given(requestTokenContext.getCustomerId()).willReturn("1");
        given(jwtTokenProvider.validateToken(any(String.class))).willReturn(true);
        given(jwtTokenProvider.getPayload(any(String.class))).willReturn("access-token");
    }
}
