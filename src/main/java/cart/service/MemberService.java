package cart.service;

import java.util.List;
import java.util.stream.Collectors;

import cart.dao.MemberDao;
import cart.domain.member.Member;
import cart.domain.member.Nickname;
import cart.domain.member.Password;
import cart.dto.MemberFindResponse;
import cart.dto.MemberRegisterRequest;
import cart.entity.MemberEntity;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberDao memberDao;

    public MemberService(final MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public long register(MemberRegisterRequest memberRegisterRequest) {
        Nickname nickname = new Nickname(memberRegisterRequest.getNickname());
        Password password = new Password(memberRegisterRequest.getPassword());
        Member member = new Member(nickname, memberRegisterRequest.getEmail(), password);

        MemberEntity registerMemberEntity = new MemberEntity.Builder()
                .nickname(member.getNickname())
                .email(member.getEmail())
                .password(member.getPassword())
                .build();

        return memberDao.insert(registerMemberEntity);
    }

    public List<MemberFindResponse> findAll() {
        List<MemberEntity> memberEntities = memberDao.selectAll();
        return memberEntities.stream()
                .map(memberEntity -> new MemberFindResponse(memberEntity.getNickname(),
                        memberEntity.getEmail(), memberEntity.getPassword()))
                .collect(Collectors.toUnmodifiableList());
    }
}
