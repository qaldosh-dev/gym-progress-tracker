package kz.sdu.kachalka.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import kz.sdu.kachalka.entity.ExerciseRecord;
import kz.sdu.kachalka.entity.ExerciseStatus;

import java.util.List;

@Repository
public interface ExerciseRecordRepository extends JpaRepository<ExerciseRecord, Integer> {

    List<ExerciseRecord> findAllByUserIdOrderById(int userId);

    List<ExerciseRecord> findAllByUserIdAndStatusOrderById(int userId, ExerciseStatus status);

    @Modifying
    @Query("UPDATE ExerciseRecord SET status = :status WHERE id = :id")
    void updateStatus(@Param("id") int id, @Param("status") ExerciseStatus status);
}