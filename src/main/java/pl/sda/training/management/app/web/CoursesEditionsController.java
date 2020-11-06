package pl.sda.training.management.app.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.training.management.app.utils.Counter;

@Controller
@RequiredArgsConstructor
@RequestMapping("admin/courses-editions")
public class CoursesEditionsController {
    private final CourseWebService courseWebService;
    private final TrainerWebService trainerWebService;
    private final CourseEditionWebService courseEditionWebService;

    @GetMapping("/new")
    public ModelAndView getNewCourseEdition() {
        return new ModelAndView("admin/course-edition-new",
                "coursesToChoose", courseWebService.getAllCoursesToChoose());
    }

    @PostMapping("/new")
    public String postId(Long chosenCourseId, Model model) {
        if (chosenCourseId == null) {
            return "redirect:/admin/course-wizard";
        }

        CourseDTO courseDTO = courseWebService.getCourseById(chosenCourseId);
        model.addAttribute("course", courseDTO);
        model.addAttribute("trainers", trainerWebService.getTrainersToShow());
        model.addAttribute("courseEdition", new CourseEditionDTO(courseDTO));
        model.addAttribute("counter", new Counter());

        return "admin/course-edition-new-dates";
    }

    @PostMapping("/save")
    public String saveCourseEdition(CourseEditionDTO courseEditionDTO, Model model) {
        courseEditionWebService.save(courseEditionDTO);

        return "redirect:/admin/courses-editions";
    }
}
