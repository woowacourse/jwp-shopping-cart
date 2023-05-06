package cart.service;

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
    public CartItemDto add(UserDto userDto, Long productId) {
        CartEntity entity = new CartEntity(null, userDto.getId(), productId);
        cartRepository.save(entity);
        Optional<ProductEntity> nullableProductEntity = productRepository.findById(entity.getProductId());
        if (nullableProductEntity.isEmpty()) {
            throw new IllegalStateException("회원이 존재하지 않습니다.");
        }
        ProductEntity productEntity = nullableProductEntity.get();

        return CartItemDto.fromProductEntity(productEntity);
    }

    @Transactional(readOnly = true)
    public List<CartItemDto> findAllUserItems(UserDto userDto) {
        List<CartEntity> entities = cartRepository.findByUserId(userDto.getId());
        return entities.stream()
                .map((cartEntity -> CartItemDto.fromProductEntity(
                        productRepository.findById(cartEntity.getProductId()).get())))
                .collect(toList());
    }

    @Transactional
    public void deleteById(Long id) {
        cartRepository.deleteById(id);
    }
}
