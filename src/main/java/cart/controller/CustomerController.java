package cart.controller;

import cart.service.CustomerService;
import cart.service.dto.CustomerResponse;
import cart.service.dto.SignUpRequest;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/settings")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/users")
    public ResponseEntity<Void> signUp(@RequestBody SignUpRequest signUpRequest) {
        long savedId = customerService.save(signUpRequest);
        return ResponseEntity.created(URI.create("/settings/users/" + savedId)).build();
    }

    @GetMapping
    public String viewAllCustomers(Model model) {
        List<CustomerResponse> customers = customerService.findAll();
        model.addAttribute("members", customers);
        return "settings";
    }
}
