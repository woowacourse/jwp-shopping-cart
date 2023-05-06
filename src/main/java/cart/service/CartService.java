package cart.service;

import cart.dao.cart.CartDao;
import cart.dto.cart.CartResponse;
import cart.dto.item.ItemResponse;
import cart.entity.CartEntity;
import cart.entity.ItemEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartDao cartDao;

    @Transactional
    public Long save(String memberEmail, Long itemId) {
        Optional<CartEntity> cart = cartDao.findByEmailAndId(memberEmail, itemId);

        if (cart.isEmpty()) {
            return cartDao.save(memberEmail, itemId);
        }

        cartDao.update(memberEmail, cart.get().addQuantity());
        return itemId;
    }

    public List<CartResponse> findAll(String memberEmail) {
        Optional<List<CartEntity>> carts = cartDao.findAll(memberEmail);

        if (carts.isEmpty()) {
            return Collections.emptyList();
        }

        return convertItemEntityMapToCartItemEntityList(carts.get());
    }

    private List<CartResponse> convertItemEntityMapToCartItemEntityList(final List<CartEntity> carts) {
        return carts.stream()
                .map(cart -> new CartResponse(itemEntityToItemResponse(cart.getItem()), cart.getQuantity()))
                .collect(Collectors.toList());
    }

    private ItemResponse itemEntityToItemResponse(final ItemEntity item) {
        return new ItemResponse(item.getId(),
                item.getName(),
                item.getImageUrl(),
                item.getPrice());
    }

    public void delete(String memberEmail, Long itemId) {
        Optional<CartEntity> cart = cartDao.findByEmailAndId(memberEmail, itemId);

        if (cart.isEmpty()) {
            throw new IllegalArgumentException("삭제할 상품이 장바구니에 존재하지 않습니다.");
        }

        CartEntity retrievedCart = cart.get();

        if (retrievedCart.isSingleQuantity()) {
            cartDao.delete(memberEmail, itemId);
            return;
        }
        cartDao.update(memberEmail, retrievedCart.deleteQuantity());
    }
}
