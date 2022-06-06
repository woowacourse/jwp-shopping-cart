package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static woowacourse.Fixtures.치킨;
import static woowacourse.Fixtures.피자;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.shoppingcart.exception.custum.InvalidInputException;

class ProductsTest {

    @DisplayName("물품들의 페이지별 목록을 반환한다.")
    @Test
    void calculatePage() {
        //given
        Products products = new Products(List.of(치킨, 피자));

        // when
        List<Product> page1 = products.calculatePage(1, 5);
        List<Product> page2 = products.calculatePage(1, 2);
        List<Product> page3 = products.calculatePage(1, 1);

        //then
        assertThat(page1).containsOnly(치킨, 피자);
        assertThat(page2).containsOnly(치킨, 피자);
        assertThat(page3).containsOnly(치킨);
    }

    @DisplayName("없는 페이지 목록을 요청할 수 없다.")
    @Test
    void calculatePageStartIndexError() {
        // given
        Products products = new Products(List.of(치킨, 피자));

        // when then
        assertThatThrownBy(() -> products.calculatePage(2,5))
                .isInstanceOf(InvalidInputException.class)
                .hasMessage("올바르지 않은 포맷의 페이지 입니다.");
    }
}
