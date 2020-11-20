package pl.sda.training.management.app.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
@RequestMapping("trainer")
@RequiredArgsConstructor
@Slf4j
public class TrainerPanelController {
    private final CourseEditionWebService courseEditionWebService;

    @GetMapping
    public String getTrainerPanel() {

        return "redirect:trainer/courses";
    }

    @GetMapping("/courses")
    public ModelAndView getTrainerCourses(Principal principal) {
        return new ModelAndView("trainer/courses",
                "coursesEditions", courseEditionWebService
                .getAllEditionsToChooseByTrainerLogin(principal.getName()));
    }
}
