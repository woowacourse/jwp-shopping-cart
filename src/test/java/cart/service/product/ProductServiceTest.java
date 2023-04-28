package cart.service.product;

import cart.domain.product.Product;
import cart.dto.product.ProductCreateRequestDto;
import cart.dto.product.ProductEditRequestDto;
import cart.dto.product.ProductsResponseDto;
import cart.repository.product.ProductRepository;
import cart.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static cart.factory.product.ProductFactory.createOtherProduct;
import static cart.factory.product.ProductFactory.createProduct;
import static cart.factory.product.ProductRequestDtoFactory.createProductCreateRequest;
import static cart.factory.product.ProductRequestDtoFactory.createProductEditRequest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@Transactional
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("상품을 조회한다.")
    void find_products_success() {
        // given
        Product product = createProduct();
        Product otherProduct = createOtherProduct();
        productRepository.add(product);
        productRepository.add(otherProduct);

        // when
        ProductsResponseDto result = productService.findAll();

        // then
        assertAll(
                () -> assertThat(result.getProducts().size()).isEqualTo(2),
                () -> assertThat(result.getProducts().get(0).getName()).isEqualTo(product.getName()),
                () -> assertThat(result.getProducts().get(1).getName()).isEqualTo(otherProduct.getName())
        );
    }

    @Test
    @DisplayName("상품을 저장한다.")
    void crete_product_success() {
        // given
        ProductCreateRequestDto req = createProductCreateRequest();

        // when
        productService.createProduct(req);

        // then
        ProductsResponseDto result = productService.findAll();
        assertAll(
                () -> assertThat(result.getProducts().get(0).getName()).isEqualTo(req.getName()),
                () -> assertThat(result.getProducts().get(0).getPrice()).isEqualTo(req.getPrice()),
                () -> assertThat(result.getProducts().get(0).getImgUrl()).isEqualTo(req.getImgUrl())
        );
    }

    @Test
    @DisplayName("상품을 수정한다.")
    void edit_product_success() {
        // given
        Product product = createProduct();
        productRepository.add(product);
        ProductEditRequestDto req = createProductEditRequest();

        // when
        productService.editProduct(product.getId(), req);

        // then
        Product result = productRepository.findAll().get(0);
        assertAll(
                () -> assertThat(result.getName()).isEqualTo(req.getName()),
                () -> assertThat(result.getPrice()).isEqualTo(req.getPrice()),
                () -> assertThat(result.getImgUrl()).isEqualTo(req.getImgUrl())
        );
    }

    @Test
    @DisplayName("상품을 삭제한다.")
    void delete_product_success() {
        // given
        productRepository.add(createProduct());
        Long id = productRepository.findAll().get(0).getId();

        // when
        productService.deleteById(id);

        // then
        ProductsResponseDto result = productService.findAll();
        assertThat(result.getProducts().size()).isEqualTo(0);
    }
}
