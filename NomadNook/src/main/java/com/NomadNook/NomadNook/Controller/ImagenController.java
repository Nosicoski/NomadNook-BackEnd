package com.NomadNook.NomadNook.Controller;

import com.NomadNook.NomadNook.DTO.RESPONSE.AlojamientoResponse;
import com.NomadNook.NomadNook.DTO.RESPONSE.ImagenResponse;
import com.NomadNook.NomadNook.Model.Alojamiento;
import com.NomadNook.NomadNook.Model.Imagen;
import com.NomadNook.NomadNook.Service.IAlojamientoService;
import com.NomadNook.NomadNook.Service.IImagenService;
import com.NomadNook.NomadNook.Service.Impl.S3Service;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/api/imagenes")
public class ImagenController {

    private final IImagenService imagenService;
    private final IAlojamientoService alojamientoService;
    private final S3Service s3Service;

    public ImagenController(IImagenService imagenService, IAlojamientoService alojamientoService, S3Service s3Service) {
        this.imagenService = imagenService;
        this.alojamientoService = alojamientoService;
        this.s3Service = s3Service;
    }

    @GetMapping("/listarTodos")
    public ResponseEntity<List<ImagenResponse>> getAll() {
        return ResponseEntity.ok(imagenService.listAllImagen());
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<ImagenResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(imagenService.getImagenById(id));
    }

    @PostMapping("/guardar")
    public ResponseEntity<ImagenResponse> create(@RequestBody Imagen imagen) {
        return ResponseEntity.ok(imagenService.createImagen(imagen));
    }

    @PutMapping ("/actualizar/{id}")
    public ResponseEntity<ImagenResponse> update(@PathVariable Long id, @RequestBody Imagen imagen) {
        return ResponseEntity.ok(imagenService.updateImagen(id, imagen));
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        imagenService.deleteImagen(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar/alojamiento/{id}")
    public ResponseEntity<List<ImagenResponse>> getByAlojamientoId(@PathVariable Long id) {
        return ResponseEntity.ok(imagenService.listAllImagenesByAlojamiento(id));
    }

    @PostMapping("/upload")
    public ResponseEntity<List<String>> handleMultipleFileUpload(
            @RequestParam("files") MultipartFile[] files,
            @RequestParam("alojamiento") Long alojamiento_id) {

        if (files == null || files.length == 0) {
            return ResponseEntity.badRequest().body(Collections.singletonList("No files provided"));
        }

        List<String> uploadedFileUrls = new ArrayList<>();

        try {
            for (MultipartFile file : files) {
                if (file.isEmpty()) {
                    continue;
                }

                String fileUrl = s3Service.uploadFile(file);

                Imagen imagen = new Imagen();
                Alojamiento alojamiento = new Alojamiento();
                alojamiento.setId(alojamiento_id);
                imagen.setAlojamiento(alojamiento);
                imagen.setUrl(fileUrl);
                imagenService.createImagen(imagen);

                uploadedFileUrls.add(fileUrl);
            }

            if (uploadedFileUrls.isEmpty()) {
                return ResponseEntity.badRequest().body(Collections.singletonList("No valid files uploaded"));
            }

            return ResponseEntity.ok(uploadedFileUrls);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Collections.singletonList("Failed to upload files: " + e.getMessage()));
        }
    }

}
