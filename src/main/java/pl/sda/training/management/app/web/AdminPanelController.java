package pl.sda.training.management.app.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminPanelController {
    private final StudentSubmissionWebService submissionWebService;

    @GetMapping
    public String getAdminPanel() {

        return "redirect:admin/accept-submissions";
    }

    @GetMapping("/course-creator")
    public ModelAndView getCourseCreator(){

        return new ModelAndView("admin/course-creator");
    }
    @GetMapping("/accept-submissions")
    public ModelAndView acceptSubmissions(){

        return new ModelAndView("admin/accept-submissions",
                "studentSubmissions", submissionWebService.getSubmissions());
    }
}
