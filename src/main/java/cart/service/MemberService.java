package cart.service;

import cart.controller.dto.MemberResponse;
import cart.dao.MemberDao;
import cart.dao.entity.MemberEntity;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberDao memberDao;

    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public MemberResponse login(String email, String password) {
        MemberEntity memberEntity = memberDao.findByEmail(email)
            .orElseThrow(() -> new NoSuchElementException());
        validatePassword(password, memberEntity.getPassword());
        return MemberResponse.from(memberEntity);
    }

    private void validatePassword(String inputPassword, String realPassword) {
        if (inputPassword.equals(realPassword)) {
            return;
        }
        throw new IllegalArgumentException();
    }

    public List<MemberResponse> findAll() {
        List<MemberEntity> memberEntities = memberDao.findAll();
        return MemberResponse.from(memberEntities);
    }
}
