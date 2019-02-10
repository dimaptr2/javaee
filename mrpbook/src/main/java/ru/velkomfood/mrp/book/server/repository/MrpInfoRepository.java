package ru.velkomfood.mrp.book.server.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.velkomfood.mrp.book.server.model.MrpInfoRow;
import ru.velkomfood.mrp.book.server.model.keys.MrpInfoKey;

import java.util.List;

public interface MrpInfoRepository extends CrudRepository<MrpInfoRow, MrpInfoKey> {

    @Query("from MrpInfoRow mr where mr.plant = ?1 and mr.materialId = ?2 and mr.year = ?3 " +
            "order by mr.plant, mr.materialId")
    List<MrpInfoRow> findMrpInfoRowByPlantAndMaterialIdAndYear(int plant, long materialId, int year);

    @Query("from MrpInfoRow mr where mr.plant = ?1 and mr.year = ?2 and mr.period between ?3 and ?4 " +
            "order by mr.plant, mr.materialId, mr.year, mr.period")
    List<MrpInfoRow> findMrpInfoRowByPlantAndYearAndPeriodBetween(int plant, int year, int periodLow, int periodHigh);

}
