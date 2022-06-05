package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductTest {

    @DisplayName("상품을 생성한다.")
    @Test
    void create() {
        //given
        final String name = "[든든] 전처리 양파 다이스 15mm(1.5*1.5*1.5/국내산) 1KG";
        final int price = 3940;
        final String imageUrl = "https://user-images.githubusercontent.com/44823900/167772500-dff4dfb5-6ad2-48fe-937d-81bc6800d0e2.jpg";
        final String descrtion = "전처리 양파 다이스";
        final int stock = 76;

        //when
        assertThatCode(() -> new Product(name, price, imageUrl, descrtion, stock)).doesNotThrowAnyException();
    }
}