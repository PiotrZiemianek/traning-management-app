package pl.sda.training.management.app.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static pl.sda.training.management.app.utils.Constants.API_PRODUCES;
import static pl.sda.training.management.app.utils.Constants.API_URL;

@RestController
@RequestMapping(path = API_URL + "/lesson", produces = API_PRODUCES)
@CrossOrigin("*")
@RequiredArgsConstructor
public class LessonApiController {
}
