package nuc.edu.lumecho.model.entity;

import lombok.Data;

@Data
public class CityInfo {
    private Long id;
    private String name;
    private Long provinceId;
}