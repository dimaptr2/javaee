package ru.velkomfood.mrp.book.server.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.velkomfood.mrp.book.server.model.MaterialMasterRow;

import java.util.List;

public interface MaterialMasterRepository extends CrudRepository<MaterialMasterRow, Long> {

    @Query("from MaterialMasterRow msr where msr.description like ?1 order by msr.id")
    List<MaterialMasterRow> findMaterialMasterRowByDescriptionLike(String description);
    @Query("from MaterialMasterRow msr where msr.id between ?1 and ?2 order by msr.id")
    List<MaterialMasterRow> findMaterialMasterRowByIdBetween(long idLow, long idHigh);

}
