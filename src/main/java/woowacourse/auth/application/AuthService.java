package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.dao.MemberDao;
import woowacourse.auth.domain.Member;
import woowacourse.auth.dto.MemberRequest;

@Service
public class AuthService {

    MemberDao memberDao;

    public AuthService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public void save(MemberRequest memberRequest) {
        validateUniqueEmail(memberRequest);
        Member member = new Member(memberRequest.getEmail(), memberRequest.getPassword(), memberRequest.getNickname());
        memberDao.save(member);
    }

    private void validateUniqueEmail(MemberRequest memberRequest) {
        if (existsEmail(memberRequest.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일 주소입니다.");
        }
    }

    public boolean existsEmail(String email) {
        return memberDao.existsEmail(email);
    }
}
