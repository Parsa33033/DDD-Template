package org.example.application.mapper;

import java.util.function.Function;
import org.example.application.exceptions.FunctionalErrorException;
import org.example.application.exceptions.InternalErrorException;
import org.example.application.exceptions.InvalidRequestException;
import org.example.framework.dto.DataTransferObject;
import org.example.framework.error.Error;
import org.example.framework.result.Result;
import org.example.model.config.error.OperationErrorCode;

public final class ResultMapper {

  public static <DTO, Data extends DataTransferObject, E extends Error> DTO mapResult(Result<Data, E> result, Function<Data, DTO> func) {
    if (result.isError()) {
      throwException(result.error());
    }
    return func.apply(result.object());
  }

  public static void throwException(Error error) {
    switch (error.code()) {
      case OperationErrorCode.OTHER: throw new InternalErrorException(error.message());
      case OperationErrorCode.INVALID_REQUEST: throw new InvalidRequestException(error.message());
      default: throw new FunctionalErrorException(error.message());
    }
  }
}
