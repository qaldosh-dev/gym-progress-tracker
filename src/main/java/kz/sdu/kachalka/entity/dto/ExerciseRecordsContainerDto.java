package kz.sdu.kachalka.entity.dto;

import kz.sdu.kachalka.entity.ExerciseRecord;
import kz.sdu.kachalka.entity.ExerciseStatus;

import java.util.List;

public class ExerciseRecordsContainerDto {

    private final String userName;
    private final List<ExerciseRecord> records;
    private final long numberOfPlanned;
    private final long numberOfCompleted;

    public ExerciseRecordsContainerDto(String userName, List<ExerciseRecord> records) {
        this.userName = userName;
        this.records = records;
        this.numberOfPlanned = records.stream()
                .filter(r -> r.getStatus() == ExerciseStatus.PLANNED)
                .count();
        this.numberOfCompleted = records.stream()
                .filter(r -> r.getStatus() == ExerciseStatus.COMPLETED)
                .count();
    }

    public String getUserName() { return userName; }
    public List<ExerciseRecord> getRecords() { return records; }
    public long getNumberOfPlanned() { return numberOfPlanned; }
    public long getNumberOfCompleted() { return numberOfCompleted; }
}