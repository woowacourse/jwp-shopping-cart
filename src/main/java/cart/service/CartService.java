package cart.service;

import cart.auth.MemberInfo;
import cart.domain.Cart;
import cart.domain.Product;
import cart.dto.request.ProductRequestDto;
import cart.dto.response.ProductDto;
import cart.excpetion.CartException;
import cart.repository.CartRepository;
import cart.repository.ProductRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartService(final CartRepository cartRepository, final ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    public void addProduct(final MemberInfo memberInfo, final ProductRequestDto productRequestDto) {
        final Optional<Product> product = productRepository.findBy(productRequestDto.getProductId());
        if (product.isPresent()) {
            add(memberInfo, product.get());
            return;
        }
        throw new CartException("존재하지 않는 상품을 카트에 등록할 수 없습니다");
    }

    private void add(final MemberInfo memberInfo, final Product product) {
        try {
            final Cart cart = cartRepository.getCartOf(memberInfo.getId());
            cart.add(product);
            cartRepository.save(cart);
        } catch (IllegalStateException e) {
            throw new CartException(e.getMessage());
        }
    }

    public void deleteProduct(final MemberInfo memberInfo, final Integer productId) {
        final Optional<Product> product = productRepository.findBy(productId);
        if (product.isPresent()) {
            delete(memberInfo, product.get());
            return;
        }
        throw new CartException("존재하지 않는 상품을 카트에 등록할 수 없습니다");
    }

    private void delete(final MemberInfo memberInfo, final Product product) {
        try {
            final Cart cart = cartRepository.getCartOf(memberInfo.getId());
            cart.delete(product);
            cartRepository.save(cart);
        } catch (IllegalStateException e) {
            throw new CartException(e.getMessage());
        }
    }

    public List<ProductDto> getProductsOf(MemberInfo memberInfo) {
        final Cart cart = cartRepository.getCartOf(memberInfo.getId());
        final ArrayList<ProductDto> productDtos = new ArrayList<>();
        for (Product product : cart.getProducts()) {
            productDtos.add(ProductDto.of(product));
        }
        return productDtos;
    }
}
