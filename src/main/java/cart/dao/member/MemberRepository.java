package cart.dao.member;

import cart.domain.Id;
import cart.domain.member.Member;
import cart.domain.member.MemberEmail;
import cart.domain.member.MemberName;
import cart.domain.member.MemberPassword;
import cart.exception.NotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {

    private final MemberDao memberDao;

    public MemberRepository(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public Long addMember(Member member) {
        MemberEntity memberEntity = convertToMemberEntity(member);
        return memberDao.insert(memberEntity);
    }

    public List<Member> getAllMembers() {
        List<MemberEntity> memberEntities = memberDao.findAll();
        return memberEntities.stream()
                .map(this::convertToMember)
                .collect(Collectors.toList());
    }

    public Member getMember(Long id) {
        Optional<MemberEntity> memberEntity = memberDao.findById(id);
        checkIfExistMember(memberEntity);
        return convertToMember(memberEntity.get());
    }

    //todo 질문 : 객체를 update 하는 것이 어색하다고 느껴집니다.
    public Member updateMember(Member member) {
        checkIfExistMember(memberDao.findById(member.getId()));
        memberDao.update(convertToMemberEntity(member));
        return member;
    }

    public void removeMember(Long memberId) {
        checkIfExistMember(memberDao.findById(memberId));
        memberDao.delete(memberId);
    }

    private void checkIfExistMember(Optional<MemberEntity> memberEntity) {
        if(memberEntity.isEmpty()) {
            throw new NotFoundException("해당 회원이 존재하지 않습니다.");
        }
    }

    private MemberEntity convertToMemberEntity(Member member) {
        return new MemberEntity(member.getId(), member.getName(), member.getEmail(),
                member.getPassword());
    }

    private Member convertToMember(MemberEntity entity) {
        return new Member(new Id(entity.getId()),
                new MemberName(entity.getName()),
                new MemberEmail(entity.getEmail()),
                new MemberPassword(entity.getPassword()));
    }

}
