package cart.service;

import cart.dao.MemberDao;
import cart.domain.Email;
import cart.domain.MemberEntity;
import cart.domain.Password;
import cart.dto.ResponseMemberDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberService {

    private final MemberDao memberDao;

    public MemberService(final MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Transactional(readOnly = true)
    public List<ResponseMemberDto> findAll() {
        final List<MemberEntity> memberEntities = memberDao.findAll();
        return memberEntities.stream()
                .map(ResponseMemberDto::new)
                .collect(Collectors.toUnmodifiableList());
    }

    @Transactional(readOnly = true)
    public MemberEntity findByEmail(final Email email) {
        return memberDao.findByEmail(email.getAddress())
                .orElseThrow(() -> new IllegalArgumentException("입력한 이메일의 회원이 존재하지 않습니다."));
    }

    @Transactional(readOnly = true)
    public boolean hasMember(final Email email, final Password password) {
        return memberDao.findByEmail(email.getAddress())
                .map(memberEntity -> password.equals(memberEntity.getPassword()))
                .orElse(Boolean.FALSE);
    }
}
