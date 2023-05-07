package cart.persistence.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.persistence.entity.MemberEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

@JdbcTest
class H2MemberDaoTest {

    private final MemberDao memberDao;

    private final RowMapper<MemberEntity> rowMapper = (resultSet, rowNumber) -> MemberEntity.create(
            resultSet.getLong("id"),
            resultSet.getString("email"),
            resultSet.getString("password"),
            resultSet.getString("name"),
            resultSet.getString("phone_number")
    );

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    H2MemberDaoTest(JdbcTemplate jdbcTemplate) {
        this.memberDao = new H2MemberDao(jdbcTemplate);
    }

    @DisplayName("회원을 저장한다.")
    @Test
    void shouldSaveMemberWhenRequest() {
        MemberEntity memberEntityToSave = MemberEntity.createToSave(
                "email.test.com",
                "1234abcd!@",
                "Test Name",
                "01012341234"
        );
        Long memberId = this.memberDao.save(memberEntityToSave);

        final String sql = "SELECT id, email, password, name, phone_number FROM member WHERE id = ?";
        final MemberEntity memberEntityFromDb = this.jdbcTemplate.queryForObject(sql, rowMapper, memberId);

        assertAll(
                () -> assertThat(memberEntityFromDb.getEmail()).isEqualTo(memberEntityToSave.getEmail()),
                () -> assertThat(memberEntityFromDb.getPassword()).isEqualTo(memberEntityToSave.getPassword()),
                () -> assertThat(memberEntityFromDb.getName()).isEqualTo(memberEntityToSave.getName()),
                () -> assertThat(memberEntityFromDb.getPhoneNumber()).isEqualTo(memberEntityToSave.getPhoneNumber())
        );
    }
}
