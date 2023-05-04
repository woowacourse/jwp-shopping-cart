package cart.service;

import cart.dao.MemberDao;
import cart.dao.entity.MemberEntity;
import cart.dto.ResponseMemberDto;
import cart.exception.AuthorizationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberService {

    private final MemberDao memberDao;

    public MemberService(final MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public List<ResponseMemberDto> findAll() {
        final List<MemberEntity> memberEntities = memberDao.findAll();
        return memberEntities.stream()
                .map(ResponseMemberDto::new)
                .collect(Collectors.toUnmodifiableList());
    }

    public Long findIdByEmail(final String email) {
        try {
            final MemberEntity memberEntity = memberDao.findByEmail(email);
            return memberEntity.getId();
        } catch (EmptyResultDataAccessException e) {
            throw new AuthorizationException(email + " 계정의 회원은 존재하지 않습니다.");
        }
    }
}
