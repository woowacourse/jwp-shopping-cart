package cart.service;

import cart.domain.Member;
import cart.domain.MemberRepository;
import cart.dto.LoginDto;
import cart.dto.response.MemberResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void saveMembers(List<Member> members) {
        for (Member member : members) {
            memberRepository.save(member);
        }
    }

    public List<MemberResponse> findAllMembers() {
        List<Member> members = memberRepository.findAll();

        return members.stream()
                .map(MemberResponse::new)
                .collect(Collectors.toList());
    }


    public LoginDto login(LoginDto loginDto) {
        Member loginMember = loginDto.toEntity();
        Member member = memberRepository.findByEmail(loginMember.getEmail());
        return new LoginDto(member);
    }
}
