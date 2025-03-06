package com.NomadNook.NomadNook.Controller;



import com.NomadNook.NomadNook.DTO.REQUEST.CaracteristicaRequest;
import com.NomadNook.NomadNook.DTO.REQUEST.CategoriaRequest;
import com.NomadNook.NomadNook.DTO.RESPONSE.CaracteristicaResponse;
import com.NomadNook.NomadNook.DTO.RESPONSE.CategoriaResponse;
import com.NomadNook.NomadNook.Model.Categoria;
import com.NomadNook.NomadNook.Service.Impl.CaracteristicaService;
import com.NomadNook.NomadNook.Service.Impl.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categorias")


@RequiredArgsConstructor
public class CategoriaController {
    @Autowired
    private final CategoriaService categoriaService;


    @GetMapping("/listarTodos")
    public ResponseEntity<List<CategoriaResponse>> getAll() {
        return ResponseEntity.ok(categoriaService.listAllCategoria());
    }

    @PostMapping("/guardar")
    public ResponseEntity<CategoriaResponse> create(@Valid @RequestBody CategoriaRequest categoria) {
        return ResponseEntity.ok(categoriaService.createCategoria(categoria));
    }

    @GetMapping("/buscar/alojamiento/{id}")
    public ResponseEntity<List<CategoriaResponse>> getByAlojamientoId(@PathVariable Long id) {
        return ResponseEntity.ok(categoriaService.listAllCategoriaByAlojamiento(id));
    }
}
