package woowacourse.member.service;

import org.springframework.stereotype.Service;
import woowacourse.member.dao.MemberDao;
import woowacourse.member.dto.MemberRegisterRequest;

@Service
public class MemberService {

    private final MemberDao memberDao;

    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public Long save(final MemberRegisterRequest memberRegisterRequest) {
        return memberDao.save(memberRegisterRequest.toEntity());
    }
}
