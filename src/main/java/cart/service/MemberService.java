package cart.service;

import cart.dao.MemberDao;
import cart.domain.member.Member;
import cart.dto.MemberRequest;
import cart.exception.NotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberDao memberDao;

    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public Long createMember(MemberRequest request) {
        Member member = new Member(request.getName(), request.getEmail(), request.getPassword());
        return memberDao.insert(member);
    }

    public List<Member> findAllMembers() {
        return memberDao.findAll();
    }

    public Member findMember(Long id) {
        return memberDao.findById(id)
                .orElseThrow(()-> new NotFoundException("해당 회원이 존재하지 않습니다."));
   }

    public Member updateMember(Long memberId, MemberRequest request) {
        findMember(memberId); //checkIfExist
        Member updateMember = new Member(memberId, request.getName(), request.getEmail(), request.getPassword());
        memberDao.update(updateMember);
        return updateMember;
    }

    public void removeMember(Long memberId) {
        findMember(memberId);
        memberDao.delete(memberId);
    }

}
