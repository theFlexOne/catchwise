package com.flexone.catchwise.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class FishResponse {

    private Long id;
    private String name;
    private String family;
    private String species;
    private String description;
    private String identification;
    private String[] commonNames;

}
