package pl.sda.training.management.app.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.sda.training.management.app.domain.model.Course;
import pl.sda.training.management.app.domain.model.LessonsBlock;
import pl.sda.training.management.app.domain.service.CourseService;

@Controller
@RequestMapping("admin/course-wizard")
@Slf4j
@RequiredArgsConstructor
public class CourseWizardController {
    private final CourseService courseService;

    @GetMapping
    public ModelAndView getCourseWizard() {

        return new ModelAndView("admin/course-wizard-cname",
                "course", new Course());
    }


    @PostMapping("/cname")
    public String postCourseName(Course course, RedirectAttributes redirectAttributes) {
        log.info("Course with name: " + course.getName().value());
        redirectAttributes.addFlashAttribute("course", course);
        return "redirect:bname";
    }

    @GetMapping("/bname")
    public String getBlockWizard(@ModelAttribute Course course) {
        if (course.getName() == null) {
            return "redirect:/admin/course-wizard";
        }
        course.getLessonsBlocks().add(new LessonsBlock());
        return "admin/course-wizard-bname";
    }

    @PostMapping("/bname")
    public String postLessonBlockName(Course course, RedirectAttributes redirectAttributes) {
        log.info("course name: " + course.getName().value());
        log.info(course.getLessonsBlocks().toString());
        redirectAttributes.addFlashAttribute("course", course);
        return "redirect:lesson";
    }

    @GetMapping("/lesson")
    public String getLessonWizard(@ModelAttribute Course course) {
        if (course.getName() == null) {
            return "redirect:/admin/course-wizard";
        }
        return "admin/course-wizard-lesson";
    }
}
