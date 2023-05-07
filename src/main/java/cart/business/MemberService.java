package cart.business;

import cart.entity.Member;
import cart.persistence.MemberDao;
import cart.presentation.dto.MemberRequest;
import cart.presentation.dto.MemberResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MemberService {

    private MemberDao memberDao;

    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Transactional
    public Integer create(MemberRequest request) {
        Member member = makeMemberFromRequest(request);
        memberDao.findSameMemberExist(member);

        return memberDao.insert(member);
    }

    @Transactional(readOnly = true)
    public List<Member> read() {
        return memberDao.findAll();
    }

    @Transactional
    public Integer delete(Integer id) {
        return memberDao.remove(id);
    }


    private Member makeMemberFromRequest(MemberRequest request) {
        return new Member(null, request.getEmail(), request.getPassword());
    }

    private Member makeMemberFromResponse(MemberResponse response) {
        return new Member(response.getId(), response.getEmail(), response.getPassword());
    }
}
