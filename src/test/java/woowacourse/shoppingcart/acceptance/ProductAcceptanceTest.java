package woowacourse.shoppingcart.acceptance;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.http.HttpStatus;
import woowacourse.shoppingcart.dto.ProductResponse;
import woowacourse.shoppingcart.dto.ProductsResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ProductAcceptanceTest extends AcceptanceTest {

    @Test
    void 상품_하나를_조회() {

        var extract = createOneProductResult(7L, HttpStatus.OK).as(ProductResponse.class);

        assertAll(
                () -> assertThat(extract.getId()).isEqualTo(7L),
                () -> assertThat(extract.getName()).isEqualTo("맛있는 초밥"),
                () -> assertThat(extract.getPrice()).isEqualTo(700),
                () -> assertThat(extract.getImageUrl()).isEqualTo("https://www.naver.com")
        );
    }

    @Test
    void 없는_상품을_조회() {

        var extract = createOneProductResult(200L, HttpStatus.BAD_REQUEST);

        assertThat(extract.body().jsonPath().getString("message"))
                .isEqualTo("[ERROR] 없는 상품입니다.");
    }

    @ParameterizedTest
    @CsvSource(value = {"1:12:12", "2:12:1"}, delimiter = ':')
    void 전체상품_중_첫_페이지를_조회하는_경우(int page, int perPage, int resultSize) {

        var extract = createPagedProductResult(page, perPage, HttpStatus.OK).as(ProductsResponse.class);

        assertThat(extract.getProducts().size()).isEqualTo(resultSize);
    }

    @Test
    void 전체상품_중_자연수가_아닌_페이지를_조회하는_경우() {

        var extract = createPagedProductResult(0, 12, HttpStatus.BAD_REQUEST);

        assertThat(extract.body().jsonPath().getString("message"))
                .isEqualTo("[ERROR] 페이지는 자연수여야 합니다.");
    }

    @Test
    void 전체상품_중_자연수가_아닌_페이지_당_원소수를_조회하는_경우() {

        var extract = createPagedProductResult(1, 0, HttpStatus.BAD_REQUEST);

        assertThat(extract.body().jsonPath().getString("message"))
                .isEqualTo("[ERROR] 페이지당 갯수는 자연수여야 합니다.");
    }

    @Test
    void 전체페이지보다_더_큰_페이지를_조회하는_경우() {

        var extract = createPagedProductResult(3, 12, HttpStatus.OK).as(ProductsResponse.class);
        assertThat(extract.getProducts().size()).isEqualTo(0);
    }

    @Test
    void 상품_전체를_조회() {

        var extract = findAllProducts(HttpStatus.OK).as(ProductsResponse.class);

        assertThat(extract.getProducts().size()).isEqualTo(13);
    }
}
