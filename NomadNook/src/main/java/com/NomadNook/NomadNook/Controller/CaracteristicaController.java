package com.NomadNook.NomadNook.Controller;

import com.NomadNook.NomadNook.DTO.REQUEST.AlojamientoRequest;
import com.NomadNook.NomadNook.DTO.REQUEST.CaracteristicaRequest;
import com.NomadNook.NomadNook.DTO.RESPONSE.AlojamientoResponse;
import com.NomadNook.NomadNook.DTO.RESPONSE.CaracteristicaResponse;
import com.NomadNook.NomadNook.Model.Caracteristica;
import com.NomadNook.NomadNook.Service.Impl.CaracteristicaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/caracteristicas")

public class CaracteristicaController {

    @Autowired
    CaracteristicaService caracteristicaService;

    @GetMapping("/listarTodos")
    public ResponseEntity<List<CaracteristicaResponse>> getAll() {
        return ResponseEntity.ok(caracteristicaService.listAllCaracteristicas());
    }

    @PostMapping("/guardar")
    public ResponseEntity<CaracteristicaResponse> create(@Valid @RequestBody CaracteristicaRequest caracteristica) {
        return ResponseEntity.ok(caracteristicaService.createCaracteristica(caracteristica));
    }

    @GetMapping("/buscar/alojamiento/{id}")
    public ResponseEntity<List<CaracteristicaResponse>> getByAlojamientoId(@PathVariable Long id) {
        return ResponseEntity.ok(caracteristicaService.listAllCaracteristicasByAlojamiento(id));
    }
}
