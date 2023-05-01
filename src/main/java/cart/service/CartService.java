package cart.service;

import cart.dao.MemberDao;
import cart.exception.MemberNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final MemberDao memberDao;

    public CartService(final MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    private void checkMemberExistByMemberInfo(String email, String password) {
        if (memberDao.isNotExistByEmailAndPassword(email, password)) {
            throw new MemberNotFoundException("사용자 인증 정보에 해당하는 사용자가 존재하지 않습니다.");
        }
    }
}
