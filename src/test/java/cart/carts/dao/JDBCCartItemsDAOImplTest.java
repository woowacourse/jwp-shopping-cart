package cart.carts.dao;

import cart.carts.domain.CartItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

@JdbcTest
@Import(JDBCCartItemsDAOImpl.class)
class JDBCCartItemsDAOImplTest {
    
    @Autowired
    private JDBCCartItemsDAOImpl cartItemsDAO;
    
    @Test
    @DisplayName("CartItem 생성")
    void create() {
        //given
        final long productId = 1L;
        
        //when
        final CartItem cartItem = this.cartItemsDAO.create(productId);
        
        //then
        Assertions.assertEquals(1L, cartItem.getId());
        Assertions.assertEquals(productId, cartItem.getProductId());
    }
    
    @Test
    @DisplayName("CartItem 삭제")
    void delete() {
        //given
        final long productId = 1L;
        final CartItem cartItem = this.cartItemsDAO.create(productId);
        final int initialSize = this.cartItemsDAO.findAll().size();
        
        //when
        this.cartItemsDAO.delete(cartItem);
        
        //then
        final int finalSize = this.cartItemsDAO.findAll().size();
        Assertions.assertEquals(initialSize - 1, finalSize);
    }
    
    @Test
    @DisplayName("CartItem 조회")
    void findById() {
        //given
        final long productId = 1L;
        final CartItem cartItem = this.cartItemsDAO.create(productId);
        
        //when
        final CartItem foundCartItem = this.cartItemsDAO.findById(cartItem.getId());
        
        //then
        Assertions.assertEquals(cartItem, foundCartItem);
    }
    
    @Test
    @DisplayName("CartItem 전체 조회")
    void findAll() {
        //given
        final long productId = 1L;
        final CartItem cartItem = this.cartItemsDAO.create(productId);
        
        //when
        final int size = this.cartItemsDAO.findAll().size();
        
        //then
        Assertions.assertEquals(1, size);
    }
    
}