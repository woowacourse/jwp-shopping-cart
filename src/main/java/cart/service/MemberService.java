package cart.service;

import cart.dao.MemberDao;
import cart.dao.entity.MemberEntity;
import cart.dto.auth.AuthInfo;
import cart.dto.response.ResponseMemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberService {

    private final MemberDao memberDao;

    @Autowired
    public MemberService(final MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public List<ResponseMemberDto> findAll() {
        final List<MemberEntity> memberEntities = memberDao.findAll();
        return memberEntities.stream()
                .map(memberEntity -> ResponseMemberDto.transferEntityToDto(memberEntity))
                .collect(Collectors.toList());
    }

    public Long findIdByAuthInfo(final AuthInfo authInfo) {
        return memberDao.findIdByAuthInfo(authInfo.getEmail(), authInfo.getPassword());
    }
}
