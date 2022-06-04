package woowacourse.auth.application;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import woowacourse.auth.domain.Customer;
import woowacourse.auth.domain.EncryptionStrategy;
import woowacourse.auth.domain.Password;
import woowacourse.auth.dto.token.TokenRequest;
import woowacourse.auth.dto.token.TokenResponse;
import woowacourse.exception.InvalidAuthException;
import woowacourse.exception.InvalidCustomerException;
import woowacourse.auth.support.JwtTokenProvider;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

	private final String email = "123@gmail.com";
	private final String password = "a1234!";
	private final String nickname = "does";

	@Mock
	private CustomerService customerService;
	@Mock
	private JwtTokenProvider tokenProvider;
	@Mock
	private EncryptionStrategy encryptionStrategy;
	@InjectMocks
	private AuthService authService;

	@DisplayName("알맞은 정보로 로그인을 한다.")
	@Test
	void login() {
		// given
		given(customerService.findByEmail(email))
			.willReturn(Customer.builder()
				.id(1L)
				.email(email)
				.nickname(nickname)
				.password(password)
				.build());
		given(tokenProvider.createToken(email))
			.willReturn("access-token");
		given(encryptionStrategy.encode(new Password(password)))
			.willReturn(password);

		// when
		TokenResponse response = authService.login(new TokenRequest(email, password));

		// then
		assertAll(
			() -> assertThat(response.getNickname()).isEqualTo("does"),
			() -> assertThat(response.getAccessToken()).isEqualTo("access-token"),
			() -> verify(customerService).findByEmail(email),
			() -> verify(tokenProvider).createToken(email)
		);
	}

	@DisplayName("틀린 이메일 정보로 로그인을 하면 예외 발생")
	@Test
	void loginFailByEmail() {
		// given
		given(customerService.findByEmail(email))
			.willThrow(InvalidCustomerException.class);

		// when
		assertAll(
			() -> assertThatThrownBy(() -> authService.login(new TokenRequest(email, password)))
				.isInstanceOf(InvalidCustomerException.class),
			() -> verify(tokenProvider, never()).createToken(any())
		);
	}

	@DisplayName("비밀번호가 틀리면 예외가 발생한다.")
	@Test
	void loginFailByPassword() {
		// given
		given(customerService.findByEmail(email))
			.willReturn(Customer.builder()
				.id(1L)
				.email(email)
				.nickname(nickname)
				.password(password)
				.build());

		// when
		assertAll(
			() -> assertThatThrownBy(() -> authService.login(new TokenRequest(email, "a1234!!!")))
				.isInstanceOf(InvalidAuthException.class),
			() -> verify(tokenProvider, never()).createToken(any())
		);
	}

	@DisplayName("토큰으로 customer를 조회한다.")
	@Test
	void findCustomer() {
		// given
		String token = "access-token";
		given(tokenProvider.validateToken(token))
			.willReturn(true);
		given(tokenProvider.getPayload(token))
			.willReturn(email);
		given(customerService.findByEmail(email))
			.willReturn(Customer.builder()
				.id(1L)
				.email(email)
				.nickname(nickname)
				.password(password)
				.build());

		// when
		Customer customer = authService.findCustomerByToken(token);

		// then
		assertAll(
			() -> assertThat(customer.getNickname()).isEqualTo(nickname),
			() -> assertThat(customer.getEmail()).isEqualTo(email),
			() -> assertThat(customer.getPassword()).isEqualTo(password),
			() -> verify(tokenProvider).validateToken(token),
			() -> verify(tokenProvider).getPayload(token),
			() -> verify(customerService).findByEmail(email)
		);
	}
}
