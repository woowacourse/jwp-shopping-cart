package cart.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cart.dto.AuthInfo;
import cart.dto.CartRequest;
import cart.dto.MemberResponse;
import cart.dto.ProductRequest;
import cart.dto.ProductResponse;
import cart.entity.Cart;
import cart.entity.CartRepository;
import cart.entity.Member;
import cart.entity.MemberRepository;
import cart.entity.Product;
import cart.entity.ProductRepository;

@Service
@Transactional(readOnly = true)
public class JwpCartService {

    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;

    public JwpCartService(ProductRepository productRepository, MemberRepository memberRepository,
        CartRepository cartRepository) {
        this.productRepository = productRepository;
        this.memberRepository = memberRepository;
        this.cartRepository = cartRepository;
    }

    @PostConstruct
    private void setUp() {
        addMember("email1", "password1");
        addMember("email2", "password2");
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
        cartRepository.save(Cart.of(null, member.getId(), cartRequest.getProductId()));
    }
}
