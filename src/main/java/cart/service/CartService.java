package cart.service;

import cart.auth.MemberInfo;
import cart.dao.CartDao;
import cart.dao.joinrequest.CartWithProduct;
import cart.dto.request.ProductRequestDto;
import cart.dto.response.ProductDto;
import cart.excpetion.CartException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CartService {

    private final CartDao cartDao;

    public CartService(final CartDao cartDao) {
        this.cartDao = cartDao;
    }

    public void addProduct(final MemberInfo memberInfo, final ProductRequestDto productRequestDto) {
        if (cartDao.existingCartItem(memberInfo.getId(), productRequestDto.getProductId())) {
            throw new CartException("이미 등록된 상품은 다시 등록할 수 없습니다.");
        }
        try {
            cartDao.addProduct(memberInfo.getId(), productRequestDto.getProductId());
        } catch (DataIntegrityViolationException e) {
            throw new CartException("존재 하지 않는 상품 혹은 유저의 요청입니다.");
        }
    }

    public void deleteProduct(final MemberInfo memberInfo, final Integer productId) {
        if (!cartDao.existingCartItem(memberInfo.getId(), productId)) {
            throw new CartException("존재하지 않는 항목에 대한 삭제 요청입니다");
        }
        cartDao.deleteProduct(memberInfo.getId(), productId);
    }

    public List<ProductDto> getProductsOf(MemberInfo memberInfo) {
        final List<CartWithProduct> cartWithProducts = cartDao.cartWithProducts(memberInfo.getId());
        final ArrayList<ProductDto> productDtos = new ArrayList<>();
        for (CartWithProduct cartWithProduct : cartWithProducts) {
            productDtos.add(new ProductDto(
                            cartWithProduct.getId(),
                            cartWithProduct.getName(),
                            cartWithProduct.getImage(),
                            cartWithProduct.getPrice()
                    )
            );
        }
        return productDtos;
    }
}
