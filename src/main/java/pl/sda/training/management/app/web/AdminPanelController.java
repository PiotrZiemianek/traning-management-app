package pl.sda.training.management.app.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String getCourseCreator(Model model) {

        CourseRequest courseRequest = new CourseRequest("java22");
        LessonsBlockRequest blockRequest = new LessonsBlockRequest("a3453", "Sql i cośtam");
        blockRequest.getLessons().add("selekty i inne takie trudne rzeczy");
        blockRequest.getLessons().add("Jak nie zepsuć połączenia z bazą danych i inne takie");
        LessonsBlockRequest blockRequest1 = new LessonsBlockRequest("b345", "iinne coś");
        blockRequest1.getLessons().add("inne ciekawe rzeczy");
        blockRequest1.getLessons().add("bla bla bla też tak nie bo niebla bla bla też tak nie bo niebla bla bla też tak nie bo niebla bla bla też tak nie bo niebla bla bla też tak nie bo niebla bla bla też tak nie bo niebla bla bla też tak nie bo nie");
        courseRequest.getLessonsBlocks().add(blockRequest);
        courseRequest.getLessonsBlocks().add(blockRequest1);

        model.addAttribute("course", courseRequest);
        model.addAttribute("lessonsBlock", blockRequest1);
        return "admin/course-creator";
    }

    @GetMapping("/accept-submissions")
    public ModelAndView acceptSubmissions() {

        return new ModelAndView("admin/accept-submissions",
                "studentSubmissions", submissionWebService.getSubmissions());
    }
}
