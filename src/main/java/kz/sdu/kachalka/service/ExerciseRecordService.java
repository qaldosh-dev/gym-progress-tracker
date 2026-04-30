package kz.sdu.kachalka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kz.sdu.kachalka.entity.ExerciseRecord;
import kz.sdu.kachalka.entity.ExerciseStatus;
import kz.sdu.kachalka.entity.User;
import kz.sdu.kachalka.entity.dto.ExerciseRecordsContainerDto;
import kz.sdu.kachalka.repository.ExerciseRecordRepository;

import java.util.List;

@Service
@Transactional
public class ExerciseRecordService {

    private final ExerciseRecordRepository exerciseRecordRepository;
    private final UserService userService;

    @Autowired
    public ExerciseRecordService(ExerciseRecordRepository exerciseRecordRepository, UserService userService) {
        this.exerciseRecordRepository = exerciseRecordRepository;
        this.userService = userService;
    }
    private double calculateOneRepMax(double weight, int reps) {
        return weight * (1.0 + reps / 30.0);
    }

    @Transactional(readOnly = true)
    public ExerciseRecordsContainerDto findAllRecords(String filterMode) {
        User user = userService.getCurrentUser();
        List<ExerciseRecord> records;

        if (filterMode != null && !filterMode.isBlank()) {
            try {
                ExerciseStatus status = ExerciseStatus.valueOf(filterMode.toUpperCase());
                records = exerciseRecordRepository.findAllByUserIdAndStatusOrderById(user.getId(), status);
            } catch (IllegalArgumentException e) {
                records = exerciseRecordRepository.findAllByUserIdOrderById(user.getId());
            }
        } else {
            records = exerciseRecordRepository.findAllByUserIdOrderById(user.getId());
        }

        return new ExerciseRecordsContainerDto(user.getName(), records);
    }

    public void saveRecord(String exerciseName, String muscleGroup, Double weight, Integer reps) {
        if (exerciseName == null || exerciseName.isBlank()) return;
        if (weight == null || reps == null || weight <= 0 || reps <= 0) return;

        User user = userService.getCurrentUser();
        double oneRepMax = calculateOneRepMax(weight, reps);
        oneRepMax = Math.round(oneRepMax * 100.0) / 100.0;

        ExerciseRecord record = new ExerciseRecord(exerciseName, muscleGroup, weight, reps, oneRepMax, user);
        exerciseRecordRepository.save(record);
    }

    public void completeRecord(int id) {
        User user = userService.getCurrentUser();
        exerciseRecordRepository.findById(id).ifPresent(record -> {
            if (record.getUser().getId() == user.getId()) {
                exerciseRecordRepository.updateStatus(id, ExerciseStatus.COMPLETED);
            }
        });
    }

    public void deleteRecord(int id) {
        User user = userService.getCurrentUser();
        exerciseRecordRepository.findById(id).ifPresent(record -> {
            if (record.getUser().getId() == user.getId()) {
                exerciseRecordRepository.deleteById(id);
            }
        });
    }
}