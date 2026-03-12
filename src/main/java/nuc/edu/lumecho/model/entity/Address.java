package nuc.edu.lumecho.model.entity;

import lombok.Data;

@Data
public class Address {
    private Long id;
    private Long userId;
    private String province;
    private String city;
    private String district;
    private String detail;
}
