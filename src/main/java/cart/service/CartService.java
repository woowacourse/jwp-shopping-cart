package cart.service;

import cart.domain.cart.User;
import cart.dto.cart.CartItemDto;
import cart.dto.cart.UserDto;
import cart.entity.CartEntity;
import cart.entity.ProductEntity;
import cart.repository.CartRepository;
import cart.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public CartItemDto addItem(UserDto userDto, Long productId) {
        User user = new User(userDto.getId(), userDto.getEmail());
        CartEntity entity = new CartEntity(null, user.getId(), productId);
        Optional<ProductEntity> nullableProductEntity = productRepository.findById(entity.getProductId());
        if (nullableProductEntity.isEmpty()) {
            throw new IllegalArgumentException("상품이 존재하지 않습니다.");
        }
        CartEntity savedEntity = cartRepository.save(entity);
        ProductEntity productEntity = nullableProductEntity.get();

        return CartItemDto.fromCartIdAndProductEntity(savedEntity.getId(), productEntity);
    }

    @Transactional(readOnly = true)
    public List<CartItemDto> findAllUserItems(UserDto userDto) {
        User user = new User(userDto.getId(), userDto.getEmail());
        List<CartEntity> entities = cartRepository.findByUserId(user.getId());
        return entities.stream()
                .map((cartEntity -> CartItemDto.fromCartIdAndProductEntity(cartEntity.getId(),
                        productRepository.findById(cartEntity.getProductId()).get())))
                .collect(toList());
    }

    @Transactional
    public void deleteById(Long id) {
        cartRepository.deleteById(id);
    }
}
