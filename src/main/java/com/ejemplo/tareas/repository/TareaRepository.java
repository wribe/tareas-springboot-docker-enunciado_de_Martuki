package com.ejemplo.tareas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ejemplo.tareas.model.Tarea;

@Repository
public interface TareaRepository extends JpaRepository<Tarea, Long> {

    /**
     * Busca tareas por estado de completado
     */
    List<Tarea> findByCompletada(boolean completada);

    /**
     * Busca tareas que contengan el texto en el título (ignorando mayúsculas)
     */
    List<Tarea> findByTituloContainingIgnoreCase(String titulo);

}
