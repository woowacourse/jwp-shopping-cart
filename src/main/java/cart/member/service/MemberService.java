package cart.member.service;

import cart.member.dao.MemberDao;
import cart.member.dto.MemberResponse;
import cart.member.entity.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberDao memberDao;

    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public List<MemberResponse> showAllMembers() {
        return memberDao.selectAllMembers().stream()
                .map(member -> new MemberResponse(member.getEmail(), member.getPassword()))
                .collect(Collectors.toList());
    }

    public Member selectMemberByEmailAndPassword(String email, String password){
        return memberDao.selectMemberByEmailAndPassword(email, password);
    }
}
