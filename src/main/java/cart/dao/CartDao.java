package cart.dao;

public interface CartDao {
    int insert(int productId, String email);

    int delete(int cartId, String email);
}
