package org.odk.tooth_office.Services.Interfaces;

import java.util.List;

public interface IService<T> {
    Response save(T entity);
    Response update(T entity);
    Response getById(Long id);
    Response getAll();
}
