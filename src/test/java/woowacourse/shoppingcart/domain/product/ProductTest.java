package woowacourse.shoppingcart.domain.product;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.shoppingcart.exception.format.InvalidPriceException;
import woowacourse.shoppingcart.exception.format.InvalidStockException;

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
        assertThatCode(() -> Product.of(null, name, price, imageUrl, descrtion, stock)).doesNotThrowAnyException();
    }

    @DisplayName("상품을 생성 실패 - 잘못된 가격")
    @Test
    void createFailedByInvalidPrice() {
        //given
        final String name = "[든든] 전처리 양파 다이스 15mm(1.5*1.5*1.5/국내산) 1KG";
        final int price = 3;
        final String imageUrl = "https://user-images.githubusercontent.com/44823900/167772500-dff4dfb5-6ad2-48fe-937d-81bc6800d0e2.jpg";
        final String descrtion = "전처리 양파 다이스";
        final int stock = 76;

        //when
        assertThatThrownBy(() -> Product.of(null, name, price, imageUrl, descrtion, stock)).isInstanceOf(
                InvalidPriceException.class);
    }

    @DisplayName("상품을 생성 실패 - 잘못된 재고")
    @Test
    void createFailedByInvalidStock() {
        //given
        final String name = "[든든] 전처리 양파 다이스 15mm(1.5*1.5*1.5/국내산) 1KG";
        final int price = 300;
        final String imageUrl = "https://user-images.githubusercontent.com/44823900/167772500-dff4dfb5-6ad2-48fe-937d-81bc6800d0e2.jpg";
        final String descrtion = "전처리 양파 다이스";
        final int stock = -1;

        //when
        assertThatThrownBy(() -> Product.of(null, name, price, imageUrl, descrtion, stock)).isInstanceOf(
                InvalidStockException.class);
    }
}