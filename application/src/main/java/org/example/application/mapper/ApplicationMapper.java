package org.example.application.mapper;

import org.example.framework.dto.DataTransferObject;

public interface ApplicationMapper<DTO, Data extends DataTransferObject> {

  DTO mapFrom(Data data);
}
