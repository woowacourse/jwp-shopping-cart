package cart.business.service;

import cart.business.domain.product.Product;
import cart.business.domain.product.ProductId;
import cart.business.domain.product.ProductImage;
import cart.business.domain.product.ProductName;
import cart.business.domain.product.ProductPrice;
import cart.business.repository.ProductRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;
    @Mock
    private ProductRepository productRepository;

    private static Product dummyProduct;

    @BeforeAll
    static void setup() {
        dummyProduct = new Product(
                new ProductId(1), new ProductName("dummy"),
                new ProductImage("https://"), new ProductPrice(1));
    }

    @Test
    @DisplayName("동일한 이름을 가진 상품을 create 할 시 예외를 던진다")
    void test_create() {
        // given, when
        when(productRepository.findByName(any())).thenReturn(Optional.of(dummyProduct));

        // then
        assertThatThrownBy(() -> productService.create(dummyProduct))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("존재하지 않는 상품을 읽으려고 하면 예외를 던진다")
    void test_read() {
        // when
        when(productRepository.findById(any())).thenReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> productService.readById(dummyProduct.getId()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("존재하지 않는 상품에 대해 update 할 시 예외를 던진다")
    void test_update() {
        // given, when
        when(productRepository.findById(any())).thenReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> productService.update(dummyProduct))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("존재하지 않는 상품에 대해 delete 할 시 예외를 던진다")
    void test_delete() {
        // given, when
        when(productRepository.findById(any())).thenReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> productService.delete(dummyProduct.getId()))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
