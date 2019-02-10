package ru.velkomfood.mrp.book.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.velkomfood.mrp.book.server.model.MaterialMasterRow;
import ru.velkomfood.mrp.book.server.model.MrpInfoRow;
import ru.velkomfood.mrp.book.server.model.keys.MrpInfoKey;
import ru.velkomfood.mrp.book.server.repository.MaterialMasterRepository;
import ru.velkomfood.mrp.book.server.repository.MrpInfoRepository;
import ru.velkomfood.mrp.book.server.repository.StockRepository;

@Service
public class DatabaseManager {

    private final MrpInfoRepository mrpInfoRepository;
    private final MaterialMasterRepository materialMasterRepository;
    private final StockRepository stockRepository;

    @Autowired
    public DatabaseManager(
            MrpInfoRepository mrpInfoRepository, MaterialMasterRepository materialMasterRepository,
            StockRepository stockRepository) {

        this.mrpInfoRepository = mrpInfoRepository;
        this.materialMasterRepository = materialMasterRepository;
        this.stockRepository = stockRepository;

    }

    public void saveMrpInfoEntity(MrpInfoRow entity) {

        if (mrpInfoRowExists(entity)) {
            mrpInfoRepository.delete(entity);
        }

        mrpInfoRepository.save(entity);
    }

    public void saveMaterialMasterEntity(MaterialMasterRow entity) {
        materialMasterRepository.save(entity);
    }

    // private section

    private boolean mrpInfoRowExists(MrpInfoRow row) {

        MrpInfoKey key = new MrpInfoKey(
                row.getPlant(), row.getMaterialId(), row.getPeriod(), row.getYear(), row.getPurchaseGroup()
        );

        if (mrpInfoRepository.findById(key).isPresent()) {
            return true;
        } else {
            return false;
        }

    }
}
