package cart.service;

import cart.dao.MemberDao;
import cart.domain.member.Member;
import cart.domain.member.MemberEntity;
import java.util.List;
import javax.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class MemberService {

    private final MemberDao memberDao;

    public MemberService(final MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public List<MemberEntity> findAll() {
        return memberDao.findAll();
    }

    public boolean isMember(@Valid final Member member) {
        return memberDao.isMember(member);
    }
}
