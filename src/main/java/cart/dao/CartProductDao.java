package cart.dao;

import cart.dto.CartProductResponse;

import java.util.List;

public interface CartProductDao {
    List<CartProductResponse> selectById(String email);
}
