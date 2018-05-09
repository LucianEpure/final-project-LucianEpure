package controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import dto.UserDto;



@Controller
@RequestMapping("/login")
public class LoginController {

	@Autowired
	public LoginController (){
	}
	
	@GetMapping()
	@Order(value = 1)
    public String displayMenu(Model model) {
		model.addAttribute(new UserDto());
        return "login";
	}
	
	@PostMapping(params="login")
    public String login(HttpServletRequest request, HttpServletResponse response,@ModelAttribute UserDto user,
                        BindingResult result) throws ServletException {
        try {
        	RequestCache requestCache = new HttpSessionRequestCache();
            request.login(user.getUsername(),user.getPassword());
            SavedRequest savedRequest = requestCache.getRequest(request, response);
            if (savedRequest != null) {
                return "redirect:" + savedRequest.getRedirectUrl();
            } else {
                return "redirect:/";
            }
        } catch (ServletException authenticationFailed) {
            result.rejectValue(null, "authentication.failed");
            return "login";
        }
	}
}
