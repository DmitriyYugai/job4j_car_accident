package ru.job4j.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StartUI {
    @Autowired
    private Store store;
    private ConsoleInput input;

    @Autowired
    public void setInput(ConsoleInput input) {
        this.input = input;
    }

    //    public StartUI(Store store, ConsoleInput input) {
//        this.store = store;
//        this.input = input;
//    }

    public void add(String value) {
        store.add(value);
    }

    public void print() {
        for (String value : store.getAll()) {
            System.out.println(value);
        }
    }
}
