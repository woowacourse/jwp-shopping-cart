package cart.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cart.dto.MemberResponse;
import cart.dto.ProductRequest;
import cart.dto.ProductResponse;
import cart.entity.MemberRepository;
import cart.entity.Product;
import cart.entity.ProductRepository;

@Service
@Transactional(readOnly = true)
public class JwpCartService {

    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    public JwpCartService(ProductRepository productRepository, MemberRepository memberRepository) {
        this.productRepository = productRepository;
        this.memberRepository = memberRepository;
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
}
