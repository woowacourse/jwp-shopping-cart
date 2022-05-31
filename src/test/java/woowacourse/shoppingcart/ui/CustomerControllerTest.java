package woowacourse.shoppingcart.ui;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Objects;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import woowacourse.shoppingcart.dto.EmailDuplicateCheckResponse;

@SpringBootTest
class CustomerControllerTest {

    private final CustomerController customerController;

    @Autowired
    public CustomerControllerTest(CustomerController customerController) {
        this.customerController = customerController;
    }

    @DisplayName("이메일이 중복되는지 확인한다.")
    @ParameterizedTest
    @CsvSource(value = {"email@email.com, false", "distinctemail@email.com, true"})
    void checkDuplicateEmail(final String email, final boolean expected) {
        ResponseEntity<EmailDuplicateCheckResponse> response = customerController.checkDuplicateEmail(email);

        assertAll(
                () -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK),
                () -> assertThat(Objects.requireNonNull(response.getBody()).getSuccess()).isEqualTo(expected)
        );
    }
}