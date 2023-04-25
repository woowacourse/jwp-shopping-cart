package cart.product.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cart.product.domain.Name;
import cart.product.domain.Price;
import cart.product.domain.Product;
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
        final long id = this.jdbcProductDao.insert(product);
        final Product insertedProduct = this.jdbcProductDao.findByID(id);
        
        //then
        assertEquals(product.getName().getValue(), insertedProduct.getName().getValue());
    }
    
    @Test
    @DisplayName("상품 목록 findAll 테스트")
    void findAll() {
        //given
        final Product product1 = new Product(new Name("스파게티"), "https://hello", new Price(1000));
        final Product product2 = new Product(new Name("피자"), "https://hello", new Price(2000));
        final Product product3 = new Product(new Name("치킨"), "https://hello", new Price(3000));
        
        //when
        this.jdbcProductDao.insert(product1);
        this.jdbcProductDao.insert(product2);
        this.jdbcProductDao.insert(product3);
        
        //then
        assertEquals(3, this.jdbcProductDao.findAll().size());
    }
    
    @Test
    @DisplayName("상품 findByName 테스트")
    void findByName() {
        //given
        final Product product1 = new Product(new Name("망고"), "https://mango", new Price(1000));
        final Product product2 = new Product(new Name("에코"), "https://echo", new Price(2000));
        final Product product3 = new Product(new Name("포비"), "https://pobi", new Price(3000));
        
        this.jdbcProductDao.insert(product1);
        this.jdbcProductDao.insert(product2);
        this.jdbcProductDao.insert(product3);
        
        //when
        final Product result = this.jdbcProductDao.findByName("망고");
        
        //then
        assertEquals(product1.getName().getValue(), result.getName().getValue());
        assertEquals(product1.getImage(), result.getImage());
        assertEquals(product1.getPrice().getValue(), result.getPrice().getValue());
    }
    
    @Test
    @DisplayName("상품 deleteByID 테스트")
    void deleteByID() {
        //given
        final Product product1 = new Product(new Name("망고"), "https://mango", new Price(1000));
        final Product product2 = new Product(new Name("에코"), "https://echo", new Price(2000));
        final Product product3 = new Product(new Name("포비"), "https://pobi", new Price(3000));
        
        final long id1 = this.jdbcProductDao.insert(product1);
        final long id2 = this.jdbcProductDao.insert(product2);
        final long id3 = this.jdbcProductDao.insert(product3);
        
        //when
        this.jdbcProductDao.deleteByID(id1);
        this.jdbcProductDao.deleteByID(id2);
        
        //then
        assertEquals(1, this.jdbcProductDao.findAll().size());
    }
    
    @Test
    @DisplayName("상품 update 테스트")
    void update() {
        //given
        final Product product1 = new Product(new Name("망고"), "https://mango", new Price(1000));
        final Product product2 = new Product(new Name("에코"), "https://echo", new Price(2000));
        final Product product3 = new Product(new Name("포비"), "https://pobi", new Price(3000));
        
        final long id1 = this.jdbcProductDao.insert(product1);
        final long id2 = this.jdbcProductDao.insert(product2);
        final long id3 = this.jdbcProductDao.insert(product3);
        
        //when
        final Product updatedProduct = new Product(id2, new Name("에코"), "https://echo", new Price(5000));
        this.jdbcProductDao.update(updatedProduct);
        
        //then
        assertEquals(5000, this.jdbcProductDao.findByID(id2).getPrice().getValue());
    }
}
