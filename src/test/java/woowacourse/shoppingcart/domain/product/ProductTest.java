package woowacourse.shoppingcart.domain.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductTest {

    @Test
    @DisplayName("상품을 정상적으로 생성할 수 있다.")
    void create_success() {
        // given
        Product product = new Product("testProduct", 10000, "testUrl.com");

        // when
        assertThat(product).isNotNull();
    }

    @Test
    @DisplayName("상품명이 0자 이하일 경우 예외가 발생한다.")
    void create_fail_by_name() {
        // when
        assertThatThrownBy(() -> new Product("", 10000, "testUrl.com"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("가격이 음수일 경우 예외가 발생한다.")
    void create_fail_by_price() {
        // when
        assertThatThrownBy(() -> new Product("a", -1, "testUrl.com"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
