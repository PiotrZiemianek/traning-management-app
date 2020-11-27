package pl.sda.training.management.app.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Map;

@Controller
@RequestMapping("trainer")
@RequiredArgsConstructor
@Slf4j
public class TrainerPanelController {
    private final CourseEditionWebService courseEditionWebService;
    private final LessonsBlockWebService lessonsBlockWebService;
    private final LessonDetailsWebService lessonDetailsWebService;

    @GetMapping
    public String getTrainerPanel() {

        return "redirect:trainer/courses";
    }

    @GetMapping("/courses")
    public ModelAndView getTrainerCourses(Principal principal) {
        return new ModelAndView("trainer/courses",
                "coursesEditions", courseEditionWebService
                .getAllEditionsToChooseByTrainerLogin(principal.getName()));
    }

    @PostMapping("/blocks")
    public ModelAndView getTrainerBlocksByCourse(Principal principal, String editionCode) {
        return new ModelAndView("trainer/blocks",
                "lessonBlocks",
                Map.of(editionCode,
                        lessonsBlockWebService
                                .getAllByTrainerLoginAndEditionCode(principal.getName(), editionCode)));
    }

    @GetMapping("/blocks")
    public ModelAndView getTrainerBlocks(Principal principal) {
        return new ModelAndView("trainer/blocks",
                "lessonBlocks",
                lessonsBlockWebService
                        .getAllByTrainerLoginOrderedByEditionCode(principal.getName()));
    }

    @PostMapping("/lessons")
    public ModelAndView getTrainerLessonsByBlock(Principal principal, Long blockId) {
        return new ModelAndView("trainer/lessons",
                "lessons",
                lessonDetailsWebService.getAllByTrainerLoginAndLessonBlockId(principal.getName(), blockId));
    }

    @GetMapping("/lessons")
    public ModelAndView getTrainerLessons(Principal principal) {
        return new ModelAndView("trainer/lessons",
                "lessons",
                lessonDetailsWebService.getAllByTrainerLogin(principal.getName()));
    }

}
