package cart.domain.member;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import cart.domain.exception.EntityNotFoundException;
import cart.domain.persistence.dao.MemberDao;
import cart.domain.persistence.entity.MemberEntity;

@Service
public class MemberService {

    private final MemberDao memberDao;

    public MemberService(final MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public long getValidatedMemberId(final String email, final String password) {
        final Optional<MemberEntity> memberEntity = memberDao.findByEmail(email);
        if (memberEntity.isEmpty() || !memberEntity.get().getPassword().equals(password)) {
            throw new EntityNotFoundException("아이디 또는 비밀번호가 잘못되었습니다.");
        }
        return memberEntity.get().getMemberId();
    }

    public List<MemberEntity> findAll() {
        return memberDao.findAll();
    }
}
