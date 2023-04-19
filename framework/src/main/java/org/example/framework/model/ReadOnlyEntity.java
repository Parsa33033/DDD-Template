package org.example.framework.model;

import org.example.framework.dto.DataTransferObject;
import org.example.framework.mapper.Mapper;

public abstract class ReadOnlyEntity<E, D extends DataTransferObject> implements ReadOnly,
    Mapper<E, D> {

}
