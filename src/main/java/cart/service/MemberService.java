package cart.service;

import cart.dao.MemberDao;
import cart.domain.MemberEntity;
import cart.dto.ResponseMemberDto;
import cart.exception.AuthorizationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberService {

    private final MemberDao memberDao;

    public MemberService(final MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Transactional(readOnly = true)
    public List<ResponseMemberDto> findAll() {
        final List<MemberEntity> memberEntities = memberDao.findAll();
        return memberEntities.stream()
                .map(ResponseMemberDto::new)
                .collect(Collectors.toUnmodifiableList());
    }

    @Transactional(readOnly = true)
    public Long findIdByEmail(final String email) {
        try {
            final MemberEntity memberEntity = memberDao.findByEmail(email);
            return memberEntity.getId();
        } catch (EmptyResultDataAccessException e) {
            throw new AuthorizationException(email + " 계정의 회원은 존재하지 않습니다.");
        }
    }
}
