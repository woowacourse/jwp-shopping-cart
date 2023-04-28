package cart.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cart.dao.Dao;
import cart.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    private static final long FIXTURE_ID = 1L;
    private static final Product FIXTURE_NEW_PRODUCT = new Product("빙봉치킨", "bingbong.jpg", 10000);
    private static final Product FIXTURE_UPDATED_PRODUCT = new Product(FIXTURE_ID, "도이치킨", "doy.jpg", 10000);

    @Mock
    private Dao<Product> productDao;
    private ProductService productService;

    @BeforeEach
    void setUp() {
        productService = new ProductService(productDao);
    }

    @DisplayName("상품 추가 시 DB에 정보를 저장한다")
    @Test
    void add() {
        // given
        // when
        productService.add(FIXTURE_NEW_PRODUCT);

        // then
        verify(productDao).insert(FIXTURE_NEW_PRODUCT);

    }

    @DisplayName("상품 수정 시 전달받은 DB에 존재하면 정보를 업데이트한다")
    @Test
    void update() {
        // given
        when(productDao.isExist(FIXTURE_UPDATED_PRODUCT.getId())).thenReturn(true);

        // when
        productService.update(FIXTURE_UPDATED_PRODUCT);

        // then
        verify(productDao).update(FIXTURE_UPDATED_PRODUCT);
    }

    @DisplayName("상품 수정 시 전달받은 DB에 없으면 예외를 던진다")
    @Test
    void updateFail() {
        // given
        when(productDao.isExist(FIXTURE_UPDATED_PRODUCT.getId())).thenReturn(false);

        // when
        //then
        assertThatThrownBy(() -> productService.update(FIXTURE_UPDATED_PRODUCT))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("모든 상품 조회 시 DB에서 모든 정보를 가져온다")
    @Test
    void findAll() {
        // given
        // when
        productService.findAll();

        // then
        verify(productDao).findAll();
    }

    @DisplayName("상품 삭제 시 전달받은 id가 DB에 존재하면 정보를 삭제한다")
    @Test
    void deleteById() {
        // given
        when(productDao.isExist(FIXTURE_ID)).thenReturn(true);

        // when
        productService.deleteById(FIXTURE_ID);

        // then
        verify(productDao).deleteById(FIXTURE_ID);
    }

    @DisplayName("상품 삭제 시 전달받은 id가 DB에 없으면 예외를 던진다")
    @Test
    void deleteByIdFail() {
        // given
        when(productDao.isExist(FIXTURE_ID)).thenReturn(false);

        // when
        assertThatThrownBy(() -> productService.deleteById(FIXTURE_ID))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
