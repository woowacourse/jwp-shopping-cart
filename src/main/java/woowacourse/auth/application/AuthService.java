package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import woowacourse.auth.domain.Customer;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.service.CustomerService;
import woowacourse.auth.support.JwtTokenProvider;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

	private final CustomerService customerService;
	private  final JwtTokenProvider tokenProvider;

	public TokenResponse login(TokenRequest tokenRequest) {
		Customer customer = customerService.findByEmail(tokenRequest.getEmail());
		if (!customer.isSamePassword(tokenRequest.getPassword())) {
			throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
		}
		String nickname = customer.getNickname();
		return new TokenResponse(nickname, tokenProvider.createToken(nickname));
	}
}
