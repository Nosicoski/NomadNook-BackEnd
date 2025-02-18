package com.NomadNook.NomadNook.Controller;

import com.NomadNook.NomadNook.Model.Pago;
import com.NomadNook.NomadNook.Service.Impl.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pagos")

public class PagoController {

    private final PagoService pagoService;

    //Agregando servicio de pago
    @Autowired
    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }


    // CREA un nuevo pago

    @PostMapping("/guardar")
    public ResponseEntity<Pago> createPago(@RequestBody Pago pago) {
        Pago nuevoPago = pagoService.createPago(pago);
        return new ResponseEntity<>(nuevoPago, HttpStatus.CREATED);
    }



    // TRAE todos los pagos
    @GetMapping

    public ResponseEntity<List<Pago>> getAllPagos() {
        List<Pago> pagos = pagoService.getAllPagos();
        return new ResponseEntity<>(pagos, HttpStatus.OK);
    }

    // TRAE un pago por su ID

    @GetMapping("/{id}")
    public ResponseEntity<Pago> getPagoById(@PathVariable Long id) {
        Optional<Pago> pago = pagoService.getPagoById(id);
        if (pago.isPresent()) {
            return new ResponseEntity<>(pago.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    // ELIMINA un pago existente por ID

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePago(@PathVariable Long id) {
        if (pagoService.deletePago(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //ACTUALIZA un pago existente por ID
    @PutMapping("/{id}")
    public ResponseEntity<Pago> updatePago(@PathVariable Long id, @RequestBody Pago pago) {
        Optional<Pago> updatedPago = pagoService.updatePago(id, pago);
        if (updatedPago.isPresent()) {
            return ResponseEntity.ok(updatedPago.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


}
