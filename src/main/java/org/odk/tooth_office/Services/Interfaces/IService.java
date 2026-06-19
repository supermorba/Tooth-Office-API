package org.odk.tooth_office.Services.Interfaces;

import java.util.List;

public interface IService<T> {
    T save(T entity);
    T update(T entity);
    T getById(Long id);
    List<T> getAll();
}
