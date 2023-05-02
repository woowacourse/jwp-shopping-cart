package cart.service;

import cart.dao.MemberDao;
import cart.dto.MemberRequestDto;
import cart.dto.MemberResponseDto;
import cart.dto.entity.MemberEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberService {

    private final MemberDao memberDao;

    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public void addMember(MemberRequestDto memberRequestDto) {
        memberDao.save(new MemberEntity(memberRequestDto.getEmail(), memberRequestDto.getPassword()));
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
