package cart.service;

import cart.dao.member.MemberRepository;
import cart.domain.Id;
import cart.domain.member.Member;
import cart.domain.member.MemberEmail;
import cart.domain.member.MemberName;
import cart.domain.member.MemberPassword;
import cart.dto.MemberResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<MemberResponse> findAllMembers() {
        List<Member> members = memberRepository.getAllMembers();
        return members.stream()
                .map(member -> new MemberResponse(member.getId(), member.getName(), member.getEmail(),
                        member.getPassword()))
                .collect(Collectors.toList());
    }
        public MemberResponse findMember(Long id) {
        Member member = memberRepository.getMember(id);
        return new MemberResponse(member.getId(), member.getName(), member.getEmail(), member.getPassword());
    }

    public MemberResponse createMember(MemberRequest request) {
        Member member = new Member(
                new Id(request.getId()),
                new MemberName(request.getName()),
                new MemberEmail(request.getEmail()),
                new MemberPassword(request.getPassword()));
        Long memberId = memberRepository.addMember(member);
        return new MemberResponse(memberId, member.getName(), member.getEmail(), member.getPassword());
    }

    public MemberResponse updateMember(Long memberId, MemberRequest request) {
        Member member = new Member(
                new Id(memberId),
                new MemberName(request.getName()),
                new MemberEmail(request.getEmail()),
                new MemberPassword(request.getPassword()));
        memberRepository.updateMember(member);
        return new MemberResponse(memberId, member.getName(), member.getEmail(), member.getPassword());
    }

    public void removeMember(Long productId) {
        memberRepository.removeMember(productId);
    }


}
