package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.CustomerSaveRequest;
import woowacourse.shoppingcart.dto.CustomerUpdateRequest;
import woowacourse.shoppingcart.dto.LoginCustomer;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@SpringBootTest
@TestConstructor(autowireMode = AutowireMode.ALL)
@Sql("/truncate.sql")
class CustomerServiceTest {

    private static final String USERNAME = "test";
    private static final String EMAIL = "test@email.com";
    private static final String PASSWORD = "1234567890";
    private static final String ADDRESS = "서울 강남구 테헤란로 411, 성담빌딩 13층 (선릉 캠퍼스)";
    private static final String PHONE_NUMBER = "010-0000-0000";

    private final CustomerService customerService;

    public CustomerServiceTest(CustomerService customerService) {
        this.customerService = customerService;
    }

    @DisplayName("customer를 저장한다.")
    @Test
    void save() {
        CustomerSaveRequest request = new CustomerSaveRequest(USERNAME, EMAIL, PASSWORD, ADDRESS, PHONE_NUMBER);

        CustomerResponse response = customerService.save(request);

        assertAll(() -> {
            assertThat(response.getId()).isNotNull();
            assertThat(response.getUsername()).isEqualTo(USERNAME);
            assertThat(response.getEmail()).isEqualTo(EMAIL);
            assertThat(response.getAddress()).isEqualTo(ADDRESS);
            assertThat(response.getPhoneNumber()).isEqualTo(PHONE_NUMBER);
        });
    }

    @DisplayName("customer의 username을 활용하여 조회한다.")
    @Test
    void find() {
        CustomerSaveRequest request = new CustomerSaveRequest(USERNAME, EMAIL, PASSWORD, ADDRESS, PHONE_NUMBER);
        customerService.save(request);

        CustomerResponse response = customerService.find(request.getUsername());

        assertAll(() -> {
            assertThat(response.getId()).isNotNull();
            assertThat(response.getUsername()).isEqualTo(USERNAME);
            assertThat(response.getEmail()).isEqualTo(EMAIL);
            assertThat(response.getAddress()).isEqualTo(ADDRESS);
            assertThat(response.getPhoneNumber()).isEqualTo(PHONE_NUMBER);
        });
    }

    @DisplayName("존재하지 않는 username인 경우 예외를 던진다.")
    @Test
    void find_error_notExist_username() {
        assertThatThrownBy(() -> customerService.find(USERNAME))
                .isInstanceOf(InvalidCustomerException.class);
    }

    @DisplayName("customer를 수정한다.")
    @Test
    void update() {
        CustomerSaveRequest request = new CustomerSaveRequest(USERNAME, EMAIL, PASSWORD, ADDRESS, PHONE_NUMBER);
        customerService.save(request);
        String address = "선릉역";
        String phoneNumber = "010-1111-1111";

        customerService.update(new LoginCustomer(USERNAME), new CustomerUpdateRequest(address, phoneNumber));

        CustomerResponse response = customerService.find(USERNAME);
        assertAll(() -> {
            assertThat(response.getId()).isNotNull();
            assertThat(response.getUsername()).isEqualTo(USERNAME);
            assertThat(response.getEmail()).isEqualTo(EMAIL);
            assertThat(response.getAddress()).isEqualTo(address);
            assertThat(response.getPhoneNumber()).isEqualTo(phoneNumber);
        });
    }

    @DisplayName("존재하지 않는 username을 수정하는 경우 예외를 던진다.")
    @Test
    void update_error_notExist_username() {
        String address = "선릉역";
        String phoneNumber = "010-1111-1111";
        CustomerUpdateRequest customerUpdateRequest = new CustomerUpdateRequest(address, phoneNumber);

        assertThatThrownBy(() -> customerService.update(new LoginCustomer(USERNAME), customerUpdateRequest))
                .isInstanceOf(InvalidCustomerException.class);
    }
}
