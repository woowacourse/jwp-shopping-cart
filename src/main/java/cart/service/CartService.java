package cart.service;

import cart.dao.CartDao;
import cart.dao.ItemDao;
import cart.dao.MemberDao;
import cart.dto.AuthorizationInformation;
import cart.dto.ItemResponse;
import cart.entity.*;
import cart.exception.ServiceIllegalArgumentException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

    private static final int ZERO_AFFECTED_ROW = 0;
    private static final String INVALID_ITEM_ID_MESSAGE = "상품 정보를 다시 입력해주세요.";
    private static final String INVALID_MEMBER_MESSAGE = "email과 password를 다시 입력해주세요.";

    private final CartDao cartDao;
    private final MemberDao memberDao;
    private final ItemDao itemDao;

    public CartService(CartDao cartDao, MemberDao memberDao, ItemDao itemDao) {
        this.cartDao = cartDao;
        this.memberDao = memberDao;
        this.itemDao = itemDao;
    }

    public void putItemIntoCart(Long itemId, AuthorizationInformation authorizationInformation) {
        AuthMember authMember = authorizationInformation.toAuthMember();

        validatePutCart(itemId, authMember);

        Member member = memberDao.findByAuthMember(authMember);
        PutCart putCart = new PutCart(member.getId(), itemId);

        cartDao.save(putCart);
    }

    private void validatePutCart(Long itemId, AuthMember authMember) {
        if (!memberDao.isMemberExists(authMember)) {
            throw new ServiceIllegalArgumentException(INVALID_MEMBER_MESSAGE);
        }
        if (!itemDao.isItemExistsById(itemId)) {
            throw new ServiceIllegalArgumentException(INVALID_ITEM_ID_MESSAGE);
        }
    }

    public List<ItemResponse> findAllItemByAuthInfo(AuthorizationInformation authorizationInformation) {
        AuthMember authMember = authorizationInformation.toAuthMember();

        Member member = memberDao.findByAuthMember(authMember);
        List<Cart> carts = cartDao.findAllByMemberId(member.getId());
        return carts.stream()
                .map(cart -> itemDao.findById(cart.getItemId()))
                .map(Item::toItemResponse)
                .collect(Collectors.toList());
    }

    public void deleteItemFromCart(Long itemId, AuthorizationInformation authorizationInformation) {
        AuthMember authMember = authorizationInformation.toAuthMember();
        Member member = memberDao.findByAuthMember(authMember);
        PutCart putCart = new PutCart(member.getId(), itemId);

        int deleteRow = cartDao.delete(putCart);
        validateItemId(deleteRow);
    }


    private void validateItemId(int changedRow) {
        if (isInvalidItemId(changedRow)) {
            throw new ServiceIllegalArgumentException(INVALID_ITEM_ID_MESSAGE);
        }
    }

    private boolean isInvalidItemId(int changedRow) {
        return changedRow == ZERO_AFFECTED_ROW;
    }
}
