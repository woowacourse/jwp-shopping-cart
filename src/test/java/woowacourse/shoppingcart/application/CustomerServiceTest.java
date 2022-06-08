package woowacourse.shoppingcart.application;

import static Fixture.CustomerFixtures.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;
import org.springframework.test.context.jdbc.Sql;

import woowacourse.shoppingcart.dto.customer.LoginCustomer;
import woowacourse.shoppingcart.dto.customer.request.CustomerSaveRequest;
import woowacourse.shoppingcart.dto.customer.request.EmailDuplicateRequest;
import woowacourse.shoppingcart.dto.customer.request.UsernameDuplicateRequest;
import woowacourse.shoppingcart.dto.customer.response.CustomerResponse;
import woowacourse.shoppingcart.dto.customer.response.EmailDuplicateResponse;
import woowacourse.shoppingcart.dto.customer.response.UsernameDuplicateResponse;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@SpringBootTest
@TestConstructor(autowireMode = AutowireMode.ALL)
@Sql("/truncate.sql")
class CustomerServiceTest {

    private final CustomerService customerService;

    public CustomerServiceTest(CustomerService customerService) {
        this.customerService = customerService;
    }

    @DisplayName("customer를 저장한다.")
    @Test
    void save() {
        CustomerResponse response = customerService.save(MAT_SAVE_REQUEST);

        assertAll(() -> {
            assertThat(response.getId()).isNotNull();
            assertThat(response.getUsername()).isEqualTo(MAT_USERNAME);
            assertThat(response.getEmail()).isEqualTo(MAT_EMAIL);
            assertThat(response.getAddress()).isEqualTo(MAT_ADDRESS);
            assertThat(response.getPhoneNumber()).isEqualTo(MAT_PHONE_NUMBER);
        });
    }

    @DisplayName("customer의 username을 활용하여 조회한다.")
    @Test
    void find() {
        CustomerSaveRequest request = YAHO_SAVE_REQUEST;
        customerService.save(request);

        CustomerResponse response = customerService.find(new LoginCustomer(request.getUsername()));

        assertAll(() -> {
            assertThat(response.getId()).isNotNull();
            assertThat(response.getUsername()).isEqualTo(YAHO_USERNAME);
            assertThat(response.getEmail()).isEqualTo(YAHO_EMAIL);
            assertThat(response.getAddress()).isEqualTo(YAHO_ADDRESS);
            assertThat(response.getPhoneNumber()).isEqualTo(YAHO_PHONE_NUMBER);
        });
    }

    @DisplayName("존재하지 않는 username인 경우 예외를 던진다.")
    @Test
    void find_error_notExist_username() {
        assertThatThrownBy(() -> customerService.find(new LoginCustomer("merong")))
                .isInstanceOf(InvalidCustomerException.class);
    }

    @DisplayName("customer를 수정한다.")
    @Test
    void update() {
        customerService.save(MAT_SAVE_REQUEST);

        customerService.update(new LoginCustomer(MAT_USERNAME), UPDATE_REQUEST);

        CustomerResponse response = customerService.find(new LoginCustomer(MAT_USERNAME));
        assertAll(() -> {
            assertThat(response.getId()).isNotNull();
            assertThat(response.getUsername()).isEqualTo(MAT_USERNAME);
            assertThat(response.getEmail()).isEqualTo(MAT_EMAIL);
            assertThat(response.getAddress()).isEqualTo(UPDATE_ADDRESS);
            assertThat(response.getPhoneNumber()).isEqualTo(UPDATE_PHONE_NUMBER);
        });
    }

    @DisplayName("존재하지 않는 username을 수정하는 경우 예외를 던진다.")
    @Test
    void update_error_notExist_username() {
        assertThatThrownBy(() -> customerService.update(new LoginCustomer("merong"), UPDATE_REQUEST))
                .isInstanceOf(InvalidCustomerException.class);
    }

    @DisplayName("customer를 삭제한다.")
    @Test
    void delete() {
        customerService.save(YAHO_SAVE_REQUEST);

        customerService.delete(new LoginCustomer(YAHO_USERNAME));

        assertThatThrownBy(() -> customerService.find(new LoginCustomer(YAHO_USERNAME)))
                .isInstanceOf(InvalidCustomerException.class);
    }

    @DisplayName("존재하지 않는 username을 삭제하는 경우 예외를 던진다.")
    @Test
    void delete_error_notExist_username() {
        assertThatThrownBy(() -> customerService.delete(new LoginCustomer(YAHO_USERNAME)))
                .isInstanceOf(InvalidCustomerException.class);
    }

    @DisplayName("이미 존재하는 username을 입력한 경우 dublicate=ture를 반환한다.")
    @Test
    void checkDuplicateUsername_true() {
        customerService.save(YAHO_SAVE_REQUEST);
        UsernameDuplicateResponse response = customerService.checkUsernameDuplicate(
                new UsernameDuplicateRequest(YAHO_USERNAME));
        assertThat(response.isDuplicated()).isTrue();
    }

    @DisplayName("이미 존재하는 username을 입력한 경우 dublicate=false를 반환한다.")
    @Test
    void checkDuplicateUsername_false() {
        customerService.save(YAHO_SAVE_REQUEST);
        UsernameDuplicateResponse response = customerService.checkUsernameDuplicate(
                new UsernameDuplicateRequest(MAT_USERNAME));
        assertThat(response.isDuplicated()).isFalse();
    }

    @DisplayName("이미 존재하는 username을 입력한 경우 dublicate=ture를 반환한다.")
    @Test
    void checkDuplicateEmail_true() {
        customerService.save(YAHO_SAVE_REQUEST);
        EmailDuplicateResponse response = customerService.checkEmailDuplicate(
                new EmailDuplicateRequest(YAHO_EMAIL));
        assertThat(response.isDuplicated()).isTrue();
    }

    @DisplayName("이미 존재하는 username을 입력한 경우 dublicate=false를 반환한다.")
    @Test
    void checkDuplicateEmail_false() {
        customerService.save(YAHO_SAVE_REQUEST);
        EmailDuplicateResponse response = customerService.checkEmailDuplicate(
                new EmailDuplicateRequest(MAT_EMAIL));
        assertThat(response.isDuplicated()).isFalse();
    }
}
