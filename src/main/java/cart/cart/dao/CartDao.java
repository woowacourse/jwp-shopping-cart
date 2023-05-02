package cart.cart.dao;

public interface CartDao {
    Long save(final Long productId, final Long id);
    
    void deleteAll();
}
