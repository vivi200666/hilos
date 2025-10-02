package com.plataformas.hilos.repository;

import com.plataformas.hilos.model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Integer> {

    List<Vehiculo> findByEstadoTrue();
    List<Vehiculo> findByEstadoFalse();
    List<Vehiculo> findByMarca(String marca);
    List<Vehiculo> findByModelo(String modelo);
}
