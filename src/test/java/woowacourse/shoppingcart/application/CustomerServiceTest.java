package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import woowacourse.auth.domain.EncryptedPassword;
import woowacourse.auth.domain.Password;
import woowacourse.auth.domain.User;
import woowacourse.setup.DatabaseTest;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.request.SignUpRequest;
import woowacourse.shoppingcart.dto.request.UpdateMeRequest;
import woowacourse.shoppingcart.dto.request.UpdatePasswordRequest;
import woowacourse.shoppingcart.dto.response.GetMeResponse;
import woowacourse.shoppingcart.dto.response.UniqueUsernameResponse;
import woowacourse.shoppingcart.exception.NotFoundException;
import woowacourse.util.DatabaseFixture;

@SuppressWarnings("NonAsciiCharacters")
class CustomerServiceTest extends DatabaseTest {

    private static final String 유효한_아이디 = "username";
    private static final String 비밀번호 = "password1@";
    private static final EncryptedPassword 암호화된_비밀번호 = new Password(비밀번호).toEncrypted();
    private static final String 유효한_닉네임 = "닉네임";
    private static final int 유효한_나이 = 20;

    private final CustomerService customerService;
    private final CustomerDao customerDao;
    private final DatabaseFixture databaseFixture;

    public CustomerServiceTest(NamedParameterJdbcTemplate jdbcTemplate) {
        customerDao = new CustomerDao(jdbcTemplate);
        customerService = new CustomerService(customerDao);
        databaseFixture = new DatabaseFixture(jdbcTemplate);
    }

    @DisplayName("getCustomer 메서드는 사용자 정보에 해당되는 고객 데이터를 조회")
    @Nested
    class GetCustomerTest {

        @Test
        void 존재하는_고객의_정보인_경우_조회결과_반환() {
            Customer 고객 = new Customer(유효한_아이디, 암호화된_비밀번호, 유효한_닉네임, 유효한_나이);
            databaseFixture.save(고객);
            User 고객에_대응되는_사용자 = new User(유효한_아이디, 암호화된_비밀번호);

            GetMeResponse actual = customerService.getCustomer(고객에_대응되는_사용자);
            GetMeResponse expected = new GetMeResponse(고객);

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        void 대응되는_고객이_존재하지_않는_경우_예외발생() {
            User 대응되는_고객이_없는_사용자 = new User(유효한_아이디, 암호화된_비밀번호);

            assertThatThrownBy(() -> customerService.getCustomer(대응되는_고객이_없는_사용자))
                    .isInstanceOf(NotFoundException.class);
        }
    }

    @DisplayName("checkUniqueUsername 메서드는 특정 아이디가 이미 존재하는지의 여부를 반환")
    @Nested
    class CheckUniqueUsernameTest {

        @Test
        void 존재하지_않는_아이디인_경우_참() {
            String 고유한_아이디 = "unique_username";

            UniqueUsernameResponse actual = customerService.checkUniqueUsername(고유한_아이디);
            UniqueUsernameResponse expected = new UniqueUsernameResponse(true);

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        void 이미_존재하는_아이디인_경우_거짓() {
            String 중복되는_아이디 = "duplicate";
            databaseFixture.save(new Customer(중복되는_아이디, 암호화된_비밀번호, 유효한_닉네임, 유효한_나이));

            UniqueUsernameResponse actual = customerService.checkUniqueUsername(중복되는_아이디);
            UniqueUsernameResponse expected = new UniqueUsernameResponse(false);

            assertThat(actual).isEqualTo(expected);
        }
    }

    @DisplayName("createCustomer 메서드는 신규 고객을 저장")
    @Nested
    class CreateCustomerTest {

        @Test
        void 유효한_정보로_고객을_저장() {
            SignUpRequest 회원가입_정보 = new SignUpRequest(유효한_아이디, 비밀번호, 유효한_닉네임, 유효한_나이);

            customerService.createCustomer(회원가입_정보);
            Customer createdCustomer = findCustomer(유효한_아이디);

            assertThat(createdCustomer.getUsername()).isEqualTo(유효한_아이디);
            assertThat(createdCustomer.getEncryptedPassword().hasSamePassword(비밀번호)).isTrue();
            assertThat(createdCustomer.getNickname()).isEqualTo(유효한_닉네임);
            assertThat(createdCustomer.getAge()).isEqualTo(유효한_나이);
        }

        @Test
        void 중복된_아이디로_고객을_저장하려는_경우_예외발생() {
            SignUpRequest 회원가입_정보 = new SignUpRequest(유효한_아이디, 비밀번호, 유효한_닉네임, 유효한_나이);
            SignUpRequest 아이디가_중복되는_회원가입_정보 = new SignUpRequest(유효한_아이디, "password!2", "nickname", 80);
            customerService.createCustomer(회원가입_정보);

            assertThatThrownBy(() -> customerService.createCustomer(아이디가_중복되는_회원가입_정보))
                    .isInstanceOf(DataAccessException.class);
        }

        @Test
        void 아이디가_4글자_미만인_경우_예외발생() {
            SignUpRequest 회원가입_정보 = new SignUpRequest("아이디", 비밀번호, 유효한_닉네임, 유효한_나이);

            assertThatThrownBy(() -> customerService.createCustomer(회원가입_정보))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 아이디가_20글자_초과인_경우_예외발생() {
            String 너무_긴_아이디 = "123456789012345678901";
            SignUpRequest 회원가입_정보 = new SignUpRequest(너무_긴_아이디, 비밀번호, 유효한_닉네임, 유효한_나이);

            assertThatThrownBy(() -> customerService.createCustomer(회원가입_정보))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 닉네임이_비어있는_경우_예외발생() {
            String 닉네임 = " ";
            SignUpRequest 회원가입_정보 = new SignUpRequest(유효한_아이디, 비밀번호, 닉네임, 유효한_나이);

            assertThatThrownBy(() -> customerService.createCustomer(회원가입_정보))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 닉네임이_10글자_초과인_경우_예외발생() {
            String 닉네임 = "12345678901";
            SignUpRequest 회원가입_정보 = new SignUpRequest(유효한_아이디, 비밀번호, 닉네임, 유효한_나이);

            assertThatThrownBy(() -> customerService.createCustomer(회원가입_정보))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 나이가_음수인_경우_예외발생() {
            SignUpRequest 회원가입_정보 = new SignUpRequest(유효한_아이디, 비밀번호, 유효한_닉네임, -1);

            assertThatThrownBy(() -> customerService.createCustomer(회원가입_정보))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @DisplayName("updateNicknameAndAge 메서드는 닉네임과 나이를 수정")
    @Nested
    class UpdateNicknameAndAgeTest {

        private final User 유효한_사용자 = new User(유효한_아이디, 암호화된_비밀번호);
        private final Customer 유효한_고객 = new Customer(유효한_아이디, 암호화된_비밀번호, 유효한_닉네임, 유효한_나이);

        @Test
        void 유효한_정보로_고객의_닉네임과_나이_수정() {
            databaseFixture.save(유효한_고객);
            String 새로운_닉네임 = "새로운닉네임";
            int 새로운_나이 = 100;
            UpdateMeRequest 수정된_고객_정보 = new UpdateMeRequest(유효한_아이디, 새로운_닉네임, 새로운_나이);

            customerService.updateNicknameAndAge(유효한_사용자, 수정된_고객_정보);
            Customer actual = findCustomer(유효한_아이디);
            Customer expected = new Customer(유효한_아이디, 암호화된_비밀번호, 새로운_닉네임, 새로운_나이);

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        void 현재_정보_그대로_수정하려는_경우_예외_미발생() {
            databaseFixture.save(유효한_고객);
            UpdateMeRequest 수정된_고객_정보 = new UpdateMeRequest(유효한_아이디, 유효한_닉네임, 유효한_나이);

            assertThatNoException()
                    .isThrownBy(() -> customerService.updateNicknameAndAge(유효한_사용자, 수정된_고객_정보));
        }

        @Test
        void 본인의_아이디를_수정하려는_경우_예외발생() {
            databaseFixture.save(유효한_고객);
            UpdateMeRequest 아이디_수정_정보 = new UpdateMeRequest("new_username", 유효한_닉네임, 유효한_나이);

            assertThatThrownBy(() -> customerService.updateNicknameAndAge(유효한_사용자, 아이디_수정_정보))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 사용자에_대응되는_고객이_없는_경우_예외발생() {
            UpdateMeRequest 수정된_고객_정보 = new UpdateMeRequest(유효한_아이디, "새로운_닉네임", 100);

            assertThatThrownBy(() -> customerService.updateNicknameAndAge(유효한_사용자, 수정된_고객_정보))
                    .isInstanceOf(NotFoundException.class);
        }
    }

    @DisplayName("updatePassword 메서드는 현재 비밀번호를 맞추면 새로운 비밀번호로 수정")
    @Nested
    class UpdatePasswordTest {

        private final User 유효한_사용자 = new User(유효한_아이디, 암호화된_비밀번호);
        private final Customer 유효한_고객 = new Customer(유효한_아이디, 암호화된_비밀번호, 유효한_닉네임, 유효한_나이);
        private final String 새로운_비밀번호 = "newpw123@#";

        @Test
        void 기존_비밀번호를_맞추면_비밀번호_수정_성공() {
            databaseFixture.save(유효한_고객);
            UpdatePasswordRequest 비밀번호_수정_정보 = new UpdatePasswordRequest(비밀번호, 새로운_비밀번호);

            customerService.updatePassword(유효한_사용자, 비밀번호_수정_정보);
            EncryptedPassword actual = findCustomer(유효한_아이디).getEncryptedPassword();

            assertThat(actual.hasSamePassword(새로운_비밀번호)).isTrue();
        }

        @Test
        void 기존_비밀번호를_틀린_경우_예외발생() {
            databaseFixture.save(유효한_고객);
            String 틀린_기존_비밀번호 = "wrong1234!@#";
            UpdatePasswordRequest 비밀번호_수정_정보 = new UpdatePasswordRequest(틀린_기존_비밀번호, 새로운_비밀번호);

            assertThatThrownBy(() -> customerService.updatePassword(유효한_사용자, 비밀번호_수정_정보))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 사용자에_대응되는_고객이_없는_경우_예외발생() {
            UpdatePasswordRequest 비밀번호_수정_정보 = new UpdatePasswordRequest(비밀번호, 새로운_비밀번호);

            assertThatThrownBy(() -> customerService.updatePassword(유효한_사용자, 비밀번호_수정_정보))
                    .isInstanceOf(NotFoundException.class);
        }
    }

    @DisplayName("deleteCustomer 메서드는 사용자에 해당되는 고객을 삭제")
    @Nested
    class DeleteCustomerTest {

        private final User 유효한_사용자 = new User(유효한_아이디, 암호화된_비밀번호);
        private final Customer 유효한_고객 = new Customer(유효한_아이디, 암호화된_비밀번호, 유효한_닉네임, 유효한_나이);

        @Test
        void 사용자에_대응되는_고객이_존재하면_삭제성공() {
            databaseFixture.save(유효한_고객);

            customerService.deleteCustomer(유효한_사용자);
            boolean exists = customerDao.findByUserName(유효한_아이디).isPresent();

            assertThat(exists).isFalse();
        }

        @Test
        void 사용자에_대응되는_고객이_없는_경우_예외발생() {
            assertThatThrownBy(() -> customerService.deleteCustomer(유효한_사용자))
                    .isInstanceOf(NotFoundException.class);
        }
    }

    private Customer findCustomer(String username) {
        return customerDao.findByUserName(username).get();
    }
}
