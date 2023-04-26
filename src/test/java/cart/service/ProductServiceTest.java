package cart.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import cart.dao.ProductDao;
import cart.domain.Product;
import io.restassured.RestAssured;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Sql("classpath:schema.sql")
class ProductServiceTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductDao productDao;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;

        productDao.insert(new Product("pizza", 1000,
                "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a3/Eq_it-na_pizza-margherita_sep2005_sml.jpg/800px-Eq_it-na_pizza-margherita_sep2005_sml.jpg"));
        productDao.insert(
                new Product("salad", 2000,
                        "https://upload.wikimedia.org/wikipedia/commons/thumb/9/94/Salad_platter.jpg/1200px-Salad_platter.jpg"));
    }

    @Test
    void 모든_상품_목록_조회() {
        final List<Product> products = productService.findAll();

        assertThat(products.size()).isEqualTo(2);
    }

    @Test
    void 상품_등록() {
        final Long id = productService.register("족발", 5000, "족발 이미지");

        assertThat(id).isEqualTo(3L);
    }

    @Test
    void 상품_수정() {
        final long id = 2L;
        final String newProductName = "salmonSalad";

        productService.updateProduct(id, newProductName, 20000, "연어 샐러드 이미지");

        final Product updatedProduct = productDao.findById(id);
        assertThat(updatedProduct.getName()).isEqualTo(newProductName);
    }

    @Test
    void 상품_삭제() {
        final long id = 1L;

        productService.deleteProduct(id);

        assertThat(productService.findAll().size()).isEqualTo(1);
    }
}
