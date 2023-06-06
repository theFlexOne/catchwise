package com.flexone.catchwise.bootstrap.dto;

import lombok.Data;

@Data
public class FishJSON {

    private String name;
    private String species;
    private String family;
    private String description;
    private String identification;

    private String[] commonNames;


}
