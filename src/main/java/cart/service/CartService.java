package cart.service;

import cart.dao.JdbcCartDao;
import cart.service.dto.ProductDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {
    private final JdbcCartDao jdbcCartDao;

    public CartService(JdbcCartDao jdbcCartDao) {
        this.jdbcCartDao = jdbcCartDao;
    }

    public List<ProductDto> displayUserCart(final String userEmail) {
        return jdbcCartDao.findByUserEmail(userEmail).stream()
                .map(cart -> new ProductDto.Builder()
                        .id(cart.getId())
                        .name(cart.getName())
                        .price(cart.getPrice())
                        .image(cart.getImage())
                        .build()
                )
                .collect(Collectors.toList());
    }

    public void addCart(final Long userId, final Long productId) {
        jdbcCartDao.addProduct(userId, productId);
    }

    public void deleteCart(final Long cartId) {
        jdbcCartDao.delete(cartId);
    }
}
