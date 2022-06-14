package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.dao.entity.CustomerEntity;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = "classpath:schema.sql")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings({"NonAsciiCharacters"})
class CustomerDaoTest {

    private final CustomerDao customerDao;

    public CustomerDaoTest(NamedParameterJdbcTemplate jdbcTemplate) {
        customerDao = new CustomerDao(jdbcTemplate);
    }

    @Test
    void 계정_저장() {
        // given

        // when
        Long id = customerDao.save(회원_엔티티());

        // then
        assertThat(id).isNotNull();
    }

    @Test
    void 계정명을_이용해_존재_여부() {
        // given
        String account = "yeon06";
        customerDao.save(회원_엔티티(account));

        // when
        boolean result = customerDao.existsByAccount(account);

        // then
        assertThat(result).isTrue();
    }

    @Test
    void 계정으로_회원_조회() {
        // given
        String account = "yeon06";
        customerDao.save(회원_엔티티(account));

        // when

        // then
        assertThat(customerDao.findByAccount(account)).isNotEmpty();
    }

    @Test
    void id로_회원_조회() {
        // given
        Long id = customerDao.save(회원_엔티티());

        // when

        // then
        assertThat(customerDao.findById(id)).isNotEmpty();
    }

    @Test
    void 회원_아이디로_계정_삭제() {
        // given
        Long id = customerDao.save(회원_엔티티());

        // when
        customerDao.deleteById(id);

        // then
        assertThat(customerDao.findById(id)).isEmpty();
    }

    @Test
    void ID를_이용해_존재_여부() {
        // given
        Long id = customerDao.save(회원_엔티티());

        // when
        boolean result = customerDao.existsById(id);

        // then
        assertThat(result).isTrue();
    }

    @Test
    void 회원_수정() {
        // given
        String account = "yeon06";
        String originNickname = "연로그";
        String changedNickname = "연로그변경";
        Long id = customerDao.save(회원_엔티티(account, originNickname));

        // when
        customerDao.update(회원_엔티티(id, account, changedNickname));

        // then
        Optional<CustomerEntity> customer = customerDao.findById(id);
        assertThat(customer).isPresent();
        assertThat(customer.get().getNickname()).isEqualTo(changedNickname);
    }

    private CustomerEntity 회원_엔티티() {
        return 회원_엔티티("yeon06");
    }

    private CustomerEntity 회원_엔티티(String account) {
        return 회원_엔티티(account, "연로그");
    }

    private CustomerEntity 회원_엔티티(String account, String nickname) {
        return 회원_엔티티(null, account, nickname);
    }

    private CustomerEntity 회원_엔티티(Long id, String account, String nickname) {
        return new CustomerEntity(id, account, nickname, "oneOne1234!", "연로그네", "01050505050");
    }
}
