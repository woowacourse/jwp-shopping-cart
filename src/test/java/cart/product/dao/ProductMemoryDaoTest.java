package cart.product.dao;

import cart.config.DBTransactionExecutor;
import cart.product.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

@SuppressWarnings("NonAsciiCharacters")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@JdbcTest
class ProductMemoryDaoTest {
    @RegisterExtension
    private DBTransactionExecutor dbTransactionExecutor;
    
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    
    private ProductDao productDao;
    
    @Autowired
    public ProductMemoryDaoTest(final JdbcTemplate jdbcTemplate) {
        dbTransactionExecutor = new DBTransactionExecutor(jdbcTemplate);
    }
    
    @BeforeEach
    void setUp() {
        productDao = new ProductMemoryDao(namedParameterJdbcTemplate);
    }
    
    @Test
    void 모든_상품_목록을_조회한다() {
        // given
        final Product firstProduct = new Product(1L, "홍고", "https://ca.slack-edge.com/TFELTJB7V-U04M4NFB5TN-e18b78fabe81-512", 10_000_000);
        final Product secondProduct = new Product(2L, "아벨", "https://ca.slack-edge.com/TFELTJB7V-U04LMNLQ78X-a7ef923d5391-512", 10_000_000);
        productDao.save(firstProduct);
        productDao.save(secondProduct);
        
        // when
        final List<Product> products = productDao.findAll();
        
        // then
        assertThat(products).containsExactly(firstProduct, secondProduct);
    }
    
    @Test
    void 상품을_저장한다() {
        // given
        final Product product = new Product(null, "홍고", "https://ca.slack-edge.com/TFELTJB7V-U04M4NFB5TN-e18b78fabe81-512", 10_000_000);
        
        // when
        final Long productId = productDao.save(product);
        
        // then
        assertThat(productId).isEqualTo(1L);
    }
    
    @Test
    void 상품을_수정한다() {
        // given
        final Product product = new Product(null, "홍고", "https://ca.slack-edge.com/TFELTJB7V-U04M4NFB5TN-e18b78fabe81-512", 10_000_000);
        long id = productDao.save(product);
        final Product updatedProduct = new Product(id, "아벨", "https://ca.slack-edge.com/TFELTJB7V-U04M4NFB5TN-e18b78fabe81-512", 10_000_000);
        productDao.update(updatedProduct);
        
        // when
        final Product actualProduct = productDao.findById(id);
        
        // then
        assertThat(actualProduct).isEqualTo(updatedProduct);
    }
    
    @Test
    void 상품을_삭제한다() {
        // given
        final Product product = new Product(null, "홍고", "https://ca.slack-edge.com/TFELTJB7V-U04M4NFB5TN-e18b78fabe81-512", 10_000_000);
        long id = productDao.save(product);
        
        // expect
        assertAll(
                () -> assertThatNoException().isThrownBy(() -> productDao.delete(id)),
                () -> assertThatThrownBy(() -> productDao.findById(id))
                        .isInstanceOf(EmptyResultDataAccessException.class)
        );
    }
}
