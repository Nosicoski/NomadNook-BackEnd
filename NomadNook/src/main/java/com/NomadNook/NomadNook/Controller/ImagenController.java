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
    public ResponseEntity<String> handleFileUpload(
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String name,
            @RequestParam("alojamiento") Long alojamiento_id) {

        try {
            // Validate file
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("Please select a file to upload");
            }

            // Upload to S3
            String fileUrl = s3Service.uploadFile(file);

            Imagen imagen = new Imagen();
            Alojamiento alojamiento = new Alojamiento();
            alojamiento.setId(alojamiento_id);
            imagen.setAlojamiento(alojamiento);
            imagen.setUrl(fileUrl);
            imagenService.createImagen(imagen);

            return ResponseEntity.ok("File uploaded successfully. URL: " + fileUrl);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to upload file: " + e.getMessage());
        }
    }
}
