package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import woowacourse.auth.domain.Customer2;

@SuppressWarnings("NonAsciiCharacters")
class CustomerDao2Test extends DatabaseTest {

    private static final String 유효한_아이디 = "valid_username";
    private static final String 비밀번호 = "valid_password";
    private static final String 유효한_닉네임 = "nickname";
    private static final int 유효한_나이 = 20;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    private final CustomerDao2 customerDao;

    public CustomerDao2Test(NamedParameterJdbcTemplate jdbcTemplate) {
        customerDao = new CustomerDao2(jdbcTemplate);
    }

    @DisplayName("findById 메서드는 아이디(username)에 해당되는 고객 데이터를 Optional로 반환")
    @Nested
    class FindByIdTest {

        @Test
        void 존재하는_고객인_경우_값이_있는_Optional_반환() {
            Customer2 고객 = new Customer2(유효한_아이디, 비밀번호, 유효한_닉네임, 유효한_나이);
            saveFixture(고객);

            Customer2 actual = customerDao.findByUserName(유효한_아이디).get();

            assertThat(actual).isEqualTo(고객);
        }

        @Test
        void 존재하지_않는_고객인_경우_값이_없는_Optional_반환() {
            String 존재하지_않는_아이디 = "non_existing";

            boolean exists = customerDao.findByUserName(존재하지_않는_아이디).isPresent();

            assertThat(exists).isFalse();
        }
    }

    @DisplayName("save 메서드는 새로운 고객 데이터를 저장하고 식별자(id)를 반환")
    @Nested
    class SaveTest {

        @Test
        void 유효한_데이터를_저장하려는_경우_성공() {
            Customer2 신규_고객 = new Customer2(유효한_아이디, 비밀번호, 유효한_닉네임, 유효한_나이);

            Long actual = customerDao.save(신규_고객);
            Long expected = 1L;

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        void 중복되는_아이디로_데이터를_저장하려는_경우_예외발생() {
            Customer2 고객 = new Customer2(유효한_아이디, 비밀번호, 유효한_닉네임, 유효한_나이);
            saveFixture(고객);

            assertThatThrownBy(() -> customerDao.save(고객))
                    .isInstanceOf(DataAccessException.class);
        }
    }

    @DisplayName("updateByUsername 메서드는 아이디(username)에 해당되는 데이터의 아이디 이외의 값들을 수정한다.")
    @Nested
    class UpdateTest {

        @Test
        void 유효한_데이터로_수정하려는_경우_성공() {
            Customer2 고객 = new Customer2(유효한_아이디, 비밀번호, 유효한_닉네임, 유효한_나이);
            Customer2 수정된_고객 = new Customer2(유효한_아이디, "새로운_비밀번호", "새로운_닉네임", 80);
            saveFixture(고객);

            customerDao.updateByUsername(수정된_고객);
            Customer2 actual = customerDao.findByUserName(유효한_아이디).get();

            assertThat(actual).isEqualTo(수정된_고객);
        }

        @Test
        void 존재하지_않는_데이터를_수장하려는_경우_예외_미발생() {
            Customer2 고객 = new Customer2(유효한_아이디, 비밀번호, 유효한_닉네임, 유효한_나이);

            assertThatNoException()
                    .isThrownBy(() -> customerDao.updateByUsername(고객));
        }
    }

    @DisplayName("delete 메서드는 아이디(username)에 해당되는 데이터를 제거한다.")
    @Nested
    class DeleteTest {

        @Test
        void 제거_성공() {
            Customer2 고객 = new Customer2(유효한_아이디, 비밀번호, 유효한_닉네임, 유효한_나이);
            saveFixture(고객);

            customerDao.delete(고객);
            boolean exists = customerDao.findByUserName(유효한_아이디).isPresent();

            assertThat(exists).isFalse();
        }

        @Test
        void 존재하지_않는_데이터를_제거하려는_경우_예외_미발생() {
            Customer2 고객 = new Customer2(유효한_아이디, 비밀번호, 유효한_닉네임, 유효한_나이);

            assertThatNoException()
                    .isThrownBy(() -> customerDao.delete(고객));
        }
    }

    private void saveFixture(Customer2 customer) {
        final String sql = "INSERT INTO customer(username, password, nickname, age) "
                + "VALUES(:username, :password, :nickname, :age)";
        SqlParameterSource params = new BeanPropertySqlParameterSource(customer);

        jdbcTemplate.update(sql, params);
    }
}
