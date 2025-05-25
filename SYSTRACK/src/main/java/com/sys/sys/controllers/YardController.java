package com.sys.sys.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
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

import com.sys.sys.model.Yard;
import com.sys.sys.repository.YardRepository;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import jakarta.validation.Valid;

@RestController
@RequestMapping("yards")
public class YardController {

    public record YardFilters(Integer maxCapacity) {
    }

    @Autowired
    private YardRepository repository;

    @GetMapping
    public Page<Yard> index(
            YardFilters filters,
            @PageableDefault(size = 5, sort = "maxCapacity", direction = Direction.DESC) Pageable pageable) {

        if (filters.maxCapacity() != null) {
            return repository.findByMaxCapacityLessThanEqual(filters.maxCapacity(), pageable);
        } else {
            return repository.findAll(pageable);
        }
    }

    @PostMapping
    @CacheEvict(value = "yards", allEntries = true)
    @ResponseStatus(code = HttpStatus.CREATED)
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Dados esperados para cadastrar um pátio.",
        content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                name = "Exemplo de pátio",
                summary = "Exemplo de pátio.",
                value = """
                    {   
                        "name": "Filial Paulista",
                        "maxCapacity": 1,
                        "adress": "Av. Paulista, 2200"
                    }
                """
            )
        )
    )
    public Yard create(@RequestBody @Valid Yard yards) {
        System.out.println("Cadastrando pátio: " + yards.getName());
        return repository.save(yards);
    }

    @GetMapping("{id}")
    public ResponseEntity<Yard> get(@PathVariable Long id) {
        return ResponseEntity.ok(getYard(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Yard> delete(@PathVariable Long id) {
        repository.delete(getYard(id));
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Dados esperados para alterar informações um pátio.",
        content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                name = "Exemplo de pátio",
                summary = "Dados alterados: capacidade máxima(era 1 virou 10).",
                value = """
                    {   
                        "name": "Filial Paulista",
                        "maxCapacity": 10,
                        "adress": "Av. Paulista, 2200"
                    }
                """
            )
        )
    )
    public ResponseEntity<Yard> update(@PathVariable Long id, @RequestBody @Valid Yard yard) {
        getYard(id);
        yard.setId(id);
        repository.save(yard);
        return ResponseEntity.ok(yard);
    }

    private Yard getYard(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pátio não encontrado"));
    }
}
