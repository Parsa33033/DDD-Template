package org.example.framework.mapper;

public interface Mapper<E, D> {

  D toDataTransferObject();

  E toEntity();

}
