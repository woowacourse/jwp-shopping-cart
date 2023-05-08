package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.dao.entity.MemberEntity;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
class MemberDaoTest {

    private MemberDao memberDao;
    private MemberEntity firstRecord;
    private String firstRecordEmail;


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        memberDao = new MemberDao(jdbcTemplate);

        firstRecord = memberDao.findAll().get(0);
        firstRecordEmail = firstRecord.getEmail();
    }

    @Test
    @DisplayName("findByEmail()에 존재하는 email을 인자로 넣으면 해당 엔티티를 반환한다")
    void findByEmail_Exist() {
        Optional<MemberEntity> findMember = memberDao.findByEmail(firstRecordEmail);

        assertAll(
            () -> assertThat(findMember).isPresent(),
            () -> assertThat(findMember.get()).isEqualTo(firstRecord)
        );
    }

    @Test
    @DisplayName("findByEmail()에 존재하지않는 email을 인자로 넣으면 해당 Empty를 반환한다")
    void findByEmail_Empty() {
        Optional<MemberEntity> findMember = memberDao.findByEmail("none");
        assertThat(findMember).isEmpty();
    }
}