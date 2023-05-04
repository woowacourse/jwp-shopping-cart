package cart.service;

import cart.auth.MemberInfo;
import cart.dto.request.ProductDto;
import cart.excpetion.CartException;
import cart.repository.CartDao;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Component
public class CartService {

    private final CartDao cartDao;

    public CartService(final CartDao cartDao) {
        this.cartDao = cartDao;
    }

    public void addProduct(final MemberInfo memberInfo, final ProductDto productDto) {
        if (cartDao.existingCartItem(memberInfo.getId(), productDto.getProductId())) {
            throw new CartException("이미 등록된 상품은 다시 등록할 수 없습니다.");
        }
        try {
            cartDao.addProduct(memberInfo.getId(), productDto.getProductId());
        } catch (DataIntegrityViolationException e) {
            throw new CartException("존재 하지 않는 상품 혹은 유저의 요청입니다.");
        }
    }

    public void deleteProduct(final MemberInfo memberInfo, final ProductDto productDto) {
        if (!cartDao.existingCartItem(memberInfo.getId(), productDto.getProductId())) {
            throw new CartException("존재하지 않는 항목에 대한 삭제 요청입니다");
        }
        cartDao.deleteProduct(memberInfo.getId(), productDto.getProductId());
    }

}
