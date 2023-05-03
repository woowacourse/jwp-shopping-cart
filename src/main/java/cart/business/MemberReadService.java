package cart.business;

import cart.business.domain.member.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MemberReadService {

    private final MemberRepository memberRepository;

    public MemberReadService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional(readOnly = true)
    public List<Member> readAll() {
        return memberRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Integer findAndReturnId(Member member) {
        return memberRepository.findAndReturnId(member)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 멤버가 존재하지 않습니다."));
    }
}
