package com.sys.sys.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Yard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome não pode estar em branco")
    private String name;

    @NotBlank(message = "Endereço não pode estar em branco")
    private String adress;

    @NotNull(message = "Capacidade total não pode estar em branco.")
    @Positive(message = "Capacidade total não pode ser negativa ou zero.")
    private Integer maxCapacity;

    @OneToMany(mappedBy = "yard")
    @JsonManagedReference
    private List<Motorcycle> motorcycles;
}