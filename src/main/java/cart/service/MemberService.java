package cart.service;

import cart.dao.MemberDao;
import cart.dao.entity.MemberEntity;
import cart.domain.Member;
import cart.dto.AuthDto;
import cart.dto.response.MemberResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

    private final MemberDao memberDao;

    @Autowired
    public MemberService(final MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Transactional(readOnly = true)
    public List<MemberResponse> findAll() {
        final List<MemberEntity> memberEntities = memberDao.findAll();
        return memberEntities.stream()
                .map(entity -> new MemberResponse(
                        entity.getId(),
                        entity.getEmail(),
                        entity.getPassword()
                        )
                ).collect(Collectors.toUnmodifiableList());
    }

    @Transactional(readOnly = true)
    public MemberEntity findMember(final AuthDto authDto) {
        final Member member = new Member(authDto.getEmail(), authDto.getPassword());
        final Optional<MemberEntity> memberResult = memberDao.findMember(member);
        if (memberResult.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 회원 토큰입니다");
        }

        return memberResult.get();
    }
}
