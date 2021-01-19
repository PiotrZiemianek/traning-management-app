package pl.sda.training.management.app.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("admin")
@RequiredArgsConstructor
@Slf4j
public class AdminPanelController {
    private final StudentSubmissionWebService submissionWebService;
    private final StudentWebService studentWebService;
    private final CourseEditionWebService courseEditionWebService;

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
    public String menageStudent(@RequestParam(name = "student", required = false) String login, Model model) {
        if (login == null) {
            return "redirect:students";
        }

        model.addAttribute("student", studentWebService.getStudentToShow(login));
        model.addAttribute("coursesToChoose", courseEditionWebService.getAllCoursesEditionsToChoose());

        return "admin/student";
    }

    @PostMapping("/students/{studentLogin}")
    public String deleteStudentFromCourseEdition(@PathVariable String studentLogin,
                                                 @RequestParam(name = "deleteFromEdition") String editionCode,
                                                 Model model) {
        courseEditionWebService.deleteStudentFromEdition(studentLogin, editionCode);

        model.addAttribute("student", studentWebService.getStudentToShow(studentLogin));
        model.addAttribute("coursesToChoose", courseEditionWebService.getAllCoursesEditionsToChoose());

        return "admin/student";
    }
}
