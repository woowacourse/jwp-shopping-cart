package cart.controller;

import cart.mapper.ProductResponseMapper;
import cart.service.ProductManagementService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    private final ProductManagementService managementService;

    public HomeController(final ProductManagementService managementService) {
        this.managementService = managementService;
    }

    @GetMapping("/")
    public ModelAndView home(ModelAndView modelAndView) {
        modelAndView.addObject("products", ProductResponseMapper.from(managementService.findAll()));
        modelAndView.setViewName("index");
        return modelAndView;
    }
}
