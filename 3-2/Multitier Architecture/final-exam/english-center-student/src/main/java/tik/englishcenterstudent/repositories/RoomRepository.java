package tik.englishcenterstudent.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tik.englishcenterstudent.models.Room;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
    Room findTopByOrderByIdDesc();

    @Query("select DISTINCT r from Room r left join fetch r.examinees where r.examination.id = :examinationId")
    List<Room> findByExaminationId(Integer examinationId);

    @Query("SELECT DISTINCT r FROM Room r left join fetch r.examinees WHERE r.id = :id")
    Optional<Room> findById(Integer id);
}
