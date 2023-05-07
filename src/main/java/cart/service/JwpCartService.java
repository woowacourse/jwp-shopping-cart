package cart.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cart.dto.AuthInfo;
import cart.dto.CartRequest;
import cart.dto.CartResponse;
import cart.dto.MemberResponse;
import cart.dto.ProductRequest;
import cart.dto.ProductResponse;
import cart.entity.CartItem;
import cart.entity.CartItemRepository;
import cart.entity.Member;
import cart.entity.MemberRepository;
import cart.entity.Product;
import cart.entity.ProductRepository;
import cart.exception.DomainException;
import cart.exception.ExceptionCode;

@Service
@Transactional(readOnly = true)
public class JwpCartService {

    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final CartItemRepository cartItemRepository;

    public JwpCartService(ProductRepository productRepository, MemberRepository memberRepository,
        CartItemRepository cartItemRepository) {
        this.productRepository = productRepository;
        this.memberRepository = memberRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @PostConstruct
    private void setUp() {
        addMember("email1@email.com", "password1");
        addMember("email2@email.com", "password2");
        addProduct(new ProductRequest("asdf", "http://www.naver.com", 1234));
        addProduct(new ProductRequest("qwer", "http://www.naver.com", 1234));
    }

    public List<ProductResponse> findAllProducts() {
        return productRepository.findAll()
            .stream()
            .map(productDto -> Product.of(
                productDto.getId(),
                productDto.getName(),
                productDto.getImgUrl(),
                productDto.getPrice()))
            .map(ProductResponse::new)
            .collect(Collectors.toList());
    }

    @Transactional
    public void addProduct(ProductRequest productRequest) {
        Product product = Product.of(
            null,
            productRequest.getName(),
            productRequest.getImgUrl(),
            productRequest.getPrice()
        );
        productRepository.save(product);
    }

    @Transactional
    public void updateProductById(ProductRequest productRequest, Long id) {
        Product product = Product.of(
            null,
            productRequest.getName(),
            productRequest.getImgUrl(),
            productRequest.getPrice()
        );
        productRepository.updateById(product, id);
    }

    @Transactional
    public void deleteProductById(Long id) {
        cartItemRepository.deleteByProductID(id);
        productRepository.deleteById(id);

    }

    public List<MemberResponse> findAllMembers() {
        return memberRepository.findAll()
            .stream()
            .map(MemberResponse::new)
            .collect(Collectors.toList());
    }

    @Transactional
    public void addMember(String email, String password) {
        Member member = Member.of(null, email, password);
        memberRepository.save(member);
    }

    @Transactional
    public void addProductToCart(AuthInfo authInfo, CartRequest cartRequest) {
        Member member = memberRepository.findByEmailAndPassword(authInfo.getEmail(), authInfo.getPassword());
        cartItemRepository.save(CartItem.of(null, member.getId(), cartRequest.getProductId()));
    }

    @Transactional
    public void deleteProductFromCart(AuthInfo authInfo, Long id) {
        Member member = memberRepository.findByEmailAndPassword(authInfo.getEmail(), authInfo.getPassword());
        if (Objects.equals(member.getId(), cartItemRepository.findById(id).getMemberId())) {
            cartItemRepository.deleteById(id);
            return;
        }
        throw new DomainException(ExceptionCode.AUTHORIZATION_FAIL);
    }

    public List<CartResponse> findAllCartItems(AuthInfo authInfo) {
        Long memberId = memberRepository.findByEmailAndPassword(authInfo.getEmail(), authInfo.getPassword()).getId();
        return cartItemRepository.findAll(memberId)
            .stream()
            .map(cart -> new CartResponse(cart, productRepository.findById(cart.getProductId())))
            .collect(Collectors.toList());
    }
}
