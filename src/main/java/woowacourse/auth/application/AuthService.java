package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import woowacourse.auth.domain.Customer;
import woowacourse.auth.domain.EncryptionStrategy;
import woowacourse.auth.domain.Password;
import woowacourse.auth.dto.token.TokenRequest;
import woowacourse.auth.dto.token.TokenResponse;
import woowacourse.exception.ErrorCode;
import woowacourse.exception.InvalidAuthException;
import woowacourse.auth.support.JwtTokenProvider;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

	private final CustomerService customerService;
	private final JwtTokenProvider tokenProvider;
	private final EncryptionStrategy encryptionStrategy;

	public TokenResponse login(TokenRequest tokenRequest) {
		Customer customer = customerService.findByEmail(tokenRequest.getEmail());
		String password = encryptionStrategy.encode(new Password(tokenRequest.getPassword()));
		validatePassword(customer, password);
		return new TokenResponse(customer.getNickname(), tokenProvider.createToken(customer.getEmail()));
	}

	private void validatePassword(Customer customer, String password) {
		if (customer.isInvalidPassword(password)) {
			throw new InvalidAuthException(ErrorCode.PASSWORD_NOT_MATCH, "비밀번호가 일치하지 않습니다.");
		}
	}

	public Customer findCustomerByToken(String token) {
		validateToken(token);
		return customerService.findByEmail(tokenProvider.getPayload(token));
	}

	private void validateToken(String token) {
		if (token == null) {
			throw new InvalidAuthException(ErrorCode.AUTH, "인증이 필요한 접근입니다ㅏ.");
		}
		if (!tokenProvider.validateToken(token)) {
			throw new InvalidAuthException(ErrorCode.TOKEN_INVALID, "유효하지 않은 토큰입니다.");
		}
	}
}
