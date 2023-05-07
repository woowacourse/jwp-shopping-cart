package cart.service;

import cart.dao.cart.CartItemRepository;
import cart.domain.Id;
import cart.domain.cart.CartItem;
import cart.domain.product.Product;
import cart.dto.CartItemRequest;
import cart.dto.ProductResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CartItemService {

    private final CartItemRepository cartItemRepository;

    public CartItemService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    public void addProduct(Long memberId, CartItemRequest cartItemRequest) {
        CartItem cartItem = new CartItem(new Id(cartItemRequest.getProductId()));

        cartItemRepository.addProduct(memberId, cartItem);
    }

    public List<ProductResponse> findAllProducts(Long memberId) {
        List<Product> products = cartItemRepository.getAllProducts(memberId);
        
        return products.stream()
                .map(product -> new ProductResponse(product.getId(), product.getName(), product.getImageUrl(), product.getPrice()))
                .collect(Collectors.toList());
    }

    public void removeProduct(Long memberId, CartItemRequest cartItemRequest) {
        cartItemRepository.removeProduct(memberId, new CartItem(new Id(cartItemRequest.getProductId())));
    }
}
