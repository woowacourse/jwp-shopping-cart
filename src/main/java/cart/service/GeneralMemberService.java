package cart.service;

import cart.domain.Member;
import cart.repository.MemberRepository;
import cart.service.request.MemberCreateRequest;
import cart.service.response.MemberResponse;
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
    public long save(final MemberCreateRequest request) {
        final Member newMember = new Member(request.getName(), request.getEmail(), request.getPassword());
        return memberRepository.save(newMember);
    }

    @Override
    public List<MemberResponse> findAll() {
        return memberRepository.findAll()
                .stream()
                .map(member -> new MemberResponse(
                        member.getId(),
                        member.getName(),
                        member.getEmail(),
                        member.getPassword()
                )).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public long deleteByMemberId(final long memberId) {
        return memberRepository.deleteByMemberId(memberId);
    }
}
