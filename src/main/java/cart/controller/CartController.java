package cart.controller;

import cart.controller.dto.ProductResponse;
import cart.dao.CartDao;
import cart.dao.MemberDao;
import cart.dao.entity.ProductEntity;
import cart.domain.Member;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartDao mySQLCartDao;
    private final MemberDao mySQLMemberDao;

    public CartController(CartDao mySQLCartDao, MemberDao mySQLMemberDao) {
        this.mySQLCartDao = mySQLCartDao;
        this.mySQLMemberDao = mySQLMemberDao;
    }


    @GetMapping("/products")
    public ResponseEntity<List<ProductResponse>> getProducts(HttpServletRequest request) {
        final List<ProductEntity> productEntities = mySQLCartDao.findByMeber(
            extractMember(request));
        List<ProductResponse> cart = ProductResponse.from(productEntities);

        return ResponseEntity.ok().body(cart);
    }

    @PostMapping("/{product_id}")
    public long create(@PathVariable("product_id") Long productId,
        HttpServletRequest request) {
        Member member = extractMember(request);
        Long memberId = mySQLMemberDao.findIdByMember(member);

        return mySQLCartDao.add(memberId, productId);
    }

    @DeleteMapping("/{product_id}")
    public int remove(@PathVariable("product_id") Long productId, HttpServletRequest request) {
        Member member = extractMember(request);
        Long memberId = mySQLMemberDao.findIdByMember(member);
        return mySQLCartDao.deleteById(memberId, productId);
    }

    public Member extractMember(HttpServletRequest request) {
        String credentials = request.getHeader("authorization").substring("Basic ".length());
        String[] decoded = new String(Base64Utils.decode(credentials.getBytes())).split(":");
        if (decoded.length != 2) {
            throw new IllegalArgumentException();
        }
        return new Member(decoded[0], decoded[1]);
    }
}
