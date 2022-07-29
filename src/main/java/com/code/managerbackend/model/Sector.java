package com.code.managerbackend.model;

import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Sector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDateTime initDate;

    private Boolean situation;

    @OneToMany(mappedBy = "sector", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Office> offices = new ArrayList<>();

    @UpdateTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private LocalDateTime updatedAt;
}
