package cart.service;

import cart.auth.AuthInfo;
import cart.dao.CartDao;
import cart.dto.entity.CartEntity;
import cart.dto.entity.ProductEntity;
import cart.dto.response.CartResponse;
import cart.exception.ErrorCode;
import cart.exception.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    public static final int ZERO = 0;

    private final CartDao cartDao;
    private final ProductService productService;
    private final MemberService memberService;

    public CartService(CartDao cartDao, ProductService productService, MemberService memberService) {
        this.cartDao = cartDao;
        this.productService = productService;
        this.memberService = memberService;
    }

    public List<CartResponse> findAllByEmailWithPassword(AuthInfo authInfo) {
        int memberId = memberService.findByEmailWithPassword(authInfo.getEmail(), authInfo.getPassword());
        List<CartEntity> carts = cartDao.findByMemberId(memberId);

        return carts.stream()
                .map(cartEntity -> {
                    ProductEntity product = productService.findById(cartEntity.getProduct_id());
                    return new CartResponse(
                            cartEntity.getId(),
                            product.getName(),
                            product.getImgUrl(),
                            product.getPrice(),
                            cartEntity.getCount()
                    );
                })
                .collect(Collectors.toList());
    }

    public int save(AuthInfo authInfo, int productId) {
        List<CartEntity> cartByProduct = cartDao.findByProductId(productId);

        if (cartByProduct.size() == ZERO) {
            int memberId = memberService.findByEmailWithPassword(authInfo.getEmail(), authInfo.getPassword());
            return cartDao.save(new CartEntity(memberId, productId));
        }

        updateCount(productId, cartByProduct.get(0).getCount() + 1);
        return cartByProduct.get(0).getId();
    }

    private void updateCount(int productId, int productCount) {
        int updateRowNumber = cartDao.updateCount(productCount, productId);
        if (updateRowNumber == ZERO) {
            throw new NotFoundException(ErrorCode.ID_NOT_FOUND);
        }
    }

    public void delete(int id) {
        int deleteRowNumber = cartDao.delete(id);
        if (deleteRowNumber == ZERO) {
            throw new NotFoundException(ErrorCode.ID_NOT_FOUND);
        }
    }
}
