package cart.service;

import cart.dao.MemberDao;
import cart.dto.entity.MemberEntity;
import cart.dto.response.MemberResponse;
import cart.exception.AuthorizationException;
import cart.exception.ErrorCode;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    public static final int ZERO = 0;

    private final MemberDao memberDao;

    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public List<MemberResponse> findAll() {
        List<MemberEntity> members = memberDao.findAll();

        return members.stream()
                .map(memberEntity -> new MemberResponse(
                        memberEntity.getEmail(),
                        memberEntity.getPassword())
                )
                .collect(Collectors.toList());
    }

    public int findByEmailWithPassword(String email, String password) {
        List<MemberEntity> member = memberDao.findByEmailWithPassword(email, password);
        if (member.size() == ZERO) {
            throw new AuthorizationException(ErrorCode.MEMBER_NOT_FOUND);
        }
        return member.get(0).getId();
    }
}
