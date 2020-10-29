package pl.sda.training.management.app.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping("admin/trainers")
public class TrainersController {
    public final TrainerWebService trainerWebService;
    public final UserWebService userWebService;

    @GetMapping
    public ModelAndView getTrainers() {
        return new ModelAndView("admin/trainers",
                "trainers", trainerWebService.getTrainersToShow());
    }

    @GetMapping("/grant-role")
    public String getGrantRoleView(Model model) {
        model.addAttribute("users", userWebService.getUsersToShow());
        model.addAttribute("loginsToGrant", new LoginsToProcess());
        return "admin/trainers-grant-role";
    }

    @PostMapping("/grant-role")
    public String postLoginsToGrant(LoginsToProcess loginsToGrant) {
        loginsToGrant.getLoginsToProcess().forEach(trainerWebService::setAsTrainerByLogin);
        return "redirect:/admin/trainers/grant-role";
    }
}
