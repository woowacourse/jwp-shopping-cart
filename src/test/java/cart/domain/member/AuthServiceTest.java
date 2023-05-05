package cart.domain.member;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import cart.domain.exception.EntityNotFoundException;
import cart.domain.persistence.dao.MemberDao;
import cart.domain.persistence.entity.MemberEntity;

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
    void getValidatedMemberId_메서드로_유효한_사용자의_ID를_가져온다() {
        final String email = "a@a.com";
        final String password = "password1";
        memberDao.save(new MemberEntity(email, password));

        assertDoesNotThrow(() -> authService.getValidatedMemberId(email, password));
    }

    @Test
    void getValidatedMemberId_메서드로_유효하지_않은_사용자_검증_시_예외_발생_테스트() {
        final String email = "a@a.com";
        final String password = "password1";
        memberDao.save(new MemberEntity(email, password));

        assertThrows(EntityNotFoundException.class, () -> authService.getValidatedMemberId("b@a.com", password));
    }
}
