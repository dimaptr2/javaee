package ru.velkomfood.reports.mrp.model;

import ru.velkomfood.reports.mrp.view.OutputView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DataBuffer implements Serializable {

    private long counter;
    private List<OutputView> outputViews;

    public DataBuffer() {
        counter = 0;
        outputViews = new ArrayList<>();
    }

    public void refresh() {
        outputViews.clear();
    }

    public long getCounter() {
        return counter;
    }

    public void setCounter(long counter) {
        this.counter = counter;
    }

    public List<OutputView> getOutputViews() {
        return outputViews;
    }

    public void setOutputViews(List<OutputView> outputViews) {
        this.outputViews.clear();
        this.outputViews = outputViews;
    }

    public void increaseCounter(long value) {
        counter = counter + value;
    }

    public void decreaseCounter(long value) {
        counter = counter - value;
    }

}
