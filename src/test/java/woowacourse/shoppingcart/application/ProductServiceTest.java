package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.helper.fixture.ProductFixture.IMAGE;
import static woowacourse.helper.fixture.ProductFixture.PRICE;
import static woowacourse.helper.fixture.ProductFixture.createProduct;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.helper.fixture.ProductFixture;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.dto.ProductRequest;
import woowacourse.shoppingcart.dto.ProductResponse;

@SpringBootTest
@Transactional
class ProductServiceTest {

    @Autowired
    ProductService productService;

    @Autowired
    ProductDao productDao;

    @DisplayName("상품을 추가한다.")
    @Test
    void save() {
        int beforeSize = productService.findProducts().size();
        ProductRequest productRequest = new ProductRequest(PRICE, ProductFixture.NAME, IMAGE);
        productService.addProduct(productRequest);

        assertThat(productService.findProducts().size()).isGreaterThan(beforeSize);
    }

    @DisplayName("상품을 조회한다.")
    @Test
    void findProductById() {
        Long productId = productDao.save(createProduct(ProductFixture.NAME, PRICE, IMAGE));

        ProductResponse productResponse = productService.findProductById(productId);
        assertAll(
                () -> assertThat(productResponse.getPrice()).isEqualTo(PRICE),
                () -> assertThat(productResponse.getName()).isEqualTo(ProductFixture.NAME),
                () -> assertThat(productResponse.getImageUrl()).isEqualTo(IMAGE)
        );
    }

    @DisplayName("상품 목록을 조회한다.")
    @Test
    void findProducts() {
        int beforeSize = productService.findProducts().size();
        productDao.save(createProduct(ProductFixture.NAME, PRICE, IMAGE));

        assertThat(productService.findProducts().size()).isEqualTo(beforeSize + 1);
    }

    @DisplayName("상품을 삭제한다.")
    @Test
    void delete() {
        int beforeSize = productService.findProducts().size();
        Long productId = productDao.save(createProduct(ProductFixture.NAME, PRICE, IMAGE));

        productService.deleteProductById(productId);

        assertThat(productService.findProducts().size()).isEqualTo(beforeSize);
    }
}
