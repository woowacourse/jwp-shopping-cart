package woowacourse.shoppingcart.domain.customer;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import woowacourse.auth.exception.BadRequestException;
import woowacourse.shoppingcart.domain.address.FullAddress;
import woowacourse.shoppingcart.fixture.CustomerFixture;

@DisplayName("Customer 는")
class CustomerTest {

    @DisplayName("사용자 생성요청을 받았을 시")
    @Nested
    class CustomerValidationTest {

        @DisplayName("입력데이터가 유효하면 저장한다.")
        @Test
        void validCustomer() {
            assertThatNoException().isThrownBy(
                    CustomerFixture.tommyCreator::get
            );
        }

        @DisplayName("입력데이터가 유효하지 않으면 에러를 던진다.")
        @Test
        void invalidCustomer() {
            assertThatThrownBy(() ->
                    new Customer(1L, new Email("her0807@naver.com"), new Password("qwert!1"),
                            "example.com", new Name("토미"), Gender.MALE, new Birthday("1988-08-07"),
                            new Contact("12345678910"),
                            new FullAddress("a", "b", "12345"), new Terms(true))
            )
                    .isInstanceOf(BadRequestException.class)
                    .hasMessage(Password.INVALID_PASSWORD_FORMAT);
        }
    }

}
