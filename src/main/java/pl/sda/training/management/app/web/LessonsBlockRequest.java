package pl.sda.training.management.app.web;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class LessonsBlockRequest {

    private String id;

    private String name;

    private List<String> lessons = new ArrayList<>();

    public LessonsBlockRequest(String id, String name) {
        this.id = id;
        this.name = name;
    }


}
