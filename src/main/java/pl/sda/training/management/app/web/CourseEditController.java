package pl.sda.training.management.app.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("admin/course-edit")
@RequiredArgsConstructor
public class CourseEditController {
    private final CourseWebService courseWebService;

    @GetMapping
    public ModelAndView getCourseEdit() {
        List<CourseToChoose> coursesToChoose = courseWebService.getAllCoursesToChoose();
        return new ModelAndView("admin/course-edit-which",
                "coursesToChoose", coursesToChoose);
    }

    @PostMapping
    public String postId(Long chosenCourseId, Model model) {
        if (chosenCourseId == null) {
            return "redirect:/admin/course-edit";
        }

        model.addAttribute("course", courseWebService.getCourseById(chosenCourseId));

        return "admin/course-edit";
    }

    @PostMapping("/save")
    public String saveCourse(CourseDTO course) {
        courseWebService.save(course.toCourse());

        return "redirect:/admin/course-edit";
    }
}
