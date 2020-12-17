package pl.sda.training.management.app.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("admin")
@RequiredArgsConstructor
@Slf4j
public class AdminPanelController {
    private final StudentSubmissionWebService submissionWebService;
    private final StudentWebService studentWebService;

    @GetMapping
    public String getAdminPanel() {

        return "redirect:admin/accept-submissions";
    }


    @GetMapping("/accept-submissions")
    public ModelAndView acceptSubmissions() {

        return new ModelAndView("admin/accept-submissions",
                "studentSubmissions", submissionWebService.getSubmissions());
    }

    @GetMapping("/students")
    public ModelAndView getStudents() {
        return new ModelAndView("admin/students",
                "students", studentWebService.getAll());
    }

    @PostMapping("/students")
    public ModelAndView menageStudent(@RequestParam(name = "student", required = false) String login) {
        if (login == null) {
            return new ModelAndView("redirect:students");
        }
        return new ModelAndView("admin/student",
                "student", studentWebService.getStudentToShow(login));
    }
}
