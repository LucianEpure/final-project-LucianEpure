package controller;

import dto.RequestDto;
import dto.ScheduleDto;
import dto.TypeDto;
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
import service.request.RequestService;
import service.type.TypeService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/chiefCommander")
public class ChiefController {


    private RequestService requestService;
    @Autowired
    public ChiefController( RequestService requestService){

    this.requestService = requestService;
    }

    @GetMapping
    @Order(value = 1)
    public String displayMenu(Model model,HttpSession session) {
        RequestDto requestDto;
        if(null == session.getAttribute("requestDto")) {
            requestDto = new RequestDto();
            session.setAttribute("requestDto", requestDto);
        }
        requestDto = (RequestDto) session.getAttribute("requestDto");
        List<TypeDto> types = requestDto.getTypes();
        model.addAttribute("types",types);
        return "chiefCommander";
    }

    @PostMapping(params = "addUnit")
    public String addRequest(@RequestParam("location") String location, @RequestParam("type") String type, HttpSession session){
        RequestDto requestDto = (RequestDto) session.getAttribute("requestDto");
        requestDto.setLocationName(location);
        requestDto = requestService.addUnit(requestDto,type);
        session.setAttribute("requestDto",requestDto);
        return "redirect:/chiefCommander";
    }

    @PostMapping(params = "finishRequest")
    public String finishRequest(HttpSession session, Model model){
        RequestDto requestDto = (RequestDto) session.getAttribute("requestDto");
        requestDto = new RequestDto();
        session.setAttribute("requestDto", requestDto);
            return "redirect:/chiefCommander";

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
