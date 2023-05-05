package cart.service;

import cart.dao.MemberDao;
import cart.domain.Member;
import cart.dto.MemberRequestDto;
import cart.dto.MemberResponseDto;
import cart.dto.entity.MemberEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberDao memberDao;

    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Transactional
    public Long addMember(MemberRequestDto memberRequestDto) {
        Member member = new Member(memberRequestDto.getEmail(), memberRequestDto.getPassword());

        MemberEntity addMember = memberDao.save(new MemberEntity(member.getEmail(), member.getPassword()));
        return addMember.getId();
    }

    public List<MemberResponseDto> findMembers() {
        return memberDao.findAll().stream()
                .map(memberEntity ->
                        new MemberResponseDto(
                                memberEntity.getId(),
                                memberEntity.getEmail(),
                                memberEntity.getPassword()
                        )
                )
                .collect(Collectors.toUnmodifiableList());
    }
}
