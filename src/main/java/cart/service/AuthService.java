package cart.service;

import cart.entity.Member;
import cart.exception.customexceptions.AdminAccessException;
import cart.exception.customexceptions.DataNotFoundException;
import cart.exception.customexceptions.PasswordNotMatchException;
import cart.repository.dao.memberDao.MemberDao;
import cart.utils.CaesarCipher;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public static final String ADMIN_EMAIL = "admin@wootech.com";

    private final MemberDao memberDao;

    public AuthService(final MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public void validateMember(final String email, final String password) {
        Member member = memberDao.findByEmail(email)
                .orElseThrow(() -> new DataNotFoundException("존재하지 않는 사용자입니다."));

        if (!member.matchPassword(CaesarCipher.encrypt(password))) {
            throw new PasswordNotMatchException("비밀번호가 일치하지 않습니다.");
        }
    }

    public void validateAdmin(final String email, final String password) {
        if (!ADMIN_EMAIL.equals(email)) {
            throw new AdminAccessException("관리자만 접근이 가능합니다.");
        }

        Member member = memberDao.findByEmail(email)
                .orElseThrow(() -> new AdminAccessException("관리자만 접근이 가능합니다."));

        if (!member.matchPassword(CaesarCipher.encrypt(password))) {
            throw new PasswordNotMatchException("비밀번호가 일치하지 않습니다.");
        }
    }
}
