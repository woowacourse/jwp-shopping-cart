package cart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import java.util.stream.Collectors;

import cart.dto.ProductModifyRequest;
import cart.dto.ProductRegisterRequest;
import cart.dto.ProductResponse;
import cart.exception.ProductNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    private final ProductRegisterRequest cuteSeonghaDoll =
            new ProductRegisterRequest("https://avatars.githubusercontent.com/u/95729738?v=4",
                    "CuteSeonghaDoll", 25000);

    private final ProductRegisterRequest cuteBaronDoll2 =
            new ProductRegisterRequest("https://avatars.githubusercontent.com/u/95729738?v=4",
                    "CuteBaronDoll", 250000);

    private final ProductModifyRequest cuteBaronDoll =
            new ProductModifyRequest("CuteBaronDoll", 250000, "https://avatars.githubusercontent.com/u/95729738?v=4");

    @Test
    @DisplayName("상품을 저장하고, 모든 상품을 조회할 수 있다.")
    void findAll() {
        // given
        long savedId1 = productService.save(cuteSeonghaDoll);
        long savedId2 = productService.save(cuteBaronDoll2);

        // when
        List<ProductResponse> products = productService.findAllProducts();
        List<Long> foundIds = products.stream()
                .map(ProductResponse::getId)
                .collect(Collectors.toList());

        // then
        assertThat(foundIds).containsExactly(savedId1, savedId2);
    }

    @Test
    @DisplayName("상품 수정 시 수정 상품 ID에 해당하는 상품이 없다면 예외가 발생한다.")
    void modifyByIdWhenProductNotFound() {
        // when, then
        assertThatThrownBy(() -> productService.modifyById(cuteBaronDoll, 100L))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessage("상품 ID에 해당하는 상품이 존재하지 않습니다.");

    }

    @Test
    @DisplayName("상품을 저장하고 수정할 수 있다.")
    void modifyById() {
        // given
        long savedId = productService.save(cuteSeonghaDoll);
        ProductModifyRequest productToModify = cuteBaronDoll;

        // when
        productService.modifyById(productToModify, savedId);

        // then
        ProductResponse foundProduct = productService.findAllProducts().get(0);

        assertAll(
                () -> assertThat(foundProduct.getId()).isEqualTo(savedId),
                () -> assertThat(foundProduct.getName()).isEqualTo(productToModify.getName()),
                () -> assertThat(foundProduct.getPrice()).isEqualTo(productToModify.getPrice()),
                () -> assertThat(foundProduct.getImgUrl()).isEqualTo(productToModify.getImgUrl())
        );
    }

    @Test
    @DisplayName("상품 삭제 시 삭제 상품 ID에 해당하는 상품이 없다면 예외가 발생한다.")
    void removeByIdWhenProductNotFound() {
        // when, then
        assertThatThrownBy(() -> productService.removeById(100L))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessage("상품 ID에 해당하는 상품이 존재하지 않습니다.");

    }

    @Test
    @DisplayName("상품을 저장하고 삭제할 수 있다.")
    void removeById() {
        // given
        long savedId = productService.save(cuteSeonghaDoll);

        // when
        productService.removeById(savedId);

        // then
        assertThat(productService.findAllProducts()).isEmpty();
    }
}
