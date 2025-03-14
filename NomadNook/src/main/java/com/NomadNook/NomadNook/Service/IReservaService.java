package com.NomadNook.NomadNook.Service;

import com.NomadNook.NomadNook.DTO.RESPONSE.ReservaResponse;
import com.NomadNook.NomadNook.Model.Reserva;


import java.util.List;

public interface IReservaService {
    ReservaResponse createReserva(Reserva reserva);
    ReservaResponse getReservaById(Long id);
    List<ReservaResponse> listAllReservas();
    ReservaResponse updateReserva(Long id, Reserva reserva);
    void deleteReserva(Long id);
}