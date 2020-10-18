package pl.sda.training.management.app.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sda.training.management.app.domain.service.StudentSubmissionService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentSubmissionWebService {
    private final StudentSubmissionService submissionService;


    public List<StudentSubmissionResponse> getSubmissions() {
        return submissionService.getAll().stream()
                .map(StudentSubmissionResponse::of)
                .collect(Collectors.toList());
    }

    public void acceptSubmissionById(Long id) {
        submissionService.acceptSubmission(id);
    }

    public void deleteSubmissionById(Long id){
        submissionService.delete(id);
    }
}
