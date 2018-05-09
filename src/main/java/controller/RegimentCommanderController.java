package controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/regimentCommander")
public class RegimentCommanderController {

    @Autowired
    public RegimentCommanderController(){

    }

    @GetMapping
    @Order(value = 1)
    public String displayMenu(Model model){
        return "regimentCommander";
    }
}
