package woowacourse.shoppingcart.unit.product.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import woowacourse.shoppingcart.product.domain.Product;
import woowacourse.shoppingcart.product.exception.notfound.NotFoundProductException;
import woowacourse.shoppingcart.unit.DaoTest;

class ProductDaoTest extends DaoTest {

    @DisplayName("productID를 상품을 찾으면, product를 반환한다.")
    @Test
    void findProductById() {
        // given
        final Long productId = 1L;
        final Product expectedProduct = new Product(productId, "사과", 1600, "apple.co.kr");

        // when
        final Product product = productDao.findProductById(productId);

        // then
        assertThat(product).usingRecursiveComparison().isEqualTo(expectedProduct);
    }

    @DisplayName("productID를 상품을 찾지 못하면, 예외를 던진다.")
    @Test
    void findProductById_notExistProduct_ExceptionThrown() {
        // when, then
        assertThatThrownBy(() -> productDao.findProductById(999L))
                .isInstanceOf(NotFoundProductException.class);
    }

    @DisplayName("상품 목록 조회")
    @Test
    void getProducts() {
        // given
        final int size = 5;

        // when
        final List<Product> products = productDao.findProducts();

        // then
        assertThat(products).size().isEqualTo(size);
    }


    @ParameterizedTest
    @DisplayName("id에 해당하는 상품이 존재 여부를 확인한다.")
    @CsvSource(value = {"1:true", "999:false"}, delimiter = ':')
    void existProduct_existProduct_trueReturned(final Long productId, final boolean expected) {
        // when
        final boolean actual = productDao.existProduct(productId);

        // then
        assertThat(actual).isEqualTo(expected);
    }
}
