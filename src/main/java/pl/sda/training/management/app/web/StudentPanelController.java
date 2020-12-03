package pl.sda.training.management.app.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
@RequestMapping("student")
@RequiredArgsConstructor
@Slf4j
public class StudentPanelController {
    private final LessonsBlockWebService lessonsBlockWebService;
    private final LessonDetailsWebService lessonDetailsWebService;

    @GetMapping
    public String getStudentPanel() {

        return "redirect:student/blocks";
    }

    @GetMapping("/blocks")
    public ModelAndView getStudentBlocks(Principal principal) {
        return new ModelAndView("student/blocks",
                "lessonBlocks",
                lessonsBlockWebService
                        .getAllByStudentLoginOrderedByEditionCode(principal.getName()));
    }

    @PostMapping("/lessons")
    public ModelAndView getStudentLessonsByBlock(String editionCode, Long blockId) {
        return new ModelAndView("student/lessons",
                "lessons",
                lessonDetailsWebService.getAllByEditionCodeAndBlockId(editionCode, blockId));
    }

}
