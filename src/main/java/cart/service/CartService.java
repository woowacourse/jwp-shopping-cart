package cart.service;

import cart.dao.CartDao;
import cart.dao.H2CartDao;
import cart.domain.Cart;
import cart.dto.request.CartRequest;
import cart.dto.response.CartResponse;
import cart.entity.CartEntity;
import cart.exception.AuthorizationException;
import cart.exception.ResourceNotFoundException;
import cart.mapper.CartMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CartService {
    private final CartDao cartDao;
    private final CartMapper cartMapper;

    public CartService(H2CartDao cartDao, CartMapper cartMapper) {
        this.cartDao = cartDao;
        this.cartMapper = cartMapper;
    }

    public CartResponse create(CartRequest cartRequest, long memberId) {
        validateExist(cartRequest, memberId);
        Cart cart = cartMapper.requestToCart(cartRequest);

        CartEntity save = cartDao.save(cart, cartRequest.getProductId(), memberId)
                .orElseThrow(() -> new ResourceNotFoundException("데이터가 정상적으로 저장되지 않았습니다." + System.lineSeparator() + "사용자 id : " + memberId));

        return cartMapper.entityToResponse(save);
    }

    private void validateExist(CartRequest cartRequest, long memberId) {
        if (isExistProduct(cartRequest, memberId)) {
            throw new IllegalArgumentException("이미 존재하는 상품입니다." + System.lineSeparator() + "productId : " + cartRequest.getProductId());
        }
    }

    private boolean isExistProduct(CartRequest cartRequest, long memberId) {
        List<CartEntity> carts = cartDao.findByMemberId(memberId);
        return carts.stream().map(CartEntity::getProductId)
                .anyMatch(id -> id == cartRequest.getProductId());
    }

    public List<CartResponse> findAll() {
        List<CartEntity> members = cartDao.findAll();
        return members.stream()
                .map(cartMapper::entityToResponse)
                .collect(Collectors.toList());
    }

    public List<CartResponse> findAllByMemberId(long memberId) {
        List<CartEntity> carts = cartDao.findByMemberId(memberId);
        return carts.stream()
                .map(cartMapper::entityToResponse)
                .collect(Collectors.toList());
    }

    public CartResponse update(CartRequest cartRequest, Long cartId, Long memberId) {
        CartEntity cart = cartDao.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 장바구니를 찾을 수 없습니다." + System.lineSeparator() + "cartId : " + cartId));
        validateOwner(cart.getMemberId(), memberId);
        cart.replace(cartRequest.getCount());
        cartDao.update(cart);
        return cartMapper.entityToResponse(cart);
    }

    public void deleteById(Long id, Long memberId) {
        CartEntity cart = cartDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("해당 장바구니를 찾을 수 없습니다." + System.lineSeparator() + "cartId : " + id));
        validateOwner(cart.getMemberId(), memberId);
        cartDao.deleteById(cart.getId());
    }

    private void validateOwner(Long originId, Long currentId) {
        if (!Objects.equals(originId, currentId)) {
            throw new AuthorizationException("해당 작업에 대한 권한이 존재하지 않습니다." + System.lineSeparator() + "사용자 id : " + currentId);
        }
    }
}
