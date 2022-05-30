package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.entity.CustomerEntity;

@SuppressWarnings("NonAsciiCharacters")
@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class CustomerDaoTest {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    private final CustomerDao customerDao;

    public CustomerDaoTest(NamedParameterJdbcTemplate jdbcTemplate) {
        customerDao = new CustomerDao(jdbcTemplate);
    }

    @DisplayName("findById 메서드는 식별자(id)에 해당되는 고객 데이터의 Optional 반환한다.")
    @Nested
    class FindByIdTest {

        @Test
        void 존재하는_고객인_경우_값이_있는_Optional_반환() {
            long 식별자 = 1L;
            CustomerEntity 고객 = new CustomerEntity(식별자, "아이디", "비밀번호", "닉네임", 15);
            saveFixture(고객);

            CustomerEntity actual = customerDao.findById(식별자).get();

            assertThat(actual).isEqualTo(고객);
        }

        @Test
        void 존재하지_않는_고객인_경우_값이_없는_Optional_반환() {
            long 식별자 = 1L;

            boolean exists = customerDao.findById(식별자).isPresent();

            assertThat(exists).isFalse();
        }
    }

    @DisplayName("save 메서드는 새로운 고객 데이터를 저장하고 식별자(id)를 반환한다.")
    @Nested
    class SaveTest {

        @Test
        void 유효한_데이터를_저장하려는_경우_성공() {
            CustomerEntity 신규_고객 = new CustomerEntity("아이디", "비밀번호", "닉네임", 15);

            Long actual = customerDao.save(신규_고객);
            Long expected = 1L;

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        void 중복되는_아이디로_데이터를_저장하려는_경우_예외발생() {
            CustomerEntity 고객 = new CustomerEntity(1L, "아이디", "비밀번호", "닉네임", 15);
            saveFixture(고객);

            assertThatThrownBy(() -> customerDao.save(고객))
                    .isInstanceOf(DataAccessException.class);
        }
    }

    @DisplayName("update 메서드는 식별자(id)에 해당되는 데이터를 수정한다.")
    @Nested
    class UpdateTest {

        @Test
        void 유효한_데이터로_수정하려는_경우_성공() {
            long 식별자 = 1L;
            CustomerEntity 고객 = new CustomerEntity(식별자, "아이디", "비밀번호", "닉네임", 15);
            CustomerEntity 수정된_고객 = new CustomerEntity(식별자, "아이디2", "비밀번호2", "닉네임2", 80);
            saveFixture(고객);

            customerDao.update(수정된_고객);
            CustomerEntity actual = findById(식별자);

            assertThat(actual).isEqualTo(수정된_고객);
        }

        @Test
        void 중복되는_아이디로_데이터를_수정하려는_경우_예외발생() {
            String 조시 = "조시";
            String 정 = "정";
            saveFixture(new CustomerEntity(1L, 조시, "비밀번호", "닉네임", 15));
            saveFixture(new CustomerEntity(2L, 정, "비밀번호", "닉네임", 15));
            CustomerEntity 조시가_되고_싶은_정 = new CustomerEntity(2L, 조시, "비밀번호", "닉네임", 15);

            assertThatThrownBy(() -> customerDao.update(조시가_되고_싶은_정))
                    .isInstanceOf(DataAccessException.class);
        }

        @Test
        void 존재하지_않는_데이터를_수장하려는_경우_예외_미발생() {
            CustomerEntity 고객 = new CustomerEntity(1L, "아이디", "비밀번호", "닉네임", 15);

            assertThatNoException()
                    .isThrownBy(() -> customerDao.update(고객));
        }
    }

    @DisplayName("delete 메서드는 식별자(id)에 해당되는 데이터를 제거한다.")
    @Nested
    class DeleteTest {

        @Test
        void 제거_성공() {
            long 식별자 = 1L;
            CustomerEntity 고객 = new CustomerEntity(식별자, "아이디", "비밀번호", "닉네임", 15);
            saveFixture(고객);

            customerDao.delete(고객);

            assertThatThrownBy(() -> findById(식별자))
                    .isInstanceOf(DataAccessException.class);
        }

        @Test
        void 존재하지_않는_데이터를_제거하려는_경우_예외_미발생() {
            CustomerEntity 고객 = new CustomerEntity(1L, "아이디", "비밀번호", "닉네임", 15);

            assertThatNoException()
                    .isThrownBy(() -> customerDao.delete(고객));
        }
    }

    private CustomerEntity findById(Long customerId) {
        final String sql = "SELECT id, username, password, nickname, age FROM customer " +
                "WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", customerId);

        return jdbcTemplate.queryForObject(sql, params, CustomerDao.ROW_MAPPER);
    }

    private void saveFixture(CustomerEntity customer) {
        final String sql = "INSERT INTO customer(id, username, password, nickname, age) "
                + "VALUES(:id, :username, :password, :nickname, :age)";
        SqlParameterSource params = new BeanPropertySqlParameterSource(customer);

        jdbcTemplate.update(sql, params);
    }
}
