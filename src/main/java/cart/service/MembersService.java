package cart.service;

import cart.domain.member.Member;
import cart.dto.auth.AuthInfo;
import cart.dto.response.MemberResponse;
import cart.persistence.MembersDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MembersService {

    private final MembersDao membersDao;

    public MembersService(MembersDao membersDao) {
        this.membersDao = membersDao;
    }

    public List<MemberResponse> readAll() {
        List<Member> members = membersDao.findAll();

        return members.stream()
                .map(member -> new MemberResponse(member.getEmail(), member.getPassword()))
                .collect(Collectors.toList());
    }

    public boolean isMemberCertified(AuthInfo authInfo) {
        return membersDao.isMemberCertified(authInfo.getEmail(), authInfo.getPassword());
    }

    public Long readIdByEmail(String email) {
        return membersDao.findIdByEmail(email);
    }
}
