package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import woowacourse.auth.domain.Customer;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.exception.InvalidAuthException;
import woowacourse.auth.support.JwtTokenProvider;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

	private final CustomerService customerService;
	private final JwtTokenProvider tokenProvider;

	public TokenResponse login(TokenRequest tokenRequest) {
		Customer customer = customerService.findByEmail(tokenRequest.getEmail());
		if (customer.isInvalidPassword(tokenRequest.getPassword())) {
			throw new InvalidAuthException("비밀번호가 일치하지 않습니다.");
		}
		return new TokenResponse(customer.getNickname(), tokenProvider.createToken(customer.getEmail()));
	}

	public Customer findCustomerByToken(String token) {
		validateToken(token);
		return customerService.findByEmail(tokenProvider.getPayload(token));
	}

	private void validateToken(String token) {
		if (token == null || !tokenProvider.validateToken(token)) {
			throw new InvalidAuthException("유효하지 않은 토큰입니다. 토큰입니다.");
		}
	}
}
