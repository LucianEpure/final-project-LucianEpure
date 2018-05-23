package controller;


import dto.RegimentDto;
import dto.ScheduleDto;
import dto.SupplyDto;
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
import service.regiment.RegimentService;
import service.regiment.SupplyService;
import service.schedule.ScheduleCRUDService;
import service.schedule.ScheduleService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping(value = "/regimentCommander")
public class RegimentCommanderController {

    private ScheduleService scheduleService;
    private ScheduleCRUDService scheduleCRUDService;
    private RegimentService regimentService;
    private SupplyService supplyService;

    @Autowired
    public RegimentCommanderController(SupplyService supplyService, RegimentService regimentService ,ScheduleCRUDService scheduleCRUDService,ScheduleService scheduleService){
         this.scheduleService = scheduleService;
         this.scheduleCRUDService = scheduleCRUDService;
         this.regimentService = regimentService;
         this.supplyService = supplyService;
    }

    @GetMapping
    @Order(value = 1)
    public String displayMenu(Model model,Principal principal){
        String username = principal.getName();
        String regimentCode = username.replaceAll("[^0-9]","");
        RegimentDto regimentDto = regimentService.findByCode(Integer.parseInt(regimentCode));
        SupplyDto supplyDto = supplyService.findSupplies(regimentDto.getSupplyId());
        model.addAttribute("regimentDto",regimentDto);
        model.addAttribute("supplyDto",supplyDto);
        return "regimentCommander";
    }

    @PostMapping(value = "/schedule", params = "schedule")
        public String schedule(Principal principal, HttpSession session){
        boolean approved;
        ScheduleDto scheduleDto = new ScheduleDto();
        try {
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date today = new Date();
            Date todayWithZeroTime = formatter.parse(formatter.format(today));
            scheduleDto.setDate(todayWithZeroTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String username = principal.getName();
        String regimentCode = username.replaceAll("[^0-9]","");
        scheduleDto.setRegimentCode(Integer.parseInt(regimentCode));
        approved = scheduleService.checkIfApproved(scheduleDto);
        if(approved)
            return "scheduleError";
        else{
            scheduleDto = scheduleCRUDService.save(scheduleDto);
            session.setAttribute("scheduleDto",scheduleDto);
            return "redirect:/regimentCommander/schedule";
        }
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
