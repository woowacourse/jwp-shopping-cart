package cart.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CartTest {

    private Cart cart;

    @BeforeEach
    void setting() {
        final List<Product> products = new ArrayList<>();
        products.add(new Product(1, "name1", "image1", 1));
        products.add(new Product(2, "name2", "image2", 2));
        products.add(new Product(3, "name3", "image3", 3));

        this.cart = new Cart(1, products);
    }

    @DisplayName("이미 존재하는 아이템을 추가하면 예외가 발생한다")
    @Test
    void add_invalid_alreadyExistProduct() {
        //given
        final Product product = new Product(1, "name1", "image1", 1);

        //when,then
        assertThatThrownBy(() -> cart.add(product))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("아이템을 추가한다")
    @Test
    void add() {
        //given
        final Product product = new Product(4, "name4", "image4", 4);

        //when
        cart.add(product);
        final List<Product> products = cart.getProducts();

        //then
        assertThat(products).hasSize(4);
    }

    @DisplayName("존재하지 않는 상품을 제거할 수 없다")
    @Test
    void delete_notExistingProduct() {
        //given
        final Product nonexistentProduct = new Product(4, "name4", "image4", 4);

        //when,then
        assertThatThrownBy(() -> cart.delete(nonexistentProduct))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("카트에서 상품을 제거한다")
    @Test
    void delete() {
        //given
        final Product exitingProduct = new Product(1, "name1", "image1", 1);

        //when
        cart.delete(exitingProduct);
        final List<Product> products = cart.getProducts();

        //then
        assertThat(products).hasSize(2);
    }

}
