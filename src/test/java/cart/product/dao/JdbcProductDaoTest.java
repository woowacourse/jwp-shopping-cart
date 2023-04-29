package cart.product.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cart.product.domain.Name;
import cart.product.domain.Price;
import cart.product.domain.Product;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

@JdbcTest
@Import(JdbcProductDao.class)
class JdbcProductDaoTest {
    
    @Autowired
    private JdbcProductDao jdbcProductDao;
    
    @Test
    @DisplayName("상품 목록 insert 테스트")
    void insert() {
        //given
        final Product product = new Product(new Name("스파게티"), "https://hello", new Price(1000));
        
        //when
        final long id = jdbcProductDao.insert(product);
        final Product insertedProduct = jdbcProductDao.findByID(id).orElseThrow(NoSuchElementException::new);
        
        //then
        assertEquals(product.getName().getValue(), insertedProduct.getName().getValue());
    }
    
    @Test
    @DisplayName("상품 목록 findAll 테스트")
    void findAll() {
        //given
        final int initialSize = jdbcProductDao.findAll().size();
        final Product product1 = new Product(new Name("스파게티"), "https://hello", new Price(1000));
        final Product product2 = new Product(new Name("피자"), "https://hello", new Price(2000));
        final Product product3 = new Product(new Name("치킨"), "https://hello", new Price(3000));
        
        //when
        jdbcProductDao.insert(product1);
        jdbcProductDao.insert(product2);
        jdbcProductDao.insert(product3);
        
        //then
        assertEquals(3, jdbcProductDao.findAll().size() - initialSize);
    }
    
    @Test
    @DisplayName("상품 deleteByID 테스트")
    void deleteByID() {
        //given
        final Product product1 = new Product(new Name("망고"), "https://mango", new Price(1000));
        final Product product2 = new Product(new Name("에코"), "https://echo", new Price(2000));
        final Product product3 = new Product(new Name("포비"), "https://pobi", new Price(3000));
        final long id1 = jdbcProductDao.insert(product1);
        jdbcProductDao.insert(product2);
        jdbcProductDao.insert(product3);

        final int initialSize = jdbcProductDao.findAll().size();
        
        //when
        jdbcProductDao.deleteByID(id1);
        
        //then
        final int updatedSize = jdbcProductDao.findAll().size();
        assertEquals(1, initialSize - updatedSize);
    }
    
    @Test
    @DisplayName("상품 update 테스트")
    void update() {
        //given
        final Product product1 = new Product(new Name("망고"), "https://mango", new Price(1000));
        final Product product2 = new Product(new Name("에코"), "https://echo", new Price(2000));
        final Product product3 = new Product(new Name("포비"), "https://pobi", new Price(3000));

        final long id1 = jdbcProductDao.insert(product1);
        jdbcProductDao.insert(product2);
        jdbcProductDao.insert(product3);
        
        //when
        final Product updatedProduct = new Product(id1, new Name("망고"), "https://mango", new Price(5000));
        jdbcProductDao.update(updatedProduct);
        
        //then
        final Product result = jdbcProductDao.findByID(id1).orElseThrow(NoSuchElementException::new);
        assertEquals(5000, result.getPrice().getValue());
    }

    @Test
    @DisplayName("상품 findByID 테스트")
    void findByID() {
        //given
        final Product product = new Product(new Name("망고"), "https://mango", new Price(1000));
        final long id = jdbcProductDao.insert(product);

        //when
        final Product result = jdbcProductDao.findByID(id).orElseThrow(NoSuchElementException::new);

        //then
        assertEquals(id, result.getId());
        assertEquals("망고", result.getName().getValue());
        assertEquals("https://mango", result.getImage());
        assertEquals(1000, result.getPrice().getValue());
    }
}
