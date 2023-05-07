package shoppingbasket.member.service;

import shoppingbasket.member.dao.MemberDao;
import shoppingbasket.member.entity.MemberEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberDao memberDao;

    public MemberService(final MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public List<MemberEntity> getMembers() {
        return memberDao.selectAll();
    }

    public MemberEntity findMemberByEmail(String memberEmail) {
        final Optional<MemberEntity> findMember = memberDao.findByEmail(memberEmail);

        if (findMember.isEmpty()) {
            throw new IllegalArgumentException("입력된 E-mail에 해당하는 사용자가 존재하지 않습니다.");
        }
        return findMember.get();
    }
}
