package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.response.ProductResponse;

@SpringBootTest
@Sql("/truncate.sql")
class ProductServiceTest {

    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductService productService;

    @DisplayName("페이징 처리를 해서 상품 정보를 조회한다.")
    @Test
    void findProductsWithPaging() {
        Product product = new Product("아이스크림", 1_000, 1, "www.test.com");
        for (int i = 0; i < 11; i++) {
            productDao.save(product);
        }

        List<ProductResponse> productResponses = productService.findProducts(4, 3);

        assertThat(productResponses).hasSize(2)
                .extracting(ProductResponse::getName)
                .contains("아이스크림");
    }
}
