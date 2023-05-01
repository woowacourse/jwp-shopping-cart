package cart.domain.admin.service;

import java.util.List;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import cart.domain.admin.persistence.dao.ProductDao;
import cart.domain.admin.persistence.entity.ProductEntity;
import cart.web.admin.dto.PostProductRequest;
import cart.web.admin.dto.ProductResponse;
import cart.web.admin.dto.PutProductRequest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:schema-truncate.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CartServiceTest {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private CartService cartService;

    @Test
    void create_메서드로_postProductRequest를_저장한다() {
        final PostProductRequest request = new PostProductRequest("modi", 10000, "https://woowacourse.github.io/");
        cartService.create(request);

        final ProductEntity findEntity = productDao.findByName("modi");

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(findEntity.getName()).isEqualTo("modi");
            softAssertions.assertThat(findEntity.getPrice()).isEqualTo(10000);
            softAssertions.assertThat(findEntity.getImageUrl()).isEqualTo("https://woowacourse.github.io/");
        });
    }

    @Test
    void readAll_메서드로_모든_ProductResponse를_불러온다() {
        final ProductEntity productEntity = new ProductEntity("modi", 10000, "https://woowacourse.github.io/");
        productDao.save(productEntity);

        final List<ProductResponse> responses = cartService.readAll();
        final ProductResponse productResponse = responses.get(0);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(responses.size()).isEqualTo(1);
            softAssertions.assertThat(productResponse.getName()).isEqualTo("modi");
            softAssertions.assertThat(productResponse.getPrice()).isEqualTo(10000);
            softAssertions.assertThat(productResponse.getImageUrl()).isEqualTo("https://woowacourse.github.io/");
        });
    }

    @Test
    void update_메서드로_ProductEntity를_변경한다() {
        final ProductEntity productEntity = new ProductEntity("modi", 10000, "https://woowacourse.github.io/");
        final Long id = productDao.save(productEntity);

        final PutProductRequest changeRequest = new PutProductRequest("modi", 2000, "https://changed.com/");
        cartService.update(id, changeRequest);

        final ProductEntity changedEntity = productDao.findByName("modi");
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(changedEntity.getId()).isEqualTo(id);
            softAssertions.assertThat(changedEntity.getName()).isEqualTo(changeRequest.getName());
            softAssertions.assertThat(changedEntity.getPrice()).isEqualTo(changeRequest.getPrice());
            softAssertions.assertThat(changedEntity.getImageUrl()).isEqualTo(changeRequest.getImageUrl());
        });
    }

    @Test
    void 존재하지_않는_id를_update_하면_예외가_발생한다() {
        final PutProductRequest changeRequest = new PutProductRequest("modi", 2000, "https://changed.com/");
        final long wrongId = 1L;

        Assertions.assertThrows(DbNotAffectedException.class,
            () -> cartService.update(wrongId, changeRequest));
    }

    @Test
    void 존재하지_않는_id를_delete_하면_예외가_발생한다() {
        final long wrongId = 0L;

        Assertions.assertThrows(DbNotAffectedException.class, () -> cartService.delete(wrongId));
    }
}
