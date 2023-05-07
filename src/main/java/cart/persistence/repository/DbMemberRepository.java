package cart.persistence.repository;

import cart.domain.member.Member;
import cart.domain.member.MemberRepository;
import cart.persistence.dao.MemberDao;
import cart.persistence.entity.MemberEntity;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DbMemberRepository implements MemberRepository {

    private final MemberDao memberDao;

    public DbMemberRepository(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Override
    public Long save(Member memberToSave) {
        MemberEntity memberEntity = mapToEntityForSaveFromMember(memberToSave);
        return this.memberDao.save(memberEntity);
    }

    @Override
    public List<Member> findAll() {
        List<MemberEntity> memberEntities = this.memberDao.findAll();
        return memberEntities.stream()
                .map(this::mapToMemberFromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        Optional<MemberEntity> optionalMemberEntity = this.memberDao.findByEmail(email);
        return optionalMemberEntity.map(this::mapToMemberFromEntity);
    }

    private MemberEntity mapToEntityForSaveFromMember(Member memberToSave) {
        String name = memberToSave.getName().orElse(null);
        String phoneNumber = memberToSave.getPhoneNumber().orElse(null);
        return MemberEntity.createToSave(
                memberToSave.getEmail(),
                memberToSave.getPassword(),
                name,
                phoneNumber
        );
    }

    private Member mapToMemberFromEntity(MemberEntity memberEntity) {
        return Member.create(
                memberEntity.getId(),
                memberEntity.getEmail(),
                memberEntity.getPassword(),
                memberEntity.getName(),
                memberEntity.getPhoneNumber()
        );
    }
}
