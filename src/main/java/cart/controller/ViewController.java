package cart.controller;

import cart.dao.entity.MemberEntity;
import cart.dto.response.ResponseMemberDto;
import cart.dto.response.ResponseProductDto;
import cart.repository.ProductDto;
import cart.service.MemberService;
import cart.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ViewController {

    private final MemberService memberService;
    private final ProductService productService;

    @Autowired
    public ViewController(MemberService memberService, ProductService productService) {
        this.memberService = memberService;
        this.productService = productService;
    }

    @GetMapping("/")
    public String readProducts(final Model model) {
        final List<ResponseProductDto> responseProductDtos = getResponseProductDtos();
        model.addAttribute("products", responseProductDtos);
        return "index";
    }

    @GetMapping("/cart")
    public String readCarts() {
        return "cart";
    }

    @GetMapping("/admin")
    public String getAdminProducts(final Model model) {
        final List<ResponseProductDto> responseProductDtos = getResponseProductDtos();
        model.addAttribute("products", responseProductDtos);
        return "admin";
    }

    @GetMapping("/settings")
    public String getMembers(final Model model) {
        final List<MemberEntity> memberEntities = memberService.findAll();
        final List<ResponseMemberDto> responseMemberDtos = memberEntities.stream()
                .map(ResponseMemberDto::transferEntityToDto)
                .collect(Collectors.toList());
        model.addAttribute("members", responseMemberDtos);
        return "settings";
    }

    private List<ResponseProductDto> getResponseProductDtos() {
        final List<ProductDto> productDtos = productService.findAll();
        return transferProductDtoToResponseDto(productDtos);
    }

    private List<ResponseProductDto> transferProductDtoToResponseDto(final List<ProductDto> productDtos) {
        return productDtos.stream()
                .map(productDto -> new ResponseProductDto(
                        productDto.getId(),
                        productDto.getName(),
                        productDto.getPrice(),
                        productDto.getImage()
                )).collect(Collectors.toList());
    }
}
