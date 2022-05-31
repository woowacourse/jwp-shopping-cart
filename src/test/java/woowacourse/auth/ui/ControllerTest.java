package woowacourse.auth.ui;

import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import woowacourse.auth.support.JwtTokenProvider;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ControllerTest {

	protected final String email = "123@gmail.com";
	protected final String password = "a1234!";
	protected final String nickname = "does";

	@Autowired
	protected MockMvc mockMvc;
	@Autowired
	protected ObjectMapper objectMapper;
	@MockBean
	protected JwtTokenProvider tokenProvider;

	@BeforeEach
	void tokenInit() {
		String token = "token";
		given(tokenProvider.createToken(email))
			.willReturn(token);
		given(tokenProvider.getPayload(token))
			.willReturn(email);
		given(tokenProvider.validateToken(token))
			.willReturn(true);
	}
}
