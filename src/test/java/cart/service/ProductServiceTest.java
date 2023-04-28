package cart.service;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.domain.Product;
import cart.dto.ProductDto;
import cart.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import java.util.List;

@SpringBootTest
@Sql("classpath:schema.sql")
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository.insert(new Product("pizza", 1000,
                "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a3/Eq_it-na_pizza-margherita_sep2005_sml.jpg/800px-Eq_it-na_pizza-margherita_sep2005_sml.jpg"));
        productRepository.insert(new Product("salad", 2000,
                "https://upload.wikimedia.org/wikipedia/commons/thumb/9/94/Salad_platter.jpg/1200px-Salad_platter.jpg"));
    }

    @Test
    void 모든_상품_목록_조회() {
        final List<Product> products = productService.findAll();

        assertThat(products.size()).isEqualTo(2);
    }

    @Test
    void 상품_등록() {
        final long expectedId = 3L;
        final Long savedId = productService.register(new ProductDto("item1", 1000, "item1 image"));

        assertThat(savedId).isEqualTo(expectedId);
    }

    @Test
    void 상품_수정() {
        productService.updateProduct(1L, new ProductDto("new Name", 10, "new Image Url"));

        final Product updatedProduct = productRepository.findById(1L);

        assertAll(
                () -> assertThat(updatedProduct.getName()).isEqualTo("new Name"),
                () -> assertThat(updatedProduct.getPrice()).isEqualTo(10),
                () -> assertThat(updatedProduct.getImageUrl()).isEqualTo("new Image Url")
        );
    }

    @Test
    void 존재하지_않는_상품_수정시_예외_발생() {
        assertThatIllegalArgumentException().isThrownBy(
                () -> productService.updateProduct(10L, new ProductDto("name", 1234, "imageUrl"))
        ).withMessage("존재하지 않는 id 입니다.");
    }

    @Test
    void 상품_삭제() {
        productService.deleteProduct(1L);

        assertThat(productService.findAll().size()).isEqualTo(1);
    }

    @Test
    void 존재하지_않는_상품_삭제시_예외_발생() {
        assertThatIllegalArgumentException().isThrownBy(
                () -> productService.deleteProduct(3L)
        ).withMessage("존재하지 않는 id 입니다.");
    }
}
