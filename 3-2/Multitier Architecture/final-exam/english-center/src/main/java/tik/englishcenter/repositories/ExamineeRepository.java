package tik.englishcenter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;
import tik.englishcenter.models.Examinee;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExamineeRepository extends JpaRepository<Examinee, Integer> {
    @Query("SELECT e FROM Examinee e WHERE ( e.identity = :identity or e.phone = :phone ) and e.examination.id = :examinationId")
    List<Examinee> findByIdentityOrPhoneAndExaminationId(
            @Param("identity") String identity, @Param("phone") String phone, @Param("examinationId") Integer examinationId
    );

    List<Examinee> findByRoomId(Integer roomId);

    Examinee findByExamineeId(String examineeId);

    @Override
    <S extends Examinee> List<S> saveAll(Iterable<S> entities);

    @Query("SELECT e FROM Examinee e WHERE e.phone LIKE %:phone% OR e.name LIKE %:name%")
    List<Examinee> searchByPhoneAndName(
            @Param("phone") String phone,
            @Param("name") String name
    );
}
