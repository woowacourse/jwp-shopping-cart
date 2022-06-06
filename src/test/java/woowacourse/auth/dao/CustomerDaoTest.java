package woowacourse.auth.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import woowacourse.auth.domain.user.EncryptedPassword;
import woowacourse.setup.DatabaseTest;
import woowacourse.auth.domain.user.Customer;
import woowacourse.util.DatabaseFixture;

@SuppressWarnings("NonAsciiCharacters")
class CustomerDaoTest extends DatabaseTest {

    private static final String 유효한_아이디 = "username";
    private static final EncryptedPassword 비밀번호 = new EncryptedPassword("valid_password");
    private static final String 유효한_닉네임 = "nickname";
    private static final int 유효한_나이 = 20;

    private final CustomerDao customerDao;
    private final DatabaseFixture databaseFixture;

    public CustomerDaoTest(NamedParameterJdbcTemplate jdbcTemplate) {
        databaseFixture = new DatabaseFixture(jdbcTemplate);
        customerDao = new CustomerDao(jdbcTemplate);
    }

    @DisplayName("findByUserName 메서드는 아이디(username)에 해당되는 고객 데이터를 Optional로 반환")
    @Nested
    class FindByUserNameTest {

        @Test
        void 존재하는_고객인_경우_값이_있는_Optional_반환() {
            Customer 고객 = new Customer(유효한_아이디, 비밀번호, 유효한_닉네임, 유효한_나이);
            databaseFixture.save(고객);

            Customer actual = customerDao.findByUserName(유효한_아이디).get();
            Customer expected =  new Customer(1L, 유효한_아이디, 비밀번호, 유효한_닉네임, 유효한_나이);

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        void 존재하지_않는_고객인_경우_값이_없는_Optional_반환() {
            String 존재하지_않는_아이디 = "non_existing";

            boolean exists = customerDao.findByUserName(존재하지_않는_아이디).isPresent();

            assertThat(exists).isFalse();
        }
    }

    @DisplayName("save 메서드는 새로운 고객 데이터를 저장")
    @Nested
    class SaveTest {

        @Test
        void 유효한_데이터를_저장하려는_경우_성공() {
            Customer 신규_고객 = new Customer(유효한_아이디, 비밀번호, 유효한_닉네임, 유효한_나이);

            customerDao.save(신규_고객);
            Customer actual = customerDao.findByUserName(유효한_아이디).get();
            Customer expected = new Customer(1L, 유효한_아이디, 비밀번호, 유효한_닉네임, 유효한_나이);

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        void 중복되는_아이디로_데이터를_저장하려는_경우_예외발생() {
            Customer 고객 = new Customer(유효한_아이디, 비밀번호, 유효한_닉네임, 유효한_나이);
            databaseFixture.save(고객);

            assertThatThrownBy(() -> customerDao.save(고객))
                    .isInstanceOf(DataAccessException.class);
        }
    }

    @DisplayName("update 메서드는 식별자(id)에 해당되는 데이터의 아이디(username) 이외의 값들을 수정한다.")
    @Nested
    class UpdateTest {

        @Test
        void 유효한_데이터로_수정하려는_경우_성공() {
            Customer 고객 = new Customer(1L, 유효한_아이디, 비밀번호, 유효한_닉네임, 유효한_나이);
            EncryptedPassword 새로운_비밀번호 = new EncryptedPassword("새로운_비밀번호");
            Customer 수정된_고객 = new Customer(1L, 유효한_아이디, 새로운_비밀번호, "새로운닉네임", 80);
            databaseFixture.save(고객);

            customerDao.update(수정된_고객);
            Customer actual = customerDao.findByUserName(유효한_아이디).get();

            assertThat(actual).isEqualTo(수정된_고객);
        }

        @Test
        void 존재하지_않는_데이터를_수장하려는_경우_예외_미발생() {
            Customer 고객 = new Customer(유효한_아이디, 비밀번호, 유효한_닉네임, 유효한_나이);

            assertThatNoException()
                    .isThrownBy(() -> customerDao.update(고객));
        }
    }

    @DisplayName("delete 메서드는 식별자(id)에 해당되는 데이터를 제거한다.")
    @Nested
    class DeleteTest {

        @Test
        void 제거_성공() {
            Customer 고객 = new Customer(1L, 유효한_아이디, 비밀번호, 유효한_닉네임, 유효한_나이);
            databaseFixture.save(고객);

            customerDao.delete(고객);
            boolean exists = customerDao.findByUserName(유효한_아이디).isPresent();

            assertThat(exists).isFalse();
        }

        @Test
        void 존재하지_않는_데이터를_제거하려는_경우_예외_미발생() {
            Customer 고객 = new Customer(1L, 유효한_아이디, 비밀번호, 유효한_닉네임, 유효한_나이);

            assertThatNoException()
                    .isThrownBy(() -> customerDao.delete(고객));
        }
    }
}
