package com.plataformas.hilos.controller;

import com.plataformas.hilos.model.Vehiculo;
import com.plataformas.hilos.repository.VehiculoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehiculos")
public class    VehiculoController {

    private final VehiculoRepository vehiculoRepository;

    public VehiculoController(VehiculoRepository vehiculoRepository) {
        this.vehiculoRepository = vehiculoRepository;
    }

    // 1. Crear vehículo
    @PostMapping
    public Vehiculo crearVehiculo(@RequestBody Vehiculo vehiculo) {
        return vehiculoRepository.save(vehiculo);
    }

    // 2. Listar todos
    @GetMapping
    public List<Vehiculo> listarVehiculos() {
        return vehiculoRepository.findAll();
    }

    // 3. Listar disponibles
    @GetMapping("/disponibles")
    public List<Vehiculo> listarDisponibles() {
        return vehiculoRepository.findByEstadoTrue();
    }

    // 4. Listar no disponibles
    @GetMapping("/ocupados")
    public List<Vehiculo> listarNoDisponibles() {
        return vehiculoRepository.findByEstadoFalse();
    }
}

