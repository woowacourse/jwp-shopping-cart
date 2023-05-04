package cart.service;

import cart.controller.dto.MemberResponse;
import cart.dao.MemberDao;
import cart.service.dto.MemberInfo;
import cart.entity.MemberEntity;
import cart.execption.AuthorizationException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberDao memberDao;

    public MemberService(final MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public List<MemberResponse> findAll() {
        final List<MemberEntity> memberEntities = memberDao.findAll();
        return memberEntities.stream()
                .map(MemberResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }

    public Long findIdByMemberInfo(final MemberInfo memberInfo) {
        final List<MemberEntity> allMembers = memberDao.findAll();
        final String email = memberInfo.getEmail();
        final String password = memberInfo.getPassword();
        return allMembers.stream()
                .filter(memberEntity -> memberEntity.getEmail().equals(email) && memberEntity.getPassword()
                        .equals(password))
                .findAny()
                .orElseThrow(() -> new AuthorizationException("해당하는 사용자 정보가 없습니다"))
                .getId();
    }
}
