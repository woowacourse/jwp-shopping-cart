package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.support.User;
import woowacourse.shoppingcart.dto.ProductRequest;
import woowacourse.shoppingcart.dto.ProductResponse;
import woowacourse.shoppingcart.dto.ProductsResponse;

@SpringBootTest
@Sql(scripts = {"classpath:schema.sql"})
@Transactional
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    @DisplayName("회원이 전체 상품을 조회한다.")
    void findProductsMember() {
        productService.addProduct(new ProductRequest("product1", "image", 1000));

        ProductsResponse products = productService.findProducts(new User(1L, User.MEMBER));

        List<Integer> result = products.getProducts().stream()
                .map(ProductResponse::getQuantity)
                .collect(Collectors.toList());
        assertThat(result).containsExactly(0);
    }

    @Test
    @DisplayName("비회원이 전체 상품을 조회한다.")
    void findProductsNonMember() {
        productService.addProduct(new ProductRequest("product1", "image", 1000));

        ProductsResponse products = productService.findProducts(new User(User.NON_MEMBER));

        List<Integer> result = products.getProducts().stream()
                .map(ProductResponse::getQuantity)
                .collect(Collectors.toList());
        assertThat(result).containsExactly(0);
    }

    @Test
    @DisplayName("상품을 삭제한다.")
    void deleteProductById() {
        Long id = productService.addProduct(new ProductRequest("product1", "image", 1000));

        productService.deleteProductById(id);

        ProductsResponse products = productService.findProducts(new User(User.NON_MEMBER));
        assertThat(products.getProducts()).hasSize(0);
    }
}