package cart.service;

import cart.dao.CartDao;
import cart.dao.MemberDao;
import cart.dao.ProductDao;
import cart.dto.entity.CartEntity;
import cart.dto.entity.ProductEntity;
import cart.dto.response.CartResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    private final CartDao cartDao;
    private final MemberDao memberDao;
    private final ProductDao productDao;

    public CartService(CartDao cartDao, MemberDao memberDao, ProductDao productDao) {
        this.cartDao = cartDao;
        this.memberDao = memberDao;
        this.productDao = productDao;
    }

    public List<CartResponse> findAllByEmail(String email) {
        int memberId = memberDao.findByEmail(email);
        List<CartEntity> carts = cartDao.findByMemberId(memberId);
        return carts.stream()
                .map(cartEntity -> {
                    ProductEntity product = productDao.findById(cartEntity.getProduct_id());
                    return new CartResponse(
                            cartEntity.getId(),
                            product.getName(),
                            product.getImgUrl(),
                            product.getPrice());
                })
                .collect(Collectors.toList());
    }
}
