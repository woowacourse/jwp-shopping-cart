package cart.controller;

import cart.dtomapper.CustomerResponseDtoMapper;
import cart.entity.customer.CustomerEntity;
import cart.service.CustomerService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public final class SettingsController {

    private final CustomerService customerService;

    public SettingsController(final CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(path = "/settings")
    public ModelAndView settingPage(ModelAndView modelAndView) {
        final List<CustomerEntity> customers = customerService.findAll();
        modelAndView.addObject("members", CustomerResponseDtoMapper.asList(customers));
        modelAndView.setViewName("settings");
        return modelAndView;
    }
}
