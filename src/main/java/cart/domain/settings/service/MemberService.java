package cart.domain.settings.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cart.domain.settings.persistence.dao.MemberDao;
import cart.domain.settings.persistence.entity.MemberEntity;

@Service
public class MemberService {

    private final MemberDao memberDao;

    public MemberService(final MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public List<MemberEntity> findAll() {
        return memberDao.findAll();
    }
}
