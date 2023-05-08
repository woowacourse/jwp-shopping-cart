package cart.service;

import cart.dao.MemberDao;
import cart.dto.AuthorizationInformation;
import cart.entity.AuthMember;
import cart.entity.Member;
import cart.exception.ServiceIllegalArgumentException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    private static final String DUPLICATED_EMAIL_MESSAGE = "이메일이 중복되었습니다.";

    private final MemberDao memberDao;

    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public void save(AuthorizationInformation authorizationInformation) {
        AuthMember authMember = authorizationInformation.toAuthMember();
        validateDuplicateEmail(authMember);

        memberDao.save(authMember);
    }

    private void validateDuplicateEmail(AuthMember authMember) {
        if (isEmailDuplicated(authMember.getEmail())) {
            throw new ServiceIllegalArgumentException(DUPLICATED_EMAIL_MESSAGE);
        }
    }

    private boolean isEmailDuplicated(String email) {
        return memberDao.isEmailExists(email);
    }

    public boolean isValidMember(AuthorizationInformation authorizationInformation) {
        AuthMember authMember = authorizationInformation.toAuthMember();

        return memberDao.isMemberExists(authMember);
    }

    public List<Member> findAll() {
        return memberDao.findAll();
    }
}
