package cart.service;

import cart.dao.MemberDao;
import cart.dao.ProductDao;
import cart.dao.cart.CartItemDao;
import cart.domain.cart.CartItem;
import cart.domain.product.Product;
import cart.dto.CartItemRequest;
import cart.dto.ProductResponse;
import cart.exception.NotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CartItemService {

    private final MemberDao memberDao;
    private final ProductDao productDao;
    private final CartItemDao cartItemDao;

    public CartItemService(MemberDao memberDao, ProductDao productDao, CartItemDao cartItemDao) {
        this.memberDao = memberDao;
        this.productDao = productDao;
        this.cartItemDao = cartItemDao;
    }

    public void addProduct(Long memberId, CartItemRequest cartItemRequest) {
        checkIfExistMember(memberId);
        Product product =  findProduct(cartItemRequest.getProductId());
        CartItem cartItem = new CartItem(product);
        cartItemDao.insert(memberId, cartItem);
    }

    public List<ProductResponse> findAllProducts(Long memberId) {
        checkIfExistMember(memberId);
        List<Product>  products = cartItemDao.findAll(memberId);
        return products.stream()
                .map(product -> new ProductResponse(product.getId(), product.getName(), product.getImageUrl(), product.getPrice()))
                .collect(Collectors.toList());
    }

    public void removeProduct(Long memberId, CartItemRequest cartItemRequest) {
        checkIfExistMember(memberId);
        Product product =  findProduct(cartItemRequest.getProductId());
        cartItemDao.delete(memberId, product.getId());
    }

    private void checkIfExistMember(Long memberId) {
        if(memberDao.findById(memberId).isEmpty()) {
            throw new NotFoundException("해당 회원이 존재하지 않습니다.");
        }
    }

    private Product findProduct(Long productId) {
        Optional<Product> product = productDao.findById(productId);
        if(product.isEmpty()) {
            throw new NotFoundException("해당 상품이 존재하지 않습니다.");
        }
        return product.get();
    }

}
