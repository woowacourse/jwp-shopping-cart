package cart.service;

import cart.exception.AuthPrincipalInValidException;
import cart.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class BasicAuthService implements AuthService {

    private static final String BASIC_AUTH_PREFIX = "Basic";
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String DELIMITER = ":";

    private final MemberRepository memberRepository;

    public BasicAuthService(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void authorize(final String principal, final String credential) {
        if (!principal.equals(BASIC_AUTH_PREFIX)) {
            throw new AuthPrincipalInValidException();
        }

    }
}
