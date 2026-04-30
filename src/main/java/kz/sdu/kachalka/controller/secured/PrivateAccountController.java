package kz.sdu.kachalka.controller.secured;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import kz.sdu.kachalka.entity.dto.ExerciseRecordsContainerDto;
import kz.sdu.kachalka.service.ExerciseRecordService;

@Controller
@RequestMapping("/account")
public class PrivateAccountController {

    private final ExerciseRecordService exerciseRecordService;

    @Autowired
    public PrivateAccountController(ExerciseRecordService exerciseRecordService) {
        this.exerciseRecordService = exerciseRecordService;
    }

    @GetMapping
    public String getMainPage(Model model,
                              @RequestParam(name = "filter", required = false) String filterMode) {
        ExerciseRecordsContainerDto container = exerciseRecordService.findAllRecords(filterMode);
        model.addAttribute("userName", container.getUserName());
        model.addAttribute("records", container.getRecords());
        model.addAttribute("numberOfPlanned", container.getNumberOfPlanned());
        model.addAttribute("numberOfCompleted", container.getNumberOfCompleted());
        model.addAttribute("currentFilter", filterMode != null ? filterMode.toUpperCase() : "");
        return "private/account-page";
    }

    @PostMapping("/add-record")
    public String addRecord(@RequestParam String exerciseName,
                            @RequestParam(required = false) String muscleGroup,
                            @RequestParam Double weight,
                            @RequestParam Integer reps) {
        exerciseRecordService.saveRecord(exerciseName, muscleGroup, weight, reps);
        return "redirect:/account";
    }

    @PostMapping("/complete-record")
    public String completeRecord(@RequestParam int id,
                                 @RequestParam(name = "filter", required = false) String filterMode) {
        exerciseRecordService.completeRecord(id);
        return "redirect:/account" + buildFilterQuery(filterMode);
    }

    @PostMapping("/delete-record")
    public String deleteRecord(@RequestParam int id,
                               @RequestParam(name = "filter", required = false) String filterMode) {
        exerciseRecordService.deleteRecord(id);
        return "redirect:/account" + buildFilterQuery(filterMode);
    }

    private String buildFilterQuery(String filterMode) {
        return (filterMode != null && !filterMode.isBlank()) ? "?filter=" + filterMode : "";
    }
}