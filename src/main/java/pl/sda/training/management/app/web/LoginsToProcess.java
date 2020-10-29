package pl.sda.training.management.app.web;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class LoginsToProcess {
    private List<String> loginsToProcess = new ArrayList<>();
}
