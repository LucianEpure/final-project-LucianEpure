package controller;

import dto.RegimentDto;
import dto.RequestDto;
import dto.RequirementDto;
import dto.SupplyDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import service.DateTime;
import service.regiment.RegimentService;
import service.regiment.RequirementService;
import service.request.RequestService;
import service.schedule.ScheduleService;
import service.regiment.SupplyService;
import validators.Notification;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/quartermaster")
public class QuartermasterController {

    private RegimentService regimentService;
    private SupplyService supplyService;
    private RequirementService requirementService;
    private RequestService requestService;
    private DateTime dateTime;

    @Autowired
    public QuartermasterController(DateTime dateTime, RegimentService regimentService,RequestService requestService, SupplyService supplyService, RequirementService requirementService){
        this.regimentService = regimentService;
        this.supplyService = supplyService;
        this.requirementService = requirementService;
        this.requestService = requestService;
        this.dateTime = dateTime;
    }

    @GetMapping
    @Order(value = 1)
    public String displayMenu(Model model,HttpSession session){
        String valid = (String) session.getAttribute("valid");
        model.addAttribute("requirementDto",new RequirementDto());
        model.addAttribute("supplyDto", new SupplyDto());
        List<RegimentDto> regiments = regimentService.showAll();
        List<RequestDto> requests = requestService.showAll();
        model.addAttribute("valid",valid);
        model.addAttribute("regiments",regiments);
        model.addAttribute("requests",requests);
        return "quartermaster";
    }

    @PostMapping(value = "/showRegiments/{regimentCode}")
    public String showReg(@ModelAttribute RequirementDto requirementDto,@ModelAttribute SupplyDto supplyDto, Model model,@PathVariable(value = "regimentCode") int regimentCode){
        RegimentDto regimentDto = regimentService.findByCode(regimentCode);
        supplyDto = supplyService.findSupplies(regimentDto.getSupplyId());
        requirementDto = requirementService.findRequirement(regimentDto.getRequirementId());
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
    public String sendToWar(@RequestParam("sendToWarCode") String sendToWarCode,@RequestParam("sendToWarLocation") String sendToWarLocation,HttpSession session){
            RequestDto requestDto = requestService.findByLocation(sendToWarLocation);
            Notification<Boolean> sendToWar = regimentService.sendRegimentToWar(Integer.parseInt(sendToWarCode),requestDto);
          if(sendToWar.hasErrors())
          session.setAttribute("valid",sendToWar.getFormattedErrors());
          return "redirect:/quartermaster";
    }



    @PostMapping(params = "setRequirements")
    public String setRequirements(@ModelAttribute RequirementDto requirementDto, @RequestParam("requirementRegimentCode") String requirementRegimentCode){
        requirementService.changeRequirements(requirementDto,Integer.parseInt(requirementRegimentCode));
        return "redirect:/quartermaster";
    }

    @PostMapping(params = "addSupplies")
    public String addSupplies(@ModelAttribute SupplyDto supplyDto, @RequestParam("supplyRegimentCode") String supplyRegimentCode){
        supplyService.addMoreSupplies(supplyDto,Integer.parseInt(supplyRegimentCode));
        return "redirect:/quartermaster";
    }

    @PostMapping(value = "/showSchedules", params = "showSchedules")
    public String showSchedules(@RequestParam("scheduleDate") String date, HttpSession session){
        Date parsedDate = dateTime.formatter(date);
        session.setAttribute("scheduleDate",parsedDate);
        return "redirect:/quartermaster/showSchedules";
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
