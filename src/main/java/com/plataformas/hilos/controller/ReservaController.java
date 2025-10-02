package com.plataformas.hilos.controller;

import com.plataformas.hilos.model.Reserva;
import com.plataformas.hilos.model.Vehiculo;
import com.plataformas.hilos.repository.ReservaRepository;
import com.plataformas.hilos.repository.VehiculoRepository;
import com.plataformas.hilos.service.ReservaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    private final ReservaService reservaService;
    private final ReservaRepository reservaRepository;
    private final VehiculoRepository vehiculoRepository;

    public ReservaController(ReservaService reservaService,
                             ReservaRepository reservaRepository,
                             VehiculoRepository vehiculoRepository) {
        this.reservaService = reservaService;
        this.reservaRepository = reservaRepository;
        this.vehiculoRepository = vehiculoRepository;
    }

    @PostMapping("/{vehiculoId}")
    public String reservarVehiculo(@PathVariable Integer vehiculoId,
                                   @RequestParam String cliente) {
        // Buscar vehículo en la base de datos
        Vehiculo vehiculo = vehiculoRepository.findById(vehiculoId)
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado: " + vehiculoId));

        // Llamar al servicio con el objeto Vehículo
        reservaService.procesarReserva(cliente, vehiculo);

        return "Reserva en proceso para el cliente: " + cliente +
                " en Vehículo ID " + vehiculo.getId() +
                " (" + vehiculo.getMarca() + " " + vehiculo.getModelo() + ")";
    }

    @GetMapping
    public List<Reserva> listarReservas() {
        return reservaRepository.findAll();
    }
}



