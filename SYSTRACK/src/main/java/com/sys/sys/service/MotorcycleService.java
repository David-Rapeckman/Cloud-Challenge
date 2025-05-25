package com.sys.sys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sys.sys.model.Motorcycle;
import com.sys.sys.model.Yard;
import com.sys.sys.repository.MotorcycleRepository;
import com.sys.sys.repository.YardRepository;

@Service
public class MotorcycleService {

    @Autowired
    private MotorcycleRepository motorcycleRepository;

    @Autowired
    private YardRepository yardRepository;

    public Motorcycle saveMotorcycle(Motorcycle motorcycle) {
        // Lógica de criação
        if (motorcycleRepository.existsByPlate(motorcycle.getPlate())) {
            throw new IllegalArgumentException("Já existe uma moto cadastrada com essa placa.");
        }

        Yard yard = getValidYard(motorcycle.getYard());
        checkYardCapacity(yard);

        motorcycle.setYard(yard);
        return motorcycleRepository.save(motorcycle);
    }

    public Motorcycle updateMotorcycle(Long id, Motorcycle updatedData) {
        Motorcycle existing = motorcycleRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Moto não encontrada."));

        // Verifica se a nova placa já existe em outra moto
        if (!existing.getPlate().equals(updatedData.getPlate()) &&
            motorcycleRepository.existsByPlate(updatedData.getPlate())) {
            throw new IllegalArgumentException("Já existe uma moto cadastrada com essa placa.");
        }

        Yard yard = getValidYard(updatedData.getYard());

        // Se o pátio foi trocado, verifica a capacidade
        if (!existing.getYard().getId().equals(yard.getId())) {
            checkYardCapacity(yard);
        }

        existing.setPlate(updatedData.getPlate());
        existing.setModel_year(updatedData.getModel_year());
        existing.setBrand(updatedData.getBrand());
        existing.setModel(updatedData.getModel());
        existing.setKm(updatedData.getKm());
        existing.setStatus(updatedData.getStatus());
        existing.setYard(yard);

        return motorcycleRepository.save(existing);
    }

    private Yard getValidYard(Yard yard) {
        if (yard == null) {
            throw new IllegalArgumentException("Pátio não pode ser nulo.");
        }

        return yardRepository.findById(yard.getId())
            .orElseThrow(() -> new IllegalArgumentException("Pátio não encontrado."));
    }

    private void checkYardCapacity(Yard yard) {
        long totalMotos = motorcycleRepository.countByYard(yard);
        if (totalMotos >= yard.getMaxCapacity()) {
            throw new IllegalStateException("O pátio está cheio. Capacidade máxima atingida.");
        }
    }
}
