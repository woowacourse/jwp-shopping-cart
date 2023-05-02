package cart.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.domain.Member;
import cart.dto.AuthInfo;
import cart.exception.WrongAuthException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@Sql(value = {"/schema.sql", "/data.sql"})
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void 모든_회원을_조회한다() {
        final var members = memberRepository.findAll();

        assertAll(
                () -> assertThat(members.size()).isEqualTo(2),
                () -> assertThat(members.get(0)).isInstanceOf(Member.class)
        );
    }

    @Test
    void 값이_존재하면_도메인을_반환한다() {
        final AuthInfo authInfo = new AuthInfo("userA@woowahan.com", "passwordA");

        final Member result = memberRepository.findByAuthInfo(authInfo);

        assertThat(result.getName()).isEqualTo("userA");
    }

    @Test
    void 값이_존재하지_않으면_예외를_발생한다() {
        final AuthInfo authInfo = new AuthInfo("nono@email.com", "nonoPassword");

        assertThatThrownBy(() -> memberRepository.findByAuthInfo(authInfo))
                .isInstanceOf(WrongAuthException.class);
    }
}
