package com.sys.sys.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.sys.sys.model.Motorcycle;
import com.sys.sys.model.MotorcycleStatus;
import com.sys.sys.repository.MotorcycleRepository;
import com.sys.sys.service.MotorcycleService;
import com.sys.sys.specification.MotorcycleSpecification;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import jakarta.validation.Valid;


@RestController
@RequestMapping("motorcycles")
public class MotorcycleController {
    
    public record MotorcycleFilters(
    String plate,
    String brand,
    String model,
    MotorcycleStatus status,
    Integer modelYear,
    Integer modelYearStart,
    Integer modelYearEnd,
    Double kmMin,
    Double kmMax,
    Long yardId
    ) {}


    @Autowired
    private MotorcycleService motorcycleService;

    @Autowired
    private MotorcycleRepository repository;

    @GetMapping
    
    public Page<Motorcycle> index(
            MotorcycleFilters filters,
            @PageableDefault(size = 5) Pageable pageable) {

        var specification = MotorcycleSpecification.withFilters(filters);
        return repository.findAll(specification, pageable);
    }

    @PostMapping
    @CacheEvict(value = "motorcycles", allEntries = true)
    @ResponseStatus(code = HttpStatus.CREATED)
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Dados esperados para cadastrar uma motocicleta.",
        content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                name = "Exemplo de motocicleta",
                summary = "Exemplo de uma moto Honda CG 160",
                value = """
                    {
                        "plate": "XYZ1234",
                        "brand": "Honda",
                        "model": "CG 160",
                        "model_year": 2021,
                        "km": 8500.0,
                        "status": "Manutenção",
                        "yard": {
                            "id": 3
                        }
                    }
                """
            )
        )
    )
    public Motorcycle create(@RequestBody @Valid Motorcycle motorcycle) {
        return motorcycleService.saveMotorcycle(motorcycle);
    }

    @GetMapping("{id}")
    public ResponseEntity<Motorcycle> get(@PathVariable Long id) {
        return ResponseEntity.ok(getMotorcycle(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Motorcycle> delete(@PathVariable Long id) {
        repository.delete(getMotorcycle(id));
        return ResponseEntity.noContent().build();
    }
    
     @PutMapping("{id}")
     @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Dados esperados para alterar os dados de uma motocicleta.",
        content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                name = "Exemplo de motocicleta",
                summary = "Dados alterados: alteração do pátio da motocicleta (era 3 virou 2)",
                value = """
                    {
                        "plate": "XYZ1234",
                        "brand": "Honda",
                        "model": "CG 160",
                        "model_year": 2021,
                        "km": 8500.0,
                        "status": "Manutenção",
                        "yard": {
                            "id": 2
                        }
                    }
                """
            )
        )
    )
    public ResponseEntity<Motorcycle> updateMotorcycle(@PathVariable Long id, @RequestBody Motorcycle moto) {
        Motorcycle updated = motorcycleService.updateMotorcycle(id, moto);
        return ResponseEntity.ok(updated);
}

    private Motorcycle getMotorcycle(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Moto não encontrada"));
    }
}