package cart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.controller.dto.ProductRequest;
import cart.service.dto.ProductResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class CartServiceTest {

    @Autowired
    private CartService cartService;

    private final ProductRequest cuteSeonghaDoll =
            new ProductRequest("https://avatars.githubusercontent.com/u/95729738?v=4",
                    "CuteSeonghaDoll", 25000);

    private final ProductRequest cuteBaronDoll =
            new ProductRequest("https://avatars.githubusercontent.com/u/95729738?v=4",
                    "CuteBaronDoll", 250000);

    @Test
    @DisplayName("상품을 저장하고, 모든 상품을 조회할 수 있다.")
    void findAll() {
        // given
        long savedId1 = cartService.save(cuteSeonghaDoll);
        long savedId2 = cartService.save(cuteBaronDoll);

        // when
        List<ProductResponse> products = cartService.findAllProducts();
        List<Long> foundIds = products.stream()
                .map(ProductResponse::getId)
                .collect(Collectors.toList());

        // then
        assertThat(foundIds).containsExactly(savedId1, savedId2);
    }

    @Test
    @DisplayName("상품을 저장하고 수정할 수 있다.")
    void modifyById() {
        // given
        long savedId = cartService.save(cuteSeonghaDoll);
        ProductRequest productToModify = cuteBaronDoll;

        // when
        cartService.modifyById(productToModify, savedId);

        // then
        ProductResponse foundProduct = cartService.findAllProducts().get(0);

        assertAll(
                () -> assertThat(foundProduct.getId()).isEqualTo(savedId),
                () -> assertThat(foundProduct.getName()).isEqualTo(productToModify.getName()),
                () -> assertThat(foundProduct.getPrice()).isEqualTo(productToModify.getPrice()),
                () -> assertThat(foundProduct.getImgUrl()).isEqualTo(productToModify.getImgUrl())
        );
    }

    @Test
    @DisplayName("상품을 저장하고 삭제할 수 있다.")
    void deleteById() {
        // given
        long savedId = cartService.save(cuteSeonghaDoll);

        // when
        cartService.removeById(savedId);

        // then
        assertThat(cartService.findAllProducts()).isEmpty();
    }
}
