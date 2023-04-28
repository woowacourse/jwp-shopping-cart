package cart.service;

import cart.domain.cart.Cart;
import cart.domain.member.Member;
import cart.domain.product.Product;
import cart.dto.member.MemberLoginRequestDto;
import cart.dto.product.ProductsResponseDto;
import cart.exception.ProductNotFoundException;
import cart.repository.cart.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final MemberService memberService;
    private final ProductService productService;

    @Autowired
    public CartService(final CartRepository cartRepository, final MemberService memberService, final ProductService productService) {
        this.cartRepository = cartRepository;
        this.memberService = memberService;
        this.productService = productService;
    }

    @Transactional(readOnly = true)
    public ProductsResponseDto findAll(final MemberLoginRequestDto memberLoginRequestDto) {
        Member member = memberService.findMember(memberLoginRequestDto);
        List<Cart> carts = cartRepository.findAllByMember(member);
        return getProductsResponseDto(carts);
    }

    private ProductsResponseDto getProductsResponseDto(final List<Cart> carts) {
        List<Product> products = carts.stream()
                .map(Cart::getProduct)
                .collect(Collectors.toList());

        return ProductsResponseDto.from(products);
    }

    @Transactional
    public void addCart(final MemberLoginRequestDto memberLoginRequestDto, final Long productId) {
        Member member = memberService.findMember(memberLoginRequestDto);
        Product product = productService.findById(productId);

        Cart cart = Cart.from(member, product);
        cartRepository.save(cart);
    }

    @Transactional
    public void deleteCart(final MemberLoginRequestDto memberLoginRequestDto, final Long productId) {
        Member member = memberService.findMember(memberLoginRequestDto);
        Product product = productService.findById(productId);
        List<Cart> memberCarts = cartRepository.findAllByMember(member);

        validateCartHasProduct(product, memberCarts);

        cartRepository.delete(product);
    }

    private void validateCartHasProduct(final Product product, final List<Cart> memberCarts) {
        List<Product> memberCartProducts = memberCarts.stream()
                .map(Cart::getProduct)
                .collect(Collectors.toList());

        if (!memberCartProducts.contains(product)) {
            throw new ProductNotFoundException();
        }
    }
}
