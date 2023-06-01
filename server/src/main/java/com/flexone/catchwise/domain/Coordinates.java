package com.flexone.catchwise.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.mapping.Any;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Coordinates {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @JsonIgnore
        private Long id;

        private Double latitude;
        private Double longitude;

        @OneToOne(mappedBy = "coordinates")
        @JsonIgnore
        private Lake lake;

}
