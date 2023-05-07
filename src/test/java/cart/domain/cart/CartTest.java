package cart.domain.cart;

import static org.assertj.core.api.Assertions.assertThat;

import cart.domain.member.Member;
import cart.domain.product.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@DisplayName("장바구니는 ")
class CartTest {

    @ParameterizedTest
    @MethodSource({"containsTestCases"})
    @DisplayName("상품이 이미 담겨있는지 확인할 수 있다.")
    void containsTest(Product requestedProduct, boolean expected) {
        // given
        Member member1 = new Member(1L, "email1", "password1");

        Product product1 = new Product(10L, "name1", 10000, "imageUrl1");
        Product product2 = new Product(20L, "name2", 20000, "imageUrl2");

        Item item1 = new Item(1L, member1, product1);
        Item item2 = new Item(2L, member1, product2);
        List<Item> items = new ArrayList<>(List.of(item1, item2));

        Cart cart = new Cart(member1, items);

        // when
        Item requestedItem = new Item(member1, requestedProduct);
        boolean result = cart.contains(requestedItem);

        // then
        assertThat(result).isEqualTo(expected);
    }

    private static Stream<Arguments> containsTestCases() {
        return Stream.of(
                Arguments.of(new Product(10L, "name1", 10000, "imageUrl1"), true),
                Arguments.of(new Product(15L, "name3", 30000, "imageUrl3"), false)
        );
    }
}
