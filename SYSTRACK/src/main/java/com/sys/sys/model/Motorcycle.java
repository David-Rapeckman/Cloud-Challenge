package com.sys.sys.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Motorcycle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Placa não pode estar em branco.")
    @Pattern(
        regexp = "^[A-Z]{3}[0-9][A-Z0-9][0-9]{2}$",
        message = "Placa deve seguir o padrão brasileiro (ABC1234) ou Mercosul (ABC1D23)."
    )
    @Column(unique = true)
    private String plate;

    @NotBlank(message = "Marca não pode estar em branco.")
    private String brand;

    @NotBlank(message = "Modelo não pode estar em branco.")
    private String model;

    @NotNull(message = "Ano não pode estar em branco.")
    private Integer model_year;

    @NotNull(message = "Status não pode estar em branco.")
    @Enumerated(EnumType.STRING)
    private MotorcycleStatus status;

    @NotNull(message = "Quilometragem não pode ser nula.")
    @PositiveOrZero(message = "Quilometragem não pode ser negativa.")
    private Double km;

    @ManyToOne
    @NotNull(message = "Tem que ter um pátio associado.")
    @JsonBackReference
    private Yard yard;

}
