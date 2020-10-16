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
    public ModelAndView getAdminPanel() {

        return new ModelAndView("admin/index",
                "studentSubmissions", submissionWebService.getSubmissions());
    }
}
