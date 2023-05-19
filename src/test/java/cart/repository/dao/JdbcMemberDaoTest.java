package cart.repository.dao;

import cart.repository.entity.MemberEntity;
import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@JdbcTest
public class JdbcMemberDaoTest {

    private static final int KOKODAK_INDEX = 0;
    private static final int SUNGHA_INDEX = 1;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private JdbcMemberDao memberDao;

    @BeforeEach
    void setUp() {
        memberDao = new JdbcMemberDao(jdbcTemplate);
    }

    @Test
    void 모든_사용자를_찾는다() {
        final List<MemberEntity> memberEntities = memberDao.findAll();

        final SoftAssertions softAssertions = new SoftAssertions();

        softAssertions.assertThat(memberEntities.size()).isEqualTo(2);

        softAssertions.assertThat(memberEntities.get(KOKODAK_INDEX).getName()).isEqualTo("kokodak");
        softAssertions.assertThat(memberEntities.get(KOKODAK_INDEX).getEmail()).isEqualTo("kokodakadokok@gmail.com");
        softAssertions.assertThat(memberEntities.get(KOKODAK_INDEX).getPassword()).isEqualTo("12345");

        softAssertions.assertThat(memberEntities.get(SUNGHA_INDEX).getName()).isEqualTo("sungha");
        softAssertions.assertThat(memberEntities.get(SUNGHA_INDEX).getEmail()).isEqualTo("singha@gmail.com");
        softAssertions.assertThat(memberEntities.get(SUNGHA_INDEX).getPassword()).isEqualTo("54321");

        softAssertions.assertAll();
    }

    @Test
    void 이메일과_패스워드로_사용자를_찾는다() {
        final MemberEntity memberEntity = memberDao.findByEmailAndPassword("kokodakadokok@gmail.com", "12345");

        final SoftAssertions softAssertions = new SoftAssertions();

        softAssertions.assertThat(memberEntity.getName()).isEqualTo("kokodak");
        softAssertions.assertThat(memberEntity.getEmail()).isEqualTo("kokodakadokok@gmail.com");
        softAssertions.assertThat(memberEntity.getPassword()).isEqualTo("12345");

        softAssertions.assertAll();
    }
}
