package ru.velkomfood.mrp.book.server.repository;

import org.springframework.data.repository.CrudRepository;
import ru.velkomfood.mrp.book.server.model.Stock;
import ru.velkomfood.mrp.book.server.model.keys.StockKey;

public interface StockRepository extends CrudRepository<Stock, StockKey> {

}
