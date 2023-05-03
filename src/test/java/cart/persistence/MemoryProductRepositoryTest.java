package cart.persistence;

import cart.entity.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Transactional
class MemoryProductRepositoryTest {

    private MemoryProductRepository memoryProductRepository;

    @BeforeEach
    void init() {
        this.memoryProductRepository = new MemoryProductRepository();
    }

    @Test
    @DisplayName("Product 객체를 넣을 수 있다")
    void test_insert() {
        //given
//        Product salmon = new Product(null,
//                new ProductName("salmon"),
//                new ProductImage("https://www.salmonlover.com"),
//                new ProductPrice(17000));

        Product salmon = new Product(null, "salmon", "https://www.salmonlover.com", 17000);

        //when
        Integer actualId = memoryProductRepository.insert(salmon);

        //then
        assertThatThrownBy(() -> memoryProductRepository.findSameProductExist(salmon)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("모든 Product 객체를 반환할 수 있다")
    void test_findAll() {
        //given
//        Product salmon = new Product(1,
//                new ProductName("salmon"),
//                new ProductImage("https://www.salmonlover.com"),
//                new ProductPrice(17000));
//
//        Product pizza = new Product(2,
//                new ProductName("pizza"),
//                new ProductImage("http://www.pizzalover.com"),
//                new ProductPrice(40000));

        Product salmon = new Product(1,
                ("salmon"),
                ("https://www.salmonlover.com"),
                (17000));

        Product pizza = new Product(2,
                ("pizza"),
                ("http://www.pizzalover.com"),
                (40000));

        //when
        Integer actualSalmonId = memoryProductRepository.insert(salmon);
        Integer actualPizzaId = memoryProductRepository.insert(pizza);

        List<Product> allProducts = memoryProductRepository.findAll();

        //then
        assertThat(allProducts.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Product 객체를 업데이트 할 수 있다")
    void update() {
        //given
        Product salmon = new Product(1,
                ("before"),
                ("https://www.salmonlover.com"),
                (17000));

        Integer salmonId = memoryProductRepository.insert(salmon);

        Product pizza = new Product(salmonId,
                ("after"),
                ("http://www.pizzalover.com"),
                (40000));

        //when
        memoryProductRepository.update(salmonId, pizza);

        //then
        Assertions.assertDoesNotThrow(() -> memoryProductRepository.findSameProductExist(salmon));
    }

    @Test
    @DisplayName("해당하는 Product 객체를 삭제할 수 있다")
    void remove() {
        //given
        Product salmon = new Product(1,
                ("before"),
                ("https://www.salmonlover.com"),
                (17000));

        //when
        Integer salmonId = memoryProductRepository.insert(salmon);
        Integer actualId = memoryProductRepository.remove(salmonId);

        //then
        assertThat(actualId).isEqualTo(salmonId);
    }
}
