package com.sys.sys.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sys.sys.model.Motorcycle;
import com.sys.sys.model.MotorcycleStatus;
import com.sys.sys.model.Yard;
import com.sys.sys.repository.MotorcycleRepository;
import com.sys.sys.repository.YardRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder {

    @Autowired
    private YardRepository yardRepository;

    @Autowired
    private MotorcycleRepository motorcycleRepository;

    @PostConstruct
    public void init() {
        var yards = List.of(
            Yard.builder().name("Pátio Central").maxCapacity(150).adress("Rua da Consolação, 1900").build(),
            Yard.builder().name("Pátio Norte").maxCapacity(50).adress("Av. Marginal Tiête, 300").build(),
            Yard.builder().name("Pátio Sul").maxCapacity(70).adress("Av. Ataliba Leonel, 782").build()
        );

        var savedYards = yardRepository.saveAll(yards);

        var brands = List.of("Honda", "Yamaha", "Suzuki", "Kawasaki", "BMW");
        var models = List.of("CG 160", "XJ6", "GSR 750", "Ninja 300", "GS 1200");
        var statusList = List.of(MotorcycleStatus.Funcional, MotorcycleStatus.Manutenção);

        var motos = new ArrayList<Motorcycle>();
        var random = new Random();

        for (Yard yard : savedYards) {
            for (int i = 0; i < 30; i++) {
                motos.add(Motorcycle.builder()
                    .plate(generateRandomPlate(random))
                    .brand(brands.get(random.nextInt(brands.size())))
                    .model(models.get(random.nextInt(models.size())))
                    .model_year(2000 + random.nextInt(25))
                    .status(statusList.get(random.nextInt(statusList.size())))
                    .km(random.nextDouble() * 50000)
                    .yard(yard)
                    .build());
            }
        }

        motorcycleRepository.saveAll(motos);
    }

    private String generateRandomPlate(Random random) {
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String digits = "0123456789";
        return String.format("%c%c%c%d%c%d%d",
            letters.charAt(random.nextInt(26)),
            letters.charAt(random.nextInt(26)),
            letters.charAt(random.nextInt(26)),
            random.nextInt(10),
            letters.charAt(random.nextInt(26)),
            random.nextInt(10),
            random.nextInt(10)
        );
    }
}