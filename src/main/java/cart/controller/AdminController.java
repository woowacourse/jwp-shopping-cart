package cart.controller;

import cart.mapper.ProductResponseMapper;
import cart.service.ProductManagementService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ProductManagementService managementService;

    public AdminController(final ProductManagementService managementService) {
        this.managementService = managementService;
    }

    @GetMapping
    public ModelAndView admin(ModelAndView modelAndView) {
        modelAndView.addObject("products", ProductResponseMapper.from(managementService.findAll()));
        modelAndView.setViewName("admin");
        return modelAndView;
    }

}
