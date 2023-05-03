package cart.business;

import cart.entity.Member;
import cart.entity.Product;
import cart.persistence.MemberDao;
import cart.persistence.ProductDao;
import cart.presentation.dto.MemberRequest;
import cart.presentation.dto.MemberResponse;
import cart.presentation.dto.ProductRequest;
import cart.presentation.dto.ProductResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartService {

    private ProductDao productDao;
    private MemberDao memberDao;

    public CartService(ProductDao productDao, MemberDao memberDao) {
        this.productDao = productDao;
        this.memberDao = memberDao;
    }

    @Transactional
    public Integer createProduct(ProductRequest request) {
        Product product = makeProductFromRequest(request);
        productDao.findSameProductExist(product);

        return productDao.insert(product);
    }

    @Transactional(readOnly = true)
    public List<Product> readProduct() {
        return productDao.findAll();
    }

    @Transactional
    public Integer updateProduct(Integer id, ProductRequest request) {
        Product product = makeProductFromRequest(request);
        return productDao.update(id, product);
    }

    @Transactional
    public Integer deleteProduct(Integer id) {
        return productDao.remove(id);
    }

    private Product makeProductFromRequest(ProductRequest request) {
        return new Product(null, request.getName(), request.getUrl(), request.getPrice());
    }

    private Product makeProductFromResponse(ProductResponse response) {
        return new Product(
                response.getId(), (response.getName()), response.getUrl(), response.getPrice());
    }
    
    @Transactional
    public Integer createMember(MemberRequest request) {
        Member member = makeMemberFromRequest(request);
        memberDao.findSameProductExist(member);

        return memberDao.insert(member);
    }

    @Transactional(readOnly = true)
    public List<Member> readMembers() {
        return memberDao.findAll();
    }

    @Transactional
    public Integer deleteMember(Integer id) {
        return memberDao.remove(id);
    }


    private Member makeMemberFromRequest(MemberRequest request) {
        return new Member(null, request.getEmail(), request.getPassword());
    }

    private Member makeMemberFromResponse(MemberResponse response) {
        return new Member(response.getId(), response.getEmail(), response.getPassword());
    }
}
