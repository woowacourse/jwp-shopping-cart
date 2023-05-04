package cart.service;

import cart.authorization.AuthorizationInformation;
import cart.dao.CartDao;
import cart.dao.ItemDao;
import cart.dao.MemberDao;
import cart.dto.ItemResponse;
import cart.entity.*;
import cart.exception.ServiceIllegalArgumentException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final CartDao cartDao;
    private final MemberDao memberDao;
    private final ItemDao itemDao;

    public CartService(CartDao cartDao, MemberDao memberDao, ItemDao itemDao) {
        this.cartDao = cartDao;
        this.memberDao = memberDao;
        this.itemDao = itemDao;
    }

    public void putItemIntoCart(Long itemId, AuthorizationInformation authorizationInformation) {
        AuthMember authMember = convertAuthInformationToMember(authorizationInformation);

        validatePutCart(itemId, authMember);

        Member member = memberDao.findByAuthMember(authMember);
        PutCart putCart = new PutCart(member.getId(), itemId);

        cartDao.save(putCart);
    }


    private AuthMember convertAuthInformationToMember(AuthorizationInformation authorizationInformation) {
        return new AuthMember(authorizationInformation.getEmail(), authorizationInformation.getPassword());
    }

    private void validatePutCart(Long itemId, AuthMember authMember) {
        if (!memberDao.isMemberExists(authMember)) {
            throw new ServiceIllegalArgumentException("email과 password를 다시 입력해주세요.");
        }
        if (!itemDao.isItemExists(itemId)) {
            throw new ServiceIllegalArgumentException("item을 다시 선택해주세요.");
        }
    }

    public List<ItemResponse> findAllItemByAuthInfo(AuthorizationInformation authorizationInformation) {
        AuthMember authMember = convertAuthInformationToMember(authorizationInformation);

        Member member = memberDao.findByAuthMember(authMember);
        List<Cart> carts = cartDao.findAllByMemberId(member.getId());
        return carts.stream()
                .map(cart -> itemDao.findById(cart.getItemId()))
                .map(item -> new ItemResponse(item.getId(), item.getName(), item.getImageUrl(), item.getPrice()))
                .collect(Collectors.toList());
    }
}
