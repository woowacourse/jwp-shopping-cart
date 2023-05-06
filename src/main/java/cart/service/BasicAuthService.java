package cart.service;

import cart.exception.MemberNotFoundException;
import cart.repository.MemberRepository;
import cart.service.dto.MemberInfo;
import org.springframework.stereotype.Service;

@Service
public class BasicAuthService {

    private final MemberRepository memberRepository;

    public BasicAuthService(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void authorize(final MemberInfo memberInfo) {
        if (memberRepository.findId(memberInfo).isEmpty()) {
            throw new MemberNotFoundException();
        }
    }
}
