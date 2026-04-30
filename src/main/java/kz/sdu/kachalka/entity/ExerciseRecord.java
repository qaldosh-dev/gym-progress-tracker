package kz.sdu.kachalka.entity;

import javax.persistence.*;

@Entity
@Table(name = "exercise_records")
public class ExerciseRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "exercise_name", nullable = false, length = 100)
    private String exerciseName;

    @Column(name = "muscle_group", length = 50)
    private String muscleGroup;

    @Column(name = "weight", nullable = false)
    private Double weight;

    @Column(name = "reps", nullable = false)
    private Integer reps;

    @Column(name = "one_rep_max")
    private Double oneRepMax;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ExerciseStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public ExerciseRecord() {}

    public ExerciseRecord(String exerciseName, String muscleGroup, Double weight, Integer reps, Double oneRepMax, User user) {
        this.exerciseName = exerciseName;
        this.muscleGroup = muscleGroup;
        this.weight = weight;
        this.reps = reps;
        this.oneRepMax = oneRepMax;
        this.status = ExerciseStatus.PLANNED;
        this.user = user;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getExerciseName() { return exerciseName; }
    public void setExerciseName(String exerciseName) { this.exerciseName = exerciseName; }

    public String getMuscleGroup() { return muscleGroup; }
    public void setMuscleGroup(String muscleGroup) { this.muscleGroup = muscleGroup; }

    public Double getWeight() { return weight; }
    public void setWeight(Double weight) { this.weight = weight; }

    public Integer getReps() { return reps; }
    public void setReps(Integer reps) { this.reps = reps; }

    public Double getOneRepMax() { return oneRepMax; }
    public void setOneRepMax(Double oneRepMax) { this.oneRepMax = oneRepMax; }

    public ExerciseStatus getStatus() { return status; }
    public void setStatus(ExerciseStatus status) { this.status = status; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}