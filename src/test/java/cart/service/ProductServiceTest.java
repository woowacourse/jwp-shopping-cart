package cart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import cart.dao.ProductDao;
import cart.domain.Product;
import cart.dto.ProductDto;
import cart.dto.response.ProductResponse;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    private static final Product PRODUCT_FIXTURE = new Product(1L, "피자", 1000, "피자 사진");

    @Mock
    private ProductDao productDao;

    @InjectMocks
    private ProductService productService;

    @Test
    void 상품을_조회한다() {
        //given
        Mockito.when(productDao.findAll())
                .thenReturn((List.of(PRODUCT_FIXTURE)));

        //when
        final List<ProductResponse> productResponses = productService.findAll();

        //then
        assertSoftly(softly -> {
            softly.assertThat(productResponses).hasSize(1);
            ProductResponse productResponse = productResponses.get(0);
            softly.assertThat(productResponse.getId()).isEqualTo(1L);
            softly.assertThat(productResponse.getName()).isEqualTo("피자");
            softly.assertThat(productResponse.getPrice()).isEqualTo(1_000);
            softly.assertThat(productResponse.getImage()).isEqualTo("피자 사진");
        });
    }

    @Test
    void id를_가진_상품을_조회한다() {
        // given
        Mockito.when(productDao.findById(Mockito.any()))
                .thenReturn(Optional.ofNullable(PRODUCT_FIXTURE));
        final Long insertedId = 1L;

        // when
        final Product product = productService.findById(insertedId);

        // then
        assertThat(product.getId()).isEqualTo(1L);
    }

    @Test
    void 상품을_등록한다() {
        // given
        Mockito.when(productDao.insert(Mockito.any()))
                .thenReturn(1L);
        final ProductDto productDto = new ProductDto("치킨", 1000, "치킨 사진");

        // when
        final Long id = productService.insert(productDto);

        // then
        assertThat(id).isEqualTo(1L);
    }

    @Test
    void 상품을_수정한다() {
        // given
        Mockito.when(productDao.update(Mockito.any(Long.class), Mockito.any(Product.class)))
                .thenReturn(1);
        final Long id = 1L;
        final ProductDto productDto = new ProductDto("순대", 1000, "순대 사진");

        // when
        final int affectedRows = productService.update(id, productDto);

        // then
        assertThat(affectedRows).isEqualTo(1);
    }

    @Test
    void 존재하지_않은_상품을_수정하면_예외가_발생한다() {
        // given
        final ProductDto productDto = new ProductDto("순대", 1000, "순대 사진");

        // expect
        assertThatThrownBy(() -> productService.update(1L, productDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 상품입니다.");
    }

    @Test
    void 상품을_삭제한다() {
        // given
        Mockito.when(productDao.delete(Mockito.any(Long.class)))
                .thenReturn(1);
        final Long id = 1L;

        // when
        final int affectedRows = productService.delete(id);

        // then
        assertThat(affectedRows).isOne();
    }
}
