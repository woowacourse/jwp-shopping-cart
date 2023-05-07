package cart.business.service;

import cart.business.repository.MemberRepository;
import cart.business.domain.member.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional(readOnly = true)
    public List<Member> readAll() {
        return memberRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Integer findAndReturnId(Member member) {
        return memberRepository.findAndReturnId(member)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 ID/PW를 가진 멤버가 존재하지 않습니다."));
    }

    @Transactional(readOnly = true)
    public void validateExists(Integer memberId) {
        memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 식별자의 멤버가 존재하지 않습니다."));
    }
}
