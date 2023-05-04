package cart.service;

import cart.domain.Member;
import cart.domain.MemberRepository;
import cart.dto.LoginDto;
import cart.dto.response.MemberResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<MemberResponse> findAllMembers() {
        List<Member> members = memberRepository.findAll();

        return members.stream()
                .map(MemberResponse::new)
                .collect(Collectors.toList());
    }

    public boolean login(LoginDto loginDto) {
        Member loginInfo = loginDto.toEntity();
        return memberRepository.contains(loginInfo);
    }

    public Optional<LoginDto> findMemberByLoginDto(LoginDto loginDto) {
        return memberRepository.findByEmailAndPassword(loginDto)
                .map(LoginDto::new);
    }
}
