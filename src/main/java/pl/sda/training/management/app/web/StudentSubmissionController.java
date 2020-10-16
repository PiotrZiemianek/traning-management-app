package pl.sda.training.management.app.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("submissions")
@RequiredArgsConstructor
public class StudentSubmissionController {
    private final StudentSubmissionWebService submissionWebService;

    @PostMapping("/{id}/accept")
    public String confirm(@PathVariable Long id) {
        System.out.println(id);
        submissionWebService.acceptSubmissionById(id);
        return "redirect:/admin";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        System.out.println(id);
        submissionWebService.deleteSubmissionById(id);
        return "redirect:/admin";
    }
}
