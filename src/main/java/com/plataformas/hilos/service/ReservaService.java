package com.plataformas.hilos.service;

import com.plataformas.hilos.model.Reserva;
import com.plataformas.hilos.model.Vehiculo;
import com.plataformas.hilos.repository.ReservaRepository;
import com.plataformas.hilos.repository.VehiculoRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final VehiculoRepository vehiculoRepository;

    private final ExecutorService executor = Executors.newFixedThreadPool(5);

    private int contadorReservas = 0;
    private final int MAX_RESERVAS = 10;


    private boolean reservasAutomaticasActivas = true;


    private final String[] nombresClientes = {"Carlos", "María", "Ana", "Luis", "Julián", "Sofía", "Pedro", "Camila"};

    private final Random random = new Random();

    public ReservaService(ReservaRepository reservaRepository, VehiculoRepository vehiculoRepository) {
        this.reservaRepository = reservaRepository;
        this.vehiculoRepository = vehiculoRepository;
    }

    /**
     * Procesa una reserva de vehículo para un cliente en un hilo separado.
     */
    public void procesarReserva(String cliente, Vehiculo vehiculo) {
        executor.submit(() -> {
            try {
                System.out.println("Cliente " + cliente + ": iniciando reserva para Vehículo " +
                        vehiculo.getMarca() + " " + vehiculo.getModelo());

                // Crear reserva
                Reserva reserva = new Reserva();
                reserva.setCliente(cliente);
                reserva.setFechaReserva(LocalDateTime.now());
                reserva.setVehiculo(vehiculo);
                reservaRepository.save(reserva);

                System.out.println("Reserva CREADA para cliente: " + cliente +
                        "; Vehículo ID " + vehiculo.getId() + " " + vehiculo.getMarca() + " " + vehiculo.getModelo());

            } catch (Exception e) {
                System.err.println("ERROR al procesar reserva para cliente: " + cliente);
                e.printStackTrace();
            }
        });
    }


    @Scheduled(fixedRate = 5000)
    public void generarReservaAutomatica() {
        if (!reservasAutomaticasActivas) {
            return;
        }

        if (contadorReservas >= MAX_RESERVAS) {
            System.out.println("Se alcanzó el límite de reservas automáticas: " + MAX_RESERVAS);
            reservasAutomaticasActivas = false;
            return;
        }


        List<Vehiculo> vehiculos = vehiculoRepository.findAll();
        if (vehiculos.isEmpty()) {
            System.out.println("No hay vehículos registrados en la base de datos");
            return;
        }

        // Elegir vehículo aleatorio
        Vehiculo vehiculo = vehiculos.get(random.nextInt(vehiculos.size()));

        // Elegir nombre aleatorio de cliente
        String cliente = nombresClientes[random.nextInt(nombresClientes.length)];

        // Procesar reserva
        procesarReserva(cliente, vehiculo);

        contadorReservas++;
    }
}





