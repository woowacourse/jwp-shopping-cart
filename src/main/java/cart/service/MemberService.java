package cart.service;

import cart.domain.member.Member;
import cart.dto.member.MembersResponseDto;
import cart.repository.member.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional(readOnly = true)
    public MembersResponseDto findAll() {
        List<Member> members = memberRepository.findAll();

        return MembersResponseDto.from(members);
    }
}
