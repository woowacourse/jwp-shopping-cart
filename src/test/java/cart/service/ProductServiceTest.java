package cart.service;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.domain.Product;
import cart.dto.ProductDto;
import cart.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
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

    @ParameterizedTest
    @CsvSource(value = {"applePizza:10000:사과피자 이미지", "salmonSalad:20000:연어 샐러드 이미지"}, delimiter = ':')
    void 상품_등록(final String name, final int price, final String imageUrl) {
        final long expectedId = 3L;
        final Long savedId = productService.register(new ProductDto(name, price, imageUrl));

        assertThat(savedId).isEqualTo(expectedId);
    }

    @ParameterizedTest
    @CsvSource(value = {"1:applePizza:10000:사과피자 이미지", "2:salmonSalad:20000:연어 샐러드 이미지"}, delimiter = ':')
    void 상품_수정(final long id, final String newName, final int newPrice, final String newImageUrl) {
        productService.updateProduct(id, new ProductDto(newName, newPrice, newImageUrl));

        final Product updatedProduct = productRepository.findById(id);
        assertAll(
                () -> assertThat(updatedProduct.getName()).isEqualTo(newName),
                () -> assertThat(updatedProduct.getPrice()).isEqualTo(newPrice),
                () -> assertThat(updatedProduct.getImageUrl()).isEqualTo(newImageUrl)
        );
    }

    @Test
    void 존재하지_않는_상품_수정시_예외_발생() {
        assertThatIllegalArgumentException().isThrownBy(
                () -> productService.updateProduct(10L, new ProductDto("name", 1234, "imageUrl"))
        ).withMessage("존재하지 않는 id 입니다.");
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    void 상품_삭제(final long id) {
        productService.deleteProduct(id);

        assertThat(productService.findAll().size()).isEqualTo(1);
    }

    @Test
    void 존재하지_않는_상품_삭제시_예외_발생() {
        assertThatIllegalArgumentException().isThrownBy(
                () -> productService.deleteProduct(3L)
        ).withMessage("존재하지 않는 id 입니다.");
    }
}
