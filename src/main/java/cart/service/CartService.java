package cart.service;

import cart.dao.CartDaoImpl;
import cart.domain.Cart;
import cart.dto.request.CartRequest;
import cart.dto.response.CartResponse;
import cart.entity.CartEntity;
import cart.exception.ResourceNotFoundException;
import cart.mapper.CartMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class CartService {
    private final CartDaoImpl cartDao;
    private final CartMapper cartMapper;

    public CartService(CartDaoImpl cartDao, CartMapper cartMapper) {
        this.cartDao = cartDao;
        this.cartMapper = cartMapper;
    }

    public CartResponse create(CartRequest cartRequest, long memberId) {
        Cart cart = cartMapper.requestToCart(cartRequest);
        CartEntity save = cartDao.save(cart, cartRequest.getProductId(), memberId)
                .orElseThrow(() -> new ResourceNotFoundException("데이터가 정상적으로 저장되지 않았습니다."));
        return cartMapper.entityToResponse(save);
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

    public CartResponse update(CartRequest cartRequest, Long id) {
        CartEntity cart = cartDao.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 장바구니를 찾을 수 없습니다." + System.lineSeparator() + "id : " + id));
        cart.replace(cartRequest);
        cartDao.update(cart);
        return cartMapper.entityToResponse(cart);
    }

    public void deleteById(Long id) {
        cartDao.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 사용자를 찾을 수 없습니다." + System.lineSeparator() + "id : " + id));
        cartDao.deleteById(id);
    }
}
