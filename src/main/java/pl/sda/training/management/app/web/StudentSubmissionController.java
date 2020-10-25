package pl.sda.training.management.app.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("submissions")
@RequiredArgsConstructor
@Slf4j
public class StudentSubmissionController {
    private final StudentSubmissionWebService submissionWebService;

    @PostMapping("/{id}/accept")
    public String confirm(@PathVariable Long id) {
        submissionWebService.acceptSubmissionById(id);
        log.debug("Submission with id: " + id + "accepted.");
        return "redirect:/admin/accept-submissions";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        submissionWebService.deleteSubmissionById(id);
        log.debug("Submission with id: " + id + "deleted.");
        return "redirect:/admin/accept-submissions";
    }
}
