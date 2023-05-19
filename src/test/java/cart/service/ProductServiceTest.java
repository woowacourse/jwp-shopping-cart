package cart.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import cart.domain.Product;
import cart.dto.CreateProductRequest;
import cart.dto.ProductDto;
import cart.dto.UpdateProductRequest;
import cart.repository.dao.JdbcProductDao;
import cart.repository.entity.ProductEntity;
import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private JdbcProductDao productDao;

    @Test
    void 상품을_추가한다() {
        final CreateProductRequest request = new CreateProductRequest("name", "imageUrl", 1000);
        final ProductEntity productEntity = new ProductEntity("name", "imageUrl", 1000);

        productService.addProduct(request);

        verify(productDao).save(productEntity); // 행위 검증
    }

    @Test
    void 모든_상품을_조회한다() {
        final ProductEntity productEntity = new ProductEntity(1L, "name", "imageUrl", 1000);
        given(productDao.findAll()).willReturn(List.of(productEntity));

        final List<ProductDto> productDtos = productService.findAllProduct();

        final SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(productDtos.size()).isOne();
        softAssertions.assertThat(productDtos.get(0).getName()).isEqualTo("name");
        softAssertions.assertThat(productDtos.get(0).getImageUrl()).isEqualTo("imageUrl");
        softAssertions.assertThat(productDtos.get(0).getPrice()).isEqualTo(1000);
        softAssertions.assertAll();
    }

    @Test
    void 상품을_업데이트한다() {
        final Long id = 1L;
        final UpdateProductRequest request = new UpdateProductRequest("name", "imageUrl", 1000);
        final Product product = new Product(request.getName(), request.getImageUrl(), request.getPrice());
        given(productDao.update(any())).willReturn(1);

        productService.updateProduct(id, request);

        verify(productDao).update(ProductEntity.of(id, product));
    }

    @Test
    void 상품을_삭제한다() {
        final Long id = 1L;
        given(productDao.delete(any())).willReturn(1);

        productService.deleteProduct(id);

        verify(productDao).delete(id);
    }
}
