package cart.service;

import cart.dao.member.MemberDao;
import cart.dto.member.MemberResponse;
import cart.entity.MemberEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MemberService {

    private final MemberDao memberDao;

    public MemberService(final MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public MemberResponse save(MemberEntity memberEntity) {
        MemberEntity saveMember = memberDao.save(memberEntity);

        return new MemberResponse(saveMember.getEmail(),
                saveMember.getName(),
                saveMember.getPhoneNumber(),
                saveMember.getPassword());
    }

    public List<MemberResponse> findAll() {
        Optional<List<MemberEntity>> members = memberDao.findAll();
        List<MemberEntity> retrievedMembers = members.get();
        return convertMembersToMemberResponses(retrievedMembers);
    }

    private List<MemberResponse> convertMembersToMemberResponses(final List<MemberEntity> members) {
        return members.stream()
                .map(member -> new MemberResponse(member.getEmail(),
                        member.getName(),
                        member.getPhoneNumber(),
                        member.getPassword())
                )
                .collect(Collectors.toList());
    }

    public void update(MemberEntity memberEntity) {
        memberDao.update(memberEntity);
    }

    public void delete(String email) {
        memberDao.delete(email);
    }
}
