package woowacourse.member.service;

import org.springframework.stereotype.Service;
import woowacourse.member.dao.MemberDao;
import woowacourse.member.dto.MemberRegisterRequest;
import woowacourse.member.exception.DuplicateMemberEmailException;

@Service
public class MemberService {

    private final MemberDao memberDao;

    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public Long save(final MemberRegisterRequest memberRegisterRequest) {
        validateDuplicateEmail(memberRegisterRequest.getEmail());
        return memberDao.save(memberRegisterRequest.toEntity());
    }

    private void validateDuplicateEmail(final String email) {
        if(memberDao.isEmailExist(email)) {
            throw new DuplicateMemberEmailException();
        }
    }
}
