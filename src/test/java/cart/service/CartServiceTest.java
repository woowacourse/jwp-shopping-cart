package cart.service;

import java.util.List;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import cart.dto.ProductPostRequest;
import cart.dto.ProductPutRequest;
import cart.dto.ProductResponse;
import cart.persistence.dao.ProductDao;
import cart.persistence.entity.ProductEntity;

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
    void create_메서드로_productPostRequest를_저장한다() {
        final ProductPostRequest request = new ProductPostRequest("modi", 10000, "https://woowacourse.github.io/");
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

        final ProductPutRequest changeRequest = new ProductPutRequest("modi", 2000, "https://changed.com/");
        cartService.update(id, changeRequest);

        final ProductEntity changedEntity = productDao.findByName("modi");
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(changedEntity.getId()).isEqualTo(id);
            softAssertions.assertThat(changedEntity.getName()).isEqualTo(changeRequest.getName());
            softAssertions.assertThat(changedEntity.getPrice()).isEqualTo(changeRequest.getPrice());
            softAssertions.assertThat(changedEntity.getImageUrl()).isEqualTo(changeRequest.getImageUrl());
        });
    }
}
