package pl.sda.training.management.app.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("admin")
public class AdminPanelController {

    @GetMapping
    public ModelAndView getAdminPanel(){
        return new ModelAndView("admin/index");
    }
}
