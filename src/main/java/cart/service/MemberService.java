package cart.service;

import cart.dao.MemberDao;
import cart.dto.MemberDto;
import cart.dto.auth.AuthInfo;
import cart.entity.MemberEntity;
import cart.exception.AuthorizationException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberService {
    private final MemberDao memberDao;

    @Transactional(readOnly = true)
    public List<MemberDto> findAll() {
        return memberDao.findAll().stream()
                .map(MemberDto::fromEntity)
                .collect(Collectors.toList());
    }

    public MemberDto findMember(final AuthInfo authInfo) {
        if (!existsMember(authInfo)) {
            throw new AuthorizationException("존재하지 않는 사용자입니다.");
        }
        MemberEntity member = memberDao.findByCredentials(authInfo.getEmail(), authInfo.getPassword());
        return MemberDto.fromEntity(member);
    }

    public boolean existsMember(final AuthInfo authInfo) {
        return memberDao.existsMemberByCredentials(authInfo.getEmail(), authInfo.getPassword());
    }
}
