package nuc.edu.lumecho.common.exception;

import nuc.edu.lumecho.common.Enum.ResultCodeEnum;
import nuc.edu.lumecho.common.Result;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<Map<String, String>> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        Map<String, List<FieldError>> fieldErrorsGrouped =
                ex.getBindingResult().getFieldErrors().stream()
                        .collect(Collectors.groupingBy(FieldError::getField));

        for (Map.Entry<String, List<FieldError>> entry : fieldErrorsGrouped.entrySet()) {
            String field = entry.getKey();
            List<FieldError> errorsForField = entry.getValue();

            String message = errorsForField.stream()
                    .min(Comparator.comparingInt(err -> {
                        String code = err.getCode();
                        if ("NotBlank".equals(code)) return 0;
                        if ("NotNull".equals(code)) return 1;
                        if ("NotEmpty".equals(code)) return 2;
                        return 3;
                    }))
                    .map(FieldError::getDefaultMessage)
                    .orElse("参数校验失败");

            errors.put(field, message);
        }

        Result<Map<String, String>> result = Result.build(errors);
        result.setCode(400);
        result.setMessage("请求参数格式不合法");
        return result;
    }

    @ExceptionHandler(BusinessException.class)
    public Result handleBusinessException(BusinessException ex) {
        return Result.fail(ex.getCode(), ex.getMessage());
    }
}
