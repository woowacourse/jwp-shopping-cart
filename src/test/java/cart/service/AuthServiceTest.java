package cart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.dao.member.MemberDao;
import cart.dao.member.MemberEntity;
import cart.global.exception.auth.InvalidAuthorizationException;
import cart.global.infrastructure.Credential;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@Sql("/schema.sql")
@Sql("/data.sql")
class AuthServiceTest {
    @Autowired
    private AuthService authService;

    @Autowired
    private MemberDao memberDao;

    private Credential credential;

    @BeforeEach
    void setUp() {
        memberDao.save(new MemberEntity("email", "password"));
        credential = new Credential("email", "password");
    }

    @Test
    @DisplayName("getMemberEntity() : member를 입력 받아 memberEntity를 반환한다.")
    void getMemberEntity() {
        // when
        MemberEntity memberEntity = authService.getMemberEntity(credential);

        // then
        assertAll(
                () -> assertThat(memberEntity.getEmail()).isEqualTo(credential.getEmail()),
                () -> assertThat(memberEntity.getPassword()).isEqualTo(credential.getPassword())
        );
    }

    @Test
    @DisplayName("올바르지 않은 Credential인 경우 InvalidAuthorizationException을 리턴한다.")
    void searchAllWithInvalidAuthorization() {
        // given
        final Credential wrongCredential = new Credential("wrongEmail", "wrongPassword");

        // then
        assertThatThrownBy(() -> authService.getMemberEntity(wrongCredential))
                .isInstanceOf(InvalidAuthorizationException.class);
    }
}
