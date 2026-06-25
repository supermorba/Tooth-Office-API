package org.odk.tooth_office.Services.Interfaces;

import org.odk.tooth_office.utils.Response;

import java.util.List;

public interface IService<REQUEST, ID> {
    Response save(REQUEST entity);
    Response update(REQUEST entity, ID id);
    Response getById(ID id);
    Response getAll();
    Response delete(ID id);
}
