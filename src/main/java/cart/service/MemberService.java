package cart.service;

import cart.controller.dto.MemberResponse;
import cart.dao.MemberDao;
import cart.dao.entity.MemberEntity;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberDao memberDao;

    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public MemberResponse findByEmailAndPassword(String email, String password) {
        MemberEntity memberEntity = memberDao.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("일치하는 회원이 존재하지 않습니다."));

        validatePassword(password, memberEntity.getPassword());

        return MemberResponse.from(memberEntity);
    }

    public List<MemberResponse> findAll() {
        return memberDao.findAll()
                .stream()
                .map(MemberResponse::from)
                .collect(Collectors.toList());
    }

    private static void validatePassword(String inputPassword, String memberPassword) {
        if (!inputPassword.equals(memberPassword)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }
}
