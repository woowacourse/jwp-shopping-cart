package cart.controller;

import cart.auth.AuthMember;
import cart.dao.H2CartDao;
import cart.dto.request.CartRequest;
import cart.dto.response.CartResponse;
import cart.entity.CartEntity;
import cart.entity.MemberEntity;
import cart.exception.ApiException;
import cart.exception.AuthorizationException;
import cart.exception.ResourceNotFoundException;
import cart.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/carts")
public class CartController {
    private final CartService cartService;
    private final H2CartDao cartDao;

    public CartController(CartService cartService, H2CartDao cartDao) {
        this.cartService = cartService;
        this.cartDao = cartDao;
    }

    @GetMapping
    public ResponseEntity<List<CartResponse>> findByMemberId(@AuthMember MemberEntity member) {
        List<CartResponse> cartResponse = cartService.findAllByMemberId(member.getId());
        return ResponseEntity.ok().body(cartResponse);
    }

    @PostMapping
    public ResponseEntity<CartResponse> addProduct(@RequestBody @Valid CartRequest cartRequest, @AuthMember MemberEntity member) {
        List<CartEntity> carts = cartDao.findByMemberId(member.getId());

        Optional<CartEntity> cartEntity = carts.stream()
                .filter(cart -> cart.getProduct().getId() == cartRequest.getProductId())
                .findAny();

        CartResponse cartResponse = saveOrUpdate(cartRequest, member, cartEntity);

        return ResponseEntity.status(HttpStatus.CREATED).body(cartResponse);
    }

    private CartResponse saveOrUpdate(CartRequest cartRequest, MemberEntity member, Optional<CartEntity> cartEntity) {
        if (cartEntity.isEmpty()) {
            return cartService.create(cartRequest, member.getId());
        }
        CartEntity cart = cartEntity.orElseThrow(() -> new ApiException("존재하지 않는 장바구니입니다."));
        return cartService.increaseCount(cart);
    }

    @PutMapping("/{cartId}")
    public ResponseEntity<CartResponse> update(@PathVariable Long cartId, @RequestBody @Valid CartRequest cartRequest, @AuthMember MemberEntity member) {
        validateMember(cartId, member);
        CartResponse updated = cartService.update(cartRequest, cartId);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> delete(@PathVariable Long cartId) {
        cartService.deleteById(cartId);
        return ResponseEntity.ok().build();
    }

    private void validateMember(Long cartId, MemberEntity member) {
        CartEntity cart = cartDao.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 상품을 찾을 수 없습니다.", cartId));
        if (!Objects.equals(member.getId(), cart.getMember().getId())) {
            throw new AuthorizationException();
        }
    }
}
