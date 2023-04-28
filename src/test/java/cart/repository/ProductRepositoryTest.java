package cart.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import cart.dao.ProductDao;
import cart.domain.Product;
import cart.entity.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import java.util.List;

@SpringBootTest
@Sql("classpath:schema.sql")
class ProductRepositoryTest {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private cart.repository.ProductRepository productRepository;

    private final Product product = new Product("item1", 1000, "https://");

    @BeforeEach
    void setUp() {
        productDao.insert(new ProductEntity("pizza", 1000,
                "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a3/Eq_it-na_pizza-margherita_sep2005_sml.jpg/800px-Eq_it-na_pizza-margherita_sep2005_sml.jpg"));
        productDao.insert(new ProductEntity("salad", 2000,
                "https://upload.wikimedia.org/wikipedia/commons/thumb/9/94/Salad_platter.jpg/1200px-Salad_platter.jpg"));
    }

    @Test
    void 상품_데이터_삽입() {
        long id = productRepository.insert(product);

        assertThat(id).isEqualTo(3L);
    }

    @Test
    void 단일_상품_데이터_조회() {
        Product product = productRepository.findById(1L);

        assertThat(product.getName()).isEqualTo("pizza");
    }

    @Test
    void 모든_상품_데이터_조회() {
        List<Product> products = productRepository.findAll();

        assertThat(products.size()).isEqualTo(2);
    }

    @Test
    void 상품_데이터_수정() {
        long id = 1L;
        Product newProduct = new Product("apple pizza", 10000, "https://");

        productRepository.update(id, newProduct);

        Product updatedProduct = productRepository.findById(id);
        assertThat(updatedProduct.getName()).isEqualTo("apple pizza");
    }

    @Test
    void 상품_데이터_삭제() {
        productRepository.delete(2L);

        assertThat(productRepository.findAll().size()).isEqualTo(1);
    }
}
