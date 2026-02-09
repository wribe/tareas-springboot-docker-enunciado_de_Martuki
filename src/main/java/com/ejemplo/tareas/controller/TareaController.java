package com.ejemplo.tareas.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ejemplo.tareas.model.Tarea;
import com.ejemplo.tareas.repository.TareaRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/tareas")
@RequiredArgsConstructor
@Tag(name = "Tareas", description = "API para gestión de tareas/notas")
public class TareaController {

    private final TareaRepository tareaRepository;

    @GetMapping
    @Operation(summary = "Obtener todas las tareas", description = "Retorna la lista completa de tareas o filtrada por estado")
    @ApiResponse(responseCode = "200", description = "Lista de tareas obtenida exitosamente")
    public ResponseEntity<List<Tarea>> obtenerTodas(
            @Parameter(description = "Filtrar por estado: true=completadas, false=pendientes")
            @RequestParam(required = false) Boolean completada) {
        
        List<Tarea> tareas;
        if (completada != null) {
            tareas = tareaRepository.findByCompletada(completada);
        } else {
            tareas = tareaRepository.findAll();
        }
        return ResponseEntity.ok(tareas);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una tarea por ID", description = "Retorna una tarea específica según su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tarea encontrada"),
        @ApiResponse(responseCode = "404", description = "Tarea no encontrada")
    })
    public ResponseEntity<Tarea> obtenerPorId(
            @Parameter(description = "ID de la tarea") @PathVariable Long id) {
        
        return tareaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar tareas por título", description = "Busca tareas que contengan el texto en el título")
    @ApiResponse(responseCode = "200", description = "Búsqueda realizada exitosamente")
    public ResponseEntity<List<Tarea>> buscarPorTitulo(
            @Parameter(description = "Texto a buscar en el título") @RequestParam String titulo) {
        
        List<Tarea> tareas = tareaRepository.findByTituloContainingIgnoreCase(titulo);
        return ResponseEntity.ok(tareas);
    }

    @PostMapping
    @Operation(summary = "Crear una nueva tarea", description = "Crea una nueva tarea con los datos proporcionados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Tarea creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    public ResponseEntity<Tarea> crear(
            @Parameter(description = "Datos de la tarea a crear") @Valid @RequestBody Tarea tarea) {
        
        tarea.setId(null); // Asegurar que se cree una nueva
        tarea.setCompletada(false);
        Tarea tareaGuardada = tareaRepository.save(tarea);
        return ResponseEntity.status(HttpStatus.CREATED).body(tareaGuardada);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una tarea", description = "Actualiza los datos de una tarea existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tarea actualizada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Tarea no encontrada"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    public ResponseEntity<Tarea> actualizar(
            @Parameter(description = "ID de la tarea") @PathVariable Long id,
            @Parameter(description = "Datos actualizados de la tarea") @Valid @RequestBody Tarea tarea) {
        
        return tareaRepository.findById(id)
                .map(tareaExistente -> {
                    tareaExistente.setTitulo(tarea.getTitulo());
                    tareaExistente.setDescripcion(tarea.getDescripcion());
                    tareaExistente.setCompletada(tarea.isCompletada());
                    Tarea tareaActualizada = tareaRepository.save(tareaExistente);
                    return ResponseEntity.ok(tareaActualizada);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una tarea", description = "Elimina una tarea según su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Tarea eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Tarea no encontrada")
    })
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID de la tarea a eliminar") @PathVariable Long id) {
        
        return tareaRepository.findById(id)
                .map(tarea -> {
                    tareaRepository.delete(tarea);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/completar")
    @Operation(summary = "Marcar tarea como completada", description = "Cambia el estado de la tarea a completada")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tarea marcada como completada"),
        @ApiResponse(responseCode = "404", description = "Tarea no encontrada")
    })
    public ResponseEntity<Tarea> completar(
            @Parameter(description = "ID de la tarea") @PathVariable Long id) {
        
        return tareaRepository.findById(id)
                .map(tarea -> {
                    tarea.setCompletada(true);
                    Tarea tareaActualizada = tareaRepository.save(tarea);
                    return ResponseEntity.ok(tareaActualizada);
                })
                .orElse(ResponseEntity.notFound().build());
    }

}
