package controller;

import converter.RegimentConverter;
import dto.RegimentDto;
import entity.Regiment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import service.RegimentService;
import validators.Notification;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/quartermaster")
public class QuartermasterController {

    private RegimentService regimentService;
    private RegimentConverter regimentConverter;
    @Autowired
    public QuartermasterController(RegimentService regimentService, RegimentConverter regimentConverter){
        this.regimentService = regimentService;
        this.regimentConverter = regimentConverter;
    }

    @GetMapping
    @Order(value = 1)
    public String displayMenu(Model model){
        return "quartermaster";
    }

    @PostMapping(params = "enlistRegiment")
    public String enlistRegiment(Model model, @RequestParam("code") String code, @RequestParam("password") String password){
        Notification<Boolean> notification = regimentService.enlistRegiment(Integer.parseInt(code),password);
        if(notification.hasErrors())
            model.addAttribute("valid",notification.getFormattedErrors());
        else
            model.addAttribute("valid","Successfully enlisted!");
        return "quartermaster";
    }

    @PostMapping(value = "/regiments", params = "showRegiments")
    public String showRegiments(Model model){
        List<Regiment> regiments = regimentService.showAll();
        List<RegimentDto> regimentDtos = new ArrayList<RegimentDto>();
        for(Regiment regiment:regiments){
            regimentDtos.add(regimentConverter.convertRegimentToDto(regiment));
        }
        model.addAttribute("regiments",regimentDtos);
        return "showRegiments";
    }

    @PostMapping(params="logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login";
    }
}
