package nuc.edu.lumecho.model.dto.request.photographer;

import lombok.Data;

import javax.validation.constraints.NotBlank;


@Data
public class PhotographerApplyRequest {

    @NotBlank(message = "简介不能为空")
    private String description;

}
