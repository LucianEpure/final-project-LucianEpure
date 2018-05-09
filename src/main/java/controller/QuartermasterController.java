package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/quartermaster")
public class QuartermasterController {

    @Autowired
    public QuartermasterController(){

    }

    @GetMapping
    @Order(value = 1)
    public String displayMenu(Model model){
        return "quartermaster";
    }
}
