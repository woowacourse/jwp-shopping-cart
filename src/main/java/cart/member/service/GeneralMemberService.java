package cart.member.service;

import cart.member.domain.Member;
import cart.member.domain.MemberId;
import cart.member.repository.MemberRepository;
import cart.member.service.request.MemberCreateRequest;
import cart.member.service.response.MemberResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
public class GeneralMemberService implements MemberService {
    private final MemberRepository memberRepository;

    public GeneralMemberService(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    @Override
    public MemberId save(final MemberCreateRequest request) {
        final Member newMember = new Member(request.getName(), request.getEmail(), request.getPassword());
        return memberRepository.save(newMember);
    }

    @Override
    public List<MemberResponse> findAll() {
        return memberRepository.findAll()
                .stream()
                .map(member -> new MemberResponse(
                        member.getId().getId(),
                        member.getName(),
                        member.getEmail(),
                        member.getPassword()
                )).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public MemberId deleteByMemberId(final MemberId memberId) {
        return memberRepository.deleteByMemberId(memberId);
    }
}
