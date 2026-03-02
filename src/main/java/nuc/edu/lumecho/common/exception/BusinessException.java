package nuc.edu.lumecho.common.exception;

import lombok.Getter;
import nuc.edu.lumecho.common.Enum.ResultCodeEnum;

@Getter
public class BusinessException extends RuntimeException{
    private final Integer code;
    private final String message;

    public BusinessException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMessage();
    }
}
