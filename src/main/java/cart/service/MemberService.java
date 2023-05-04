package cart.service;

import cart.controller.dto.MemberResponse;
import cart.controller.dto.ProductResponse;
import cart.dao.MemberDao;
import cart.domain.Member;
import cart.domain.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberService {

    private final MemberDao memberDao;

    public MemberService(final MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public List<MemberResponse> findAll() {
        List<Member> members = memberDao.findAll();
        return members.stream()
                .map(member -> new MemberResponse(member.getEmail(), member.getPassword()))
                .collect(Collectors.toList());
    }

    public List<ProductResponse> findProductByEmail(final String email) {
        List<Product> productByEmail = memberDao.findProductByEmail(email);
        return productByEmail.stream().map(ProductResponse::from).collect(Collectors.toList());
    }
}
