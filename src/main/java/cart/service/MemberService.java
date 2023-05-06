package cart.service;

import cart.dao.MemberDao;
import cart.domain.MemberEntity;
import cart.dto.ResponseMemberDto;
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
    public MemberEntity findByEmail(final String email) {
        return memberDao.findByEmail(email);
    }

    public boolean hasMember(final String email, final String password) {
        try {
            final MemberEntity memberEntity = memberDao.findByEmail(email);
            return password.equals(memberEntity.getPassword());
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }
}
