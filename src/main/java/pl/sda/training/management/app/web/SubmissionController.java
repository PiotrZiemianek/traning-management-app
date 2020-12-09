package pl.sda.training.management.app.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("submission")
public class SubmissionController {
    private final CourseWebService courseWebService;
    private final CourseEditionWebService courseEditionWebService;
    private final StudentSubmissionWebService submissionWebService;

    @GetMapping
    public ModelAndView getChooseCourseView() {
        return new ModelAndView("submission/submission-choose-course",
                "coursesToChoose", courseWebService.getAllCoursesToChoose());
    }

    @PostMapping
    public ModelAndView getChooseEditionView(Long chosenCourseId, Principal principal) {
        return new ModelAndView("submission/submission-choose-edition",
                "editionsCodes", courseEditionWebService.getEditionsCodesByCourseIdAndUserNotParticipated(chosenCourseId, principal.getName()));
    }

    @PostMapping("/submit")
    private String submit(String editionCode, Principal principal) {
        submissionWebService.sendSubmission(editionCode, principal.getName());
        return "submission/submission-submitted";
    }

}
