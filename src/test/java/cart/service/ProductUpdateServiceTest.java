package cart.service;

import static cart.domain.ProductFixture.ODO_PRODUCT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Optional;

import cart.domain.Product;
import cart.repository.StubProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@SuppressWarnings({"NonAsciiCharacters", "SpellCheckingInspection"})
@DisplayName("ProductUpdateService 클래스")
class ProductUpdateServiceTest {

    private StubProductRepository stubProductRepository;
    private ProductUpdateService productUpdateService;

    @BeforeEach
    void setUp() {
        stubProductRepository = new StubProductRepository();
        productUpdateService = new ProductUpdateService(stubProductRepository);
    }

    @Nested
    @DisplayName("update 메서드는 오도가 저장되어 있을 때")
    class DescribeUpdate {

        Product product;

        @BeforeEach
        void saveOdo() {
            product = stubProductRepository.save(ODO_PRODUCT);
        }

        @Nested
        @DisplayName("오도를 누누로 업데이트하면")
        class ContextUpdateOdoToNunu {

            Product result;

            @BeforeEach
            void setUp() {
                result = productUpdateService.update(product.getProductId().getValue(), "누누", "url", 2);
            }

            @Test
            @DisplayName("누누를 리턴한다")
            void itReturnNunu() {
                assertAll(
                        () -> assertThat(result.getProductId().getValue()).isPositive(),
                        () -> assertThat(result.getProductName().getValue()).isEqualTo("누누"),
                        () -> assertThat(result.getProductImage().getValue()).isEqualTo("url"),
                        () -> assertThat(result.getProductPrice().getValue()).isEqualTo(2)
                );
            }

            @Test
            @DisplayName("누누로 변경된다")
            void itUpdateToNunu() {
                final Optional<Product> updatedProduct = stubProductRepository.findById(product.getProductId().getValue());
                assertAll(
                        () -> assertThat(updatedProduct).isPresent(),
                        () -> assertThat(updatedProduct.get().getProductId().getValue()).isPositive(),
                        () -> assertThat(updatedProduct.get().getProductName().getValue()).isEqualTo("누누"),
                        () -> assertThat(updatedProduct.get().getProductImage().getValue()).isEqualTo("url"),
                        () -> assertThat(updatedProduct.get().getProductPrice().getValue()).isEqualTo(2)
                );
            }
        }
    }
}
