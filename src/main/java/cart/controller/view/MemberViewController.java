package cart.controller.view;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import cart.service.MemberService;
import cart.service.response.MemberResponse;

@Controller
public class MemberViewController {
	private final MemberService memberService;

	public MemberViewController(final MemberService memberService) {
		this.memberService = memberService;
	}

	@GetMapping("settings")
	public String showMembers(Model model){
		final List<MemberResponse> findAllMembers = memberService.findAll();
		model.addAttribute("members", findAllMembers);
		return "settings";
	}
}
