package cart.service;

import cart.dao.CartDao;
import cart.dao.MemberDao;
import cart.domain.Member;
import cart.dto.MemberRequestDto;
import cart.dto.ProductResponseDto;
import cart.dto.entity.CartEntity;
import cart.dto.entity.MemberEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class CartService {
    private static final String NOT_EXIST_MEMBER = "해당하는 사용자가 없습니다.";
    private final CartDao cartDao;

    private final MemberDao memberDao;

    public CartService(CartDao cartDao, MemberDao memberDao) {
        this.cartDao = cartDao;
        this.memberDao = memberDao;
    }

    public List<ProductResponseDto> findAll(MemberRequestDto memberRequestDto) {
        Member member = new Member(memberRequestDto.getEmail(), memberRequestDto.getPassword());
        MemberEntity findMember = memberDao.findByEmail(new MemberEntity(member.getEmail(), member.getPassword()));
        validateExistence(findMember.getId());

        return cartDao.findCartByMember(findMember.getId())
                .stream()
                .map(entity -> new ProductResponseDto(entity.getProductId(),
                        entity.getProductName(),
                        entity.getProductImage(),
                        entity.getProductPrice()))
                .collect(Collectors.toUnmodifiableList());
    }

    @Transactional
    public void save(MemberRequestDto memberRequestDto, Long id) {
        Member member = new Member(memberRequestDto.getEmail(), memberRequestDto.getPassword());
        MemberEntity findMember = memberDao.findByEmail(new MemberEntity(member.getEmail(), member.getPassword()));

        validateExistence(findMember.getId());

        cartDao.save(new CartEntity(id, findMember.getId()));
    }

    @Transactional
    public void delete(MemberRequestDto memberRequestDto, Long id) {
        Member member = new Member(memberRequestDto.getEmail(), memberRequestDto.getPassword());
        MemberEntity findMember = memberDao.findByEmail(new MemberEntity(member.getEmail(), member.getPassword()));

        validateExistence(findMember.getId());

        cartDao.delete(new CartEntity(id, findMember.getId()));
    }

    private void validateExistence(Long id) {
        if (id == null) {
            throw new IllegalArgumentException(NOT_EXIST_MEMBER);
        }
    }
}
