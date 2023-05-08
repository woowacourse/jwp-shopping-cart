package cart.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import cart.persistence.dao.MemberDao;
import cart.persistence.entity.Member;
import cart.service.AuthService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:schema-truncate.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class AuthServiceTest {

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private AuthService authService;

    @Test
    void isNotRegistered_메서드로_특정_정보의_사용자가_있는지_확인_성공_테스트() {
        final String email = "a@a.com";
        final String password = "password1";
        memberDao.save(new Member(email, password));

        assertFalse(() -> authService.isNotRegistered(email, password));
    }

    @Test
    void isNotRegistered_메서드로_잘못된_이메일의_사용자가_없는지_확인_성공_테스트() {
        final String email = "a@a.com";
        final String password = "password1";
        memberDao.save(new Member(email, password));

        assertTrue(() -> authService.isNotRegistered("b@a.com", password));
    }

    @Test
    void isNotRegistered_메서드로_잘못된_비밀번호의_사용자가_없는지_확인_성공_테스트() {
        final String email = "a@a.com";
        final String password = "password1";
        memberDao.save(new Member(email, password));

        assertTrue(() -> authService.isNotRegistered(email, "password2"));
    }
}
