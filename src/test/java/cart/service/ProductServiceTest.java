package cart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import cart.dao.ProductDao;
import cart.dao.entity.ProductEntity;
import cart.domain.Product;
import cart.dto.request.CreateProductRequest;
import cart.dto.request.UpdateProductRequest;
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

    private static final ProductEntity ENTITY_FIXTURE = new ProductEntity(1L, "피자", 1000, "피자 사진");

    @Mock
    private ProductDao productDao;

    @InjectMocks
    private ProductService productService;

    @Test
    void 상품을_조회한다() {
        //given
        Mockito.when(productDao.findAll())
                .thenReturn(Optional.ofNullable(List.of(ENTITY_FIXTURE)));

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
                .thenReturn(Optional.ofNullable(ENTITY_FIXTURE));
        final Long insertedId = 1L;

        // when
        final ProductEntity productEntity = productService.findById(insertedId);

        // then
        assertThat(productEntity.getId()).isEqualTo(1L);
    }

    @Test
    void 상품을_등록한다() {
        // given
        Mockito.when(productDao.insert(Mockito.any()))
                .thenReturn(1L);
        final CreateProductRequest createProductRequest = new CreateProductRequest("치킨", 1000, "치킨 사진");

        // when
        final Long id = productService.insert(createProductRequest);

        // then
        assertThat(id).isEqualTo(1L);
    }

    @Test
    void 상품을_수정한다() {
        // given
        Mockito.when(productDao.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.ofNullable(ENTITY_FIXTURE));
        Mockito.when(productDao.update(Mockito.any(Long.class), Mockito.any(Product.class)))
                .thenReturn(1);
        final Long id = 1L;
        final UpdateProductRequest updateProductRequest = new UpdateProductRequest("순대", 1000, "순대 사진");

        // when
        final int affectedRows = productService.update(id, updateProductRequest);

        // then
        assertThat(affectedRows).isEqualTo(1);
    }

    @Test
    void 존재하지_않은_상품을_수정하면_예외가_발생한다() {
        // given
        final UpdateProductRequest updateProductRequest = new UpdateProductRequest("순대", 1000, "순대 사진");

        // expect
        assertThatThrownBy(() -> productService.update(1L, updateProductRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 데이터입니다.");
    }

    @Test
    void 상품을_삭제한다() {
        // given
        Mockito.when(productDao.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.ofNullable(ENTITY_FIXTURE));
        Mockito.when(productDao.delete(Mockito.any(Long.class)))
                .thenReturn(1);
        final Long id = 1L;

        // when
        final int affectedRows = productService.delete(id);

        // then
        assertThat(affectedRows).isEqualTo(1);
    }

    @Test
    void 존재하지_않은_상품을_삭제하면_예외가_발생한다() {
        // expect
        assertThatThrownBy(() -> productService.delete(1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 데이터입니다.");
    }
}
