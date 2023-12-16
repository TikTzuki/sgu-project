package tik.englishcenter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tik.englishcenter.models.Examination;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExaminationRepository extends JpaRepository<Examination, Integer> {
    @Query("SELECT DISTINCT e FROM Examination e INNER JOIN FETCH e.rooms r")
    public List<Examination> findAllByOrderByIdDesc();

    @Query("SELECT DISTINCT e FROM Examination e LEFT JOIN FETCH e.examinees WHERE e.id = :id")
    public Optional<Examination> findByIdFetchExaminees(Integer id);
}
