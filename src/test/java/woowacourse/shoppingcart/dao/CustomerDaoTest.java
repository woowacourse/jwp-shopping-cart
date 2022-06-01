package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.entity.CustomerEntity;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings({"NonAsciiCharacters", "InnerClassMayBeStatic"})
public class CustomerDaoTest {

    private final CustomerDao customerDao;

    public CustomerDaoTest(NamedParameterJdbcTemplate jdbcTemplate) {
        customerDao = new CustomerDao(jdbcTemplate);
    }

    @Test
    void 계정을_통해_아이디를_검색() {

        // given
        final String account = "puterism";

        // when
        final Long customerId = customerDao.findIdByAccount(account);

        // then
        assertThat(customerId).isEqualTo(1L);
    }

    @Test
    void 대소문자_구분없이_계정을_통해_아이디를_검색() {

        // given
        final String account = "gwangyeol-iM";

        // when
        final Long customerId = customerDao.findIdByAccount(account);

        // then
        assertThat(customerId).isEqualTo(16L);
    }

    @Test
    void 계정_저장() {
        Long id = customerDao
                .save(new CustomerEntity("yeonlog", "연로그", "asdf1234!", "연로그네", "01050505050"));

        assertThat(id).isNotNull();
    }

    @Test
    void 계정명을_이용해_존재_여부() {
        customerDao.save(new CustomerEntity("yeonlog", "연로그", "asdf1234!", "연로그네", "01050505050"));
        boolean result = customerDao.existsByAccount("yeonlog");

        assertThat(result).isTrue();
    }

    @Test
    void 계정으로_회원_조회() {
        String account = "yeonlog";
        customerDao.save(new CustomerEntity(account, "연로그", "asdf1234!", "연로그네", "01050505050"));

        assertThat(customerDao.findByAccount(account)).isNotEmpty();
    }

    @Test
    void id로_회원_조회() {
        Long id = customerDao
                .save(new CustomerEntity("yeonlog", "연로그", "asdf1234!", "연로그네", "01050505050"));

        assertThat(customerDao.findById(id)).isNotEmpty();
    }

    @Test
    void 회원_아이디로_계정_삭제() {
        Long id = customerDao
                .save(new CustomerEntity("yeonlog", "연로그", "asdf1234!", "연로그네", "01050505050"));

        customerDao.deleteById(id);

        assertThat(customerDao.findById(id)).isEmpty();
    }

    @Test
    void ID를_이용해_존재_여부() {
        Long id = customerDao
                .save(new CustomerEntity("yeonlog", "연로그", "asdf1234!", "연로그네", "01050505050"));
        boolean result = customerDao.existsById(id);

        assertThat(result).isTrue();
    }

    @Test
    void 회원_수정() {
        Long id = customerDao
                .save(new CustomerEntity("yeonlog", "연로그", "asdf1234!", "연로그네", "01050505050"));
        String changedNickname = "연로그변경";

        customerDao.update(new CustomerEntity(id, "yeonlog", changedNickname, "asdf1234!", "연로그네",
                "01050505050"));

        assertThat(customerDao.findById(id).get().getNickname()).isEqualTo(changedNickname);
    }
}
