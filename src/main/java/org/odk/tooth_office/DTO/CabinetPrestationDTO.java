package org.odk.tooth_office.DTO;

public record CabinetPrestationDTO(
        Long id,
        double prix,
        String description,
        Integer cabinetId,
        String nomCabinet,
        Long prestationId,
        String nomPrestation
) {
}
