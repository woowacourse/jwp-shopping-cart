package cart.controller;

import cart.auth.AuthMember;
import cart.dao.CartDaoImpl;
import cart.dto.request.CartRequest;
import cart.dto.response.CartResponse;
import cart.entity.CartEntity;
import cart.entity.MemberEntity;
import cart.exception.ApiException;
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

@RestController
@RequestMapping("/carts")
public class CartController {
    private final CartService cartService;
    private final CartDaoImpl cartDao;

    public CartController(CartService cartService, CartDaoImpl cartDao) {
        this.cartService = cartService;
        this.cartDao = cartDao;
    }

    @GetMapping
    public ResponseEntity<List<CartResponse>> findByMemberId(@AuthMember MemberEntity member) {
        List<CartResponse> cartResponse = cartService.findAllByMemberId(member.getId());
        return ResponseEntity.ok().body(cartResponse);
    }

    @PostMapping
    public ResponseEntity<CartResponse> create(@RequestBody @Valid CartRequest cartRequest, @AuthMember MemberEntity member) {
        CartResponse cartResponse = cartService.create(cartRequest, member.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(cartResponse);
    }

    @PutMapping("/{cartId}")
    public ResponseEntity<CartResponse> update(@PathVariable Long cartId, @RequestBody @Valid CartRequest cartRequest, @AuthMember MemberEntity member) {
        validateMember(cartId, member);
        CartResponse updated = cartService.update(cartRequest, cartId);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Object> delete(@PathVariable Long cartId) {
        cartService.deleteById(cartId);
        return ResponseEntity.ok().build();
    }

    private void validateMember(Long cartId, MemberEntity member) {
        CartEntity cart = cartDao.findById(cartId)
                .orElseThrow(() -> new ApiException(""));
        if (!Objects.equals(member.getId(), cart.getMember().getId())) {
            throw new ApiException("해당 작업에 대한 권힌이 존재하지 않습니다.");
        }
    }
}
