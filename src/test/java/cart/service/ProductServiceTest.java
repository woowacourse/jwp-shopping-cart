package cart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;

import cart.dao.ProductDao;
import cart.dto.ProductModifyRequestDto;
import cart.dto.ProductResponseDto;
import cart.entity.Product;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ProductServiceTest {

    private ProductService productService;
    private ProductDao productDao;

    @BeforeEach
    void beforeEach() {
        productDao = Mockito.mock(ProductDao.class);
        productService = new ProductService(productDao);
    }

    @Test
    @DisplayName("상품 목록 조회")
    void findAll() {
        given(productDao.selectAll())
                .willReturn(List.of(
                        buildProduct(1, "재연", 10000, "재연씨"),
                        buildProduct(2, "미성", 10000, "미성씨")
                ));

        List<ProductResponseDto> products = productService.findAll();

        assertAll(
                () -> assertThat(products.size()).isEqualTo(2),
                () -> assertThat(products.get(0).getId()).isEqualTo(1),
                () -> assertThat(products.get(1).getId()).isEqualTo(2)
        );
    }

    private Product buildProduct(int id, String name, int price, String imageUrl) {
        return new Product.Builder()
                .id(id)
                .name(name)
                .price(price)
                .imageUrl(imageUrl)
                .build();
    }

    @Test
    @DisplayName("수정할 상품의 id가 없는 경우 예외가 발생한다.")
    void validateId_modify() {
        given(productDao.findById(1))
                .willReturn(Optional.empty());

        assertThatThrownBy(
                () -> productService.modifyById(1, new ProductModifyRequestDto("재연", 10000, "재연씨"))
        ).isInstanceOf(NoSuchElementException.class)
                .hasMessage("존재하지 않는 id 입니다.");
    }

    @Test
    @DisplayName("삭제할 상품의 id가 없는 경우 예외가 발생한다.")
    void validateId_remove() {
        given(productDao.findById(1))
                .willReturn(Optional.empty());

        assertThatThrownBy(
                () -> productService.removeById(1)
        ).isInstanceOf(NoSuchElementException.class)
                .hasMessage("존재하지 않는 id 입니다.");
    }

}
