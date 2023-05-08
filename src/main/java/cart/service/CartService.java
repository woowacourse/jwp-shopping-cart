package cart.service;

import cart.domain.cart.Cart;
import cart.domain.cart.CartRepository;
import cart.domain.member.Member;
import cart.domain.product.Product;
import cart.service.dto.MemberAuthDto;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class CartService {

    private final MemberService memberService;
    private final ProductService productService;
    private final CartRepository cartRepository;

    public CartService(MemberService memberService, ProductService productService, CartRepository cartRepository) {
        this.memberService = memberService;
        this.productService = productService;
        this.cartRepository = cartRepository;
    }

    public List<Product> findProductsInCartByUser(MemberAuthDto memberAuthDto) {
        Member signedUpMember = this.memberService.signUp(memberAuthDto);
        return this.cartRepository.findByUserId(signedUpMember.getId()).getProducts();
    }

    public void addProductToCartById(MemberAuthDto memberAuthDto, Long productId) {
        Member signedUpMember = this.memberService.signUp(memberAuthDto);
        Product product = this.productService.findById(productId);
        Cart cart = this.cartRepository.findByUserId(signedUpMember.getId());
        this.cartRepository.saveProductToCart(cart, product);
    }

    public void deleteProductFromCartById(MemberAuthDto memberAuthDto, Long productId) {
        Member signedUpMember = this.memberService.signUp(memberAuthDto);
        Product product = this.productService.findById(productId);
        Cart cart = this.cartRepository.findByUserId(signedUpMember.getId());
        this.cartRepository.deleteProductFromCart(cart, product);
    }
}
