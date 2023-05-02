package cart.controller.view;

import cart.dtomapper.CustomerResponseDtoMapper;
import cart.entity.customer.CustomerEntity;
import cart.service.CustomerService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/settings")
public final class SettingsViewController {

    private final CustomerService customerService;

    public SettingsViewController(final CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ModelAndView settingPage(ModelAndView modelAndView) {
        final List<CustomerEntity> customers = customerService.findAll();
        modelAndView.addObject("members", CustomerResponseDtoMapper.asList(customers));
        modelAndView.setViewName("settings");
        return modelAndView;
    }
}
