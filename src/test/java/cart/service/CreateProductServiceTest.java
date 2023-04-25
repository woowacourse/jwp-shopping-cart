package cart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Optional;

import cart.dao.StubProductDao;
import cart.entiy.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CreateProductServiceTest {
    private StubProductDao stubProductDao;
    private CreateProductService createProductService;

    @BeforeEach
    void setUp() {
        this.stubProductDao = new StubProductDao();
        this.createProductService = new CreateProductService(stubProductDao);
    }

    @Test
    void 생성_테스트() {
        final ProductEntity result = createProductService.create("오도", "naver.com", 1);
        final Optional<ProductEntity> productEntity = stubProductDao.findById(1L);

        assertAll(
                () -> assertThat(result.getId()).isPositive(),
                () -> assertThat(productEntity).isPresent()
        );
    }
}
