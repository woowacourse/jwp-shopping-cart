package cart.service;

import cart.dao.CartDao;
import cart.dao.H2CartDao;
import cart.dto.request.CartRequest;
import cart.dto.response.CartResponse;
import cart.entity.CartEntity;
import cart.exception.AuthorizationException;
import cart.exception.ResourceNotFoundException;
import cart.mapper.CartMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {
    private final CartDao cartDao;
    private final CartMapper cartMapper;

    public CartService(H2CartDao cartDao, CartMapper cartMapper) {
        this.cartDao = cartDao;
        this.cartMapper = cartMapper;
    }

    public CartResponse create(CartRequest cartRequest, Long memberId) {
        validateExist(cartRequest, memberId);
        CartEntity cart = cartMapper.requestToCartEntity(memberId, cartRequest);

        CartEntity save = cartDao.save(cart, cartRequest.getProductId(), memberId)
                .orElseThrow(() -> new ResourceNotFoundException("데이터가 정상적으로 저장되지 않았습니다." + System.lineSeparator() + "사용자 id : " + memberId));

        return cartMapper.entityToResponse(save);
    }

    private void validateExist(CartRequest cartRequest, Long memberId) {
        if (isExistProduct(cartRequest.getProductId(), memberId)) {
            throw new IllegalArgumentException("이미 존재하는 상품입니다." + System.lineSeparator() + "productId : " + cartRequest.getProductId());
        }
    }

    private boolean isExistProduct(Long productId, Long memberId) {
        return cartDao.existByMemberIdAndProductId(memberId, productId);
    }

    public List<CartResponse> findAll() {
        List<CartEntity> members = cartDao.findAll();
        return members.stream()
                .map(cartMapper::entityToResponse)
                .collect(Collectors.toList());
    }

    public List<CartResponse> findAllByMemberId(Long memberId) {
        List<CartEntity> carts = cartDao.findByMemberId(memberId);
        return carts.stream()
                .map(cartMapper::entityToResponse)
                .collect(Collectors.toList());
    }

    public CartResponse update(CartRequest cartRequest, Long cartId, Long memberId) {
        validateCartOwner(cartId, memberId);
        CartEntity cart = cartDao.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 장바구니를 찾을 수 없습니다." + System.lineSeparator() + "cartId : " + cartId));
        cart.replace(cartRequest.getCount());
        cartDao.update(cart);
        return cartMapper.entityToResponse(cart);
    }

    public void deleteById(Long cartId, Long memberId) {
        validateCartOwner(cartId, memberId);
        CartEntity cart = cartDao.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 장바구니를 찾을 수 없습니다." + System.lineSeparator() + "cartId : " + cartId));
        cartDao.deleteById(cart.getId());
    }

    private void validateCartOwner(Long cartId, Long memberId) {
        if (!cartDao.existByIdAndMemberId(cartId, memberId)) {
            throw new AuthorizationException("해당 작업에 대한 권한이 존재하지 않습니다." + System.lineSeparator() + "사용자 id : " + memberId);
        }
    }
}
