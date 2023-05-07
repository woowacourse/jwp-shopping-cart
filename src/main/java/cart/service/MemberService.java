package cart.service;

import cart.dao.MemberDao;
import cart.dao.entity.MemberEntity;
import cart.dto.auth.AuthInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    private final MemberDao memberDao;

    @Autowired
    public MemberService(final MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public List<MemberEntity> findAll() {
        return memberDao.findAll();
    }

    public Long findIdByAuthInfo(final AuthInfo authInfo) {
        return memberDao.findIdByAuthInfo(authInfo.getEmail(), authInfo.getPassword());
    }
}
