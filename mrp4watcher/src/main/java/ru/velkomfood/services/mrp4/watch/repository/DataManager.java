package ru.velkomfood.services.mrp4.watch.repository;

import ru.velkomfood.services.mrp4.watch.Component;

public interface DataManager extends Component {
    void configure();
    void saveData(String address);
}
