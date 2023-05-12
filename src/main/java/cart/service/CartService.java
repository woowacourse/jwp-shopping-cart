package cart.service;

import cart.auth.MemberInfo;
import cart.domain.Cart;
import cart.domain.Product;
import cart.dto.response.ProductDto;
import cart.excpetion.product.ProductNotFoundException;
import cart.repository.CartRepository;
import cart.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartService(final CartRepository cartRepository, final ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    public void addProduct(final MemberInfo memberInfo, final int productId) {
        final Product product = productRepository.findBy(productId)
                .orElseThrow(() -> {
                    throw new ProductNotFoundException("상품이 존재하지 않습니다.");
                });
        add(memberInfo, product);
    }

    private void add(final MemberInfo memberInfo, final Product product) {
        final Cart cart = cartRepository.getCartOf(memberInfo.getId());
        cart.add(product);
        cartRepository.save(cart);
    }

    public void deleteProduct(final MemberInfo memberInfo, final Integer productId) {
        final Product product = productRepository.findBy(productId)
                .orElseThrow(() -> {
                    throw new ProductNotFoundException("상품이 존재하지 않습니다.");
                });
        delete(memberInfo, product);
    }

    private void delete(final MemberInfo memberInfo, final Product product) {
        final Cart cart = cartRepository.getCartOf(memberInfo.getId());
        cart.delete(product);
        cartRepository.save(cart);
    }

    @Transactional(readOnly = true)
    public List<ProductDto> getProductsOf(MemberInfo memberInfo) {
        final Cart cart = cartRepository.getCartOf(memberInfo.getId());
        final ArrayList<ProductDto> productDtos = new ArrayList<>();
        for (Product product : cart.getProducts()) {
            productDtos.add(ProductDto.of(product));
        }
        return productDtos;
    }
}
