package cart.service;

import cart.domain.cart.Cart;
import cart.domain.cart.CartRepository;
import cart.domain.product.Product;
import cart.service.dto.MemberAuthDto;
import cart.service.dto.MemberDto;
import cart.service.dto.ProductDto;
import java.util.List;
import java.util.stream.Collectors;
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

    public List<ProductDto> findProductsInCartByUser(MemberAuthDto memberAuthDto) {
        MemberDto signedUpMemberDto = this.memberService.signIn(memberAuthDto);
        List<Product> products = this.cartRepository.findByMemberId(signedUpMemberDto.getId()).getProducts();
        return products.stream()
                .map(ProductDto::from)
                .collect(Collectors.toList());
    }

    public void addProductToCartById(MemberAuthDto memberAuthDto, Long productId) {
        MemberDto signedUpMemberDto = this.memberService.signIn(memberAuthDto);
        ProductDto productDto = this.productService.findById(productId);
        Cart cart = this.cartRepository.findByMemberId(signedUpMemberDto.getId());
        this.cartRepository.saveProductToCart(cart, productDto.toDomain());
    }

    public void deleteProductFromCartById(MemberAuthDto memberAuthDto, Long productId) {
        MemberDto signedUpMemberDto = this.memberService.signIn(memberAuthDto);
        ProductDto productDto = this.productService.findById(productId);
        Cart cart = this.cartRepository.findByMemberId(signedUpMemberDto.getId());
        this.cartRepository.deleteProductFromCart(cart, productDto.toDomain());
    }
}
