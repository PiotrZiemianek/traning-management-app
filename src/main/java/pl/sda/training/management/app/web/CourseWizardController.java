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
                "course", new CourseDTO());
    }


    @PostMapping("/cname")
    public String postCourseName(CourseDTO course, RedirectAttributes redirectAttributes) {
        log.info("Course with name: " + course.getName());
        redirectAttributes.addFlashAttribute("course", course);
        return "redirect:bname";
    }

    @GetMapping("/bname")
    public String getBlockWizard(@ModelAttribute("course") CourseDTO course) {
        if (course.getName() == null) {
            return "redirect:/admin/course-wizard";
        }
        course.getLessonsBlocks().add(new LessonsBlockDTO());
        return "admin/course-wizard-bname";
    }

    @PostMapping("/bname")
    public String postLessonBlockName(CourseDTO course, RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("course", course);
        return "redirect:lesson";
    }

    @GetMapping("/lesson")
    public String getLessonWizard(@ModelAttribute("course") CourseDTO course) {
        if (course.getName() == null) {
            return "redirect:/admin/course-wizard";
        }

        return "admin/course-wizard-lesson";
    }

    @PostMapping("/lesson")
    public String postLesson(CourseDTO course, RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("course", course);
        return "redirect:lesson";
    }

    @GetMapping("/summary")
    public String getSummary(@ModelAttribute("course") CourseDTO course) {
        if (course.getName() == null) {
            return "redirect:/admin/course-wizard";
        }

        return "admin/course-wizard-summary";
    }

    @PostMapping("/summary")
    public String postSummary(CourseDTO course, RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("course", course);
        return "redirect:summary";
    }
    @GetMapping("/edit")
    public String getEdit(@ModelAttribute("course") CourseDTO course) {
        if (course.getName() == null) {
            return "redirect:/admin/course-wizard";
        }

        return "admin/course-wizard-edit";
    }

    @PostMapping("/edit")
    public String postEdit(CourseDTO course, RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("course", course);
        return "redirect:edit";
    }

    @PostMapping("/save")
    public String save(CourseDTO course) {
        courseService.save(course.toCourse());
        return "redirect:/admin";
    }
}
