package controller;

import dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import service.RegimentService;
import service.ScheduleReportService;
import service.ScheduleService;
import service.SupplyService;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

import static application.Constants.APPROVED;
import static application.Constants.DENIED;

@Controller
@RequestMapping(value = "/quartermaster/showSchedules")
public class ShowSchedulesController {


    ScheduleService scheduleService;
    ScheduleReportService scheduleReportService;
    RegimentService regimentService;
    SupplyService supplyService;

    @Autowired
    public ShowSchedulesController(ScheduleService scheduleService, ScheduleReportService scheduleReportService,SupplyService supplyService, RegimentService regimentService){
    this.scheduleService = scheduleService;
    this.scheduleReportService = scheduleReportService;
    this.regimentService =regimentService;
    this.supplyService = supplyService;
    }

    @GetMapping
    @Order(value = 1)
    public String displayMenu(Model model,HttpSession session){
        Date date = (Date) session.getAttribute("scheduleDate");
        List<ScheduleDto> scheduleDtos = scheduleService.findByDate(date);
        model.addAttribute("schedules", scheduleDtos);
        return "scheduleView";
    }

    @PostMapping(value = "/{scheduleId}",params = "approve")
    public String approve(@PathVariable(value = "scheduleId") int scheduleId,Model model) {


        ScheduleDto scheduleDto = scheduleService.findById(scheduleId);
        ScheduleReportDto scheduleReport = new ScheduleReportDto();
        scheduleReport.init(regimentService.findByCode(scheduleDto.getRegimentCode()),supplyService.findSupplies(regimentService.findByCode(scheduleDto.getRegimentCode()).getSupplyId()));
        scheduleDto.setApproved(APPROVED);
        for(ActivityDto activityDto:scheduleDto.getActivities()){
            scheduleReportService.updateStats(scheduleReport,activityDto,"add");
            System.out.println("DURATIOOOOOOOOOOOOOOOOOOON"+scheduleReport.getDuration());
        }
        regimentService.update(scheduleReportService.retrieveRegiment(scheduleReport),scheduleReportService.retrieveSupply(scheduleReport));
        scheduleService.update(scheduleDto);

        return "redirect:/quartermaster/showSchedules";
    }
    @PostMapping(value = "/{scheduleId}", params = "deny")
    public String deny(@PathVariable(value = "scheduleId") int scheduleId,Model model){
        ScheduleDto scheduleDto = scheduleService.findById(scheduleId);
        scheduleDto.setApproved(DENIED);
        scheduleService.update(scheduleDto);
        return "redirect:/quartermaster/showSchedules";
    }
}
