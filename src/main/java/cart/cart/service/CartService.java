package cart.cart.service;

import cart.cart.dao.CartDAO;
import cart.cart.domain.Cart;
import cart.cart.dto.CartRequestDTO;
import cart.catalog.dao.CatalogDAO;
import cart.catalog.dto.ProductResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final CartDAO cartDAO;
    private final CatalogDAO catalogDao;

    public CartService(final CartDAO cartDAO, final CatalogDAO catalogDao) {
        this.cartDAO = cartDAO;
        this.catalogDao = catalogDao;
    }

    public List<ProductResponseDTO> findUserCart(final long userId) {
        final List<Cart> carts = this.cartDAO.findUserCart(userId);
        return carts.stream()
                .map(Cart::getProductId)
                .map(this.catalogDao::findByID)
                .map(ProductResponseDTO::create)
                .collect(Collectors.toList());
    }

    public void createCart(final CartRequestDTO cartRequestDTO) {
        this.cartDAO.create(cartRequestDTO);
    }

    public void deleteCart(final CartRequestDTO cartRequestDTO) {
        final Cart cart = this.cartDAO.find(cartRequestDTO);
        this.cartDAO.delete(cart);
    }

    public void clearCart(final CartRequestDTO cartRequestDTO) {
        final long userId = cartRequestDTO.getUserId();
        this.cartDAO.clear(userId);
    }
}