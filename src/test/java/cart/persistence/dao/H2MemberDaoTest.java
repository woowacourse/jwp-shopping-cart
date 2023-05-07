package cart.persistence.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.persistence.entity.MemberEntity;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

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
    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    H2MemberDaoTest(JdbcTemplate jdbcTemplate) {
        this.memberDao = new H2MemberDao(jdbcTemplate);
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("member")
                .usingGeneratedKeyColumns("id");
    }

    @DisplayName("사용자를 저장한다.")
    @Test
    void shouldSaveMemberWhenRequest() {
        // Given
        MemberEntity memberEntityToSave = MemberEntity.createToSave(
                "email.test.com",
                "1234abcd!@",
                "Test Name",
                "01012341234"
        );
        Long memberId = this.memberDao.save(memberEntityToSave);

        // When
        final String sql = "SELECT id, email, password, name, phone_number FROM member WHERE id = ?";
        final MemberEntity memberEntityFromDb = this.jdbcTemplate.queryForObject(sql, rowMapper, memberId);

        // Then
        assertAll(
                () -> assertThat(memberEntityFromDb.getEmail()).isEqualTo(memberEntityToSave.getEmail()),
                () -> assertThat(memberEntityFromDb.getPassword()).isEqualTo(memberEntityToSave.getPassword()),
                () -> assertThat(memberEntityFromDb.getName()).isEqualTo(memberEntityToSave.getName()),
                () -> assertThat(memberEntityFromDb.getPhoneNumber()).isEqualTo(memberEntityToSave.getPhoneNumber())
        );
    }

    @DisplayName("사용자 전체를 조회한다")
    @Test
    void shouldReturnAllMembersWhenRequest() {
        // Given
        this.jdbcTemplate.update("INSERT INTO member (email, password, name, phone_number) VALUES (?, ?, ?, ?)",
                "email.test.com", "1234abcd!@", "Test Name", "01012341234");
        this.jdbcTemplate.update("INSERT INTO member (email, password) VALUES (?, ?)", "email2.test.com", "1234abcd!@");

        // When
        final List<MemberEntity> memberEntities = this.memberDao.findAll();

        // Then
        assertAll(
                () -> assertThat(memberEntities).hasSize(2),
                () -> assertThat(memberEntities.get(0).getEmail()).isEqualTo("email.test.com"),
                () -> assertThat(memberEntities.get(0).getPassword()).isEqualTo("1234abcd!@"),
                () -> assertThat(memberEntities.get(0).getName()).isEqualTo("Test Name"),
                () -> assertThat(memberEntities.get(0).getPhoneNumber()).isEqualTo("01012341234"),
                () -> assertThat(memberEntities.get(1).getEmail()).isEqualTo("email2.test.com"),
                () -> assertThat(memberEntities.get(1).getName()).isNull()
        );
    }

    @DisplayName("사용자 1명을 이메일로 조회한다.")
    @Test
    void shouldFindMemberByEmailWhenRequest() {
        // Given
        MemberEntity memberEntity = MemberEntity.createToSave(
                "email.test.com",
                "1234abcd!@",
                "Test Name",
                "01012341234"
        );
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(memberEntity);
        long memberId = this.simpleJdbcInsert.executeAndReturnKey(parameterSource).longValue();

        // When
        MemberEntity memberEntityFromDb = this.memberDao.findByEmail("email.test.com").orElse(null);

        // Then
        assertThat(memberEntityFromDb.getId()).isEqualTo(memberId);
    }

    @DisplayName("없는 이메일로 조회하면 빈 값을 반환한다.")
    @Test
    void shouldReturnEmptyValueWhenFindByEmailNotExist() {
        // Product를 생성하지 않고 ID로 조회
        assertThat(this.memberDao.findByEmail("not-exist@test.com").isPresent()).isFalse();
    }
}
