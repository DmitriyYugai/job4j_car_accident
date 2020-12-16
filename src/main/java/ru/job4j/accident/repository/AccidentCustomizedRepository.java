package ru.job4j.accident.repository;

public interface AccidentCustomizedRepository<T> {

    boolean update(T accident);

    <S extends T> S save(S accident);
}
