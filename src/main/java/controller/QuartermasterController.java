package controller;

import converter.RegimentConverter;
import dto.RegimentDto;
import dto.RequirementDto;
import dto.SupplyDto;
import entity.Regiment;
import entity.Supply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import repository.RegimentRepository;
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

    @Autowired
    public QuartermasterController(RegimentService regimentService){
        this.regimentService = regimentService;
    }

    @GetMapping
    @Order(value = 1)
    public String displayMenu(Model model){
        model.addAttribute("requirementDto",new RequirementDto());
        model.addAttribute("supplyDto", new SupplyDto());
        List<RegimentDto> regiments = regimentService.showAll();
        model.addAttribute("regiments",regiments);
        return "quartermaster";
    }

    @PostMapping(value = "/showRegiments/{regimentCode}")
    public String showReg(@ModelAttribute RequirementDto requirementDto,@ModelAttribute SupplyDto supplyDto, Model model,@PathVariable(value = "regimentCode") int regimentCode){
        RegimentDto regimentDto = regimentService.findByCode(regimentCode);
        supplyDto = regimentService.findSupplies(regimentDto.getSupplyId());
        requirementDto = regimentService.findRequirement(regimentDto.getRequirementId());
        model.addAttribute("regimentDto",regimentDto);
        model.addAttribute("supplyDto", supplyDto);
        model.addAttribute("requirementDto", requirementDto);
        return "regimentView";
    }

    @PostMapping(params = "enlistRegiment")
    public String enlistRegiment(Model model, @RequestParam("code") String code, @RequestParam("password") String password){

        Notification<Boolean> notification = regimentService.enlistRegiment(Integer.parseInt(code),password);
        if(notification.hasErrors())
            model.addAttribute("valid",notification.getFormattedErrors());
        else
            model.addAttribute("valid","Successfully enlisted!");
        return "redirect:/quartermaster";
    }

    @PostMapping(params = "sendToWar")
    public String sendToWar(Model model, @RequestParam("sendToWarCode") String sendToWarCode){
        regimentService.removeRegiment(Integer.parseInt(sendToWarCode));
          return "redirect:/quartermaster";
    }



    @PostMapping(params = "setRequirements")
    public String setRequirements(Model model,@ModelAttribute RequirementDto requirementDto, @RequestParam("requirementRegimentCode") String requirementRegimentCode){
        regimentService.changeRequirements(requirementDto,Integer.parseInt(requirementRegimentCode));
        return "redirect:/quartermaster";
    }

    @PostMapping(params = "addSupplies")
    public String addSupplies(Model model, @ModelAttribute SupplyDto supplyDto, @RequestParam("supplyRegimentCode") String supplyRegimentCode){
        regimentService.addMoreSupplies(supplyDto,Integer.parseInt(supplyRegimentCode));
        return "redirect:/quartermaster";
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
