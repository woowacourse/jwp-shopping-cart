package cart.service;

import cart.domain.member.Member;
import cart.dto.member.MemberLoginRequestDto;
import cart.dto.member.MembersResponseDto;
import cart.exception.MemberNotFoundException;
import cart.exception.PasswordInvalidException;
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

    @Transactional(readOnly = true)
    public Member findMember(final MemberLoginRequestDto memberLoginRequestDto) {
        Member member = memberRepository.findByEmail(memberLoginRequestDto.getEmail())
                .orElseThrow(MemberNotFoundException::new);

        validateMemberLogin(memberLoginRequestDto, member);

        return member;
    }

    private static void validateMemberLogin(final MemberLoginRequestDto memberLoginRequestDto, final Member member) {
        if (!memberLoginRequestDto.getEmail().equals(member.getEmail())) {
            throw new MemberNotFoundException();
        }

        if (!memberLoginRequestDto.getPassword().equals(member.getPassword())) {
            throw new PasswordInvalidException();
        }
    }
}
