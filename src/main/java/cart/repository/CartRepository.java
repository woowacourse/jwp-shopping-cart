package cart.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import cart.dao.CartItemDao;
import cart.dao.ProductDao;
import cart.dao.dto.CartItemDto;
import cart.dao.dto.ProductDto;
import cart.domain.Cart;
import cart.domain.Product;
import cart.domain.User;

@Repository
public class CartRepository {

    private final CartItemDao cartItemDao;
    private final ProductDao productDao;

    public CartRepository(CartItemDao cartItemDao, ProductDao productDao) {
        this.cartItemDao = cartItemDao;
        this.productDao = productDao;
    }

    public void save(User user, Cart cart) {
        cartItemDao.deleteAll(user.getId());
        for (Product product : cart.getProducts()) {
            cartItemDao.insert(user.getId(), product.getId());
        }
    }

    // 기존 카트와 비교해 삭제할 항목, 추가할 항목, 업데이트할 항목을 가져온다.
    // 일단 어려우니 전체 삭제 후 저장.
    // public void update(User user, Cart cart) {
    //     List<Product> savedProducts = getCartOf(user).getProducts();
    //     List<Product> productsToUpdate = cart.getProducts();
    //
    //     List<Product> productsToDelete = getChanges(productsToUpdate, savedProducts)
    //     List<Product> productsToInsert = new ArrayList<>();
    // }

    // private List<Product> getChanges(List<Product> original, List<Product> changedProducts) {
    //     return changedProducts.stream()
    //             .filter(product -> !original.contains(product))
    //             .collect(Collectors.toList());
    // }

    public Cart getCartOf(final User user) {
        List<CartItemDto> cartItems = cartItemDao.selectAllItemsOf(user.getId());

        List<Product> products = cartItems.stream()
                .map(cartItem -> getProductBy(cartItem.getProductId())) // 카트 한번 조회 시, 하위 도메인 개수만큼 추가 쿼리가 나간다.
                .collect(Collectors.toList());

        return new Cart(products);
    }

    private Product getProductBy(Integer productId) {
        ProductDto productDto = productDao.select(productId);
        return new Product(
                productDto.getId(),
                productDto.getName(),
                productDto.getImage(),
                productDto.getPrice()
        );
    }
}
