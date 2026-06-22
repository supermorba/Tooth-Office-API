package org.odk.tooth_office.Services.Interfaces;

import org.odk.tooth_office.utils.Response;

import java.util.List;

public interface IService<T> {
    Response save(T entity);
    Response update(T entity);
    Response getById(Long id);
    Response getAll();
}
