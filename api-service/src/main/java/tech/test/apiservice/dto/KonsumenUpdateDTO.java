package tech.test.apiservice.dto;

import java.sql.Timestamp;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class KonsumenUpdateDTO {
     private Long id;
    
    @Size(max = 50, message = "{api.error.konsumen.nama.maximum}")
    private String nama;

    private String alamat;

    @Size(max = 50, message = "{api.error.konsumen.kota.maximum}")
    private String kota;

    @Size(max = 50, message = "{api.error.konsumen.provinsi.maximum}")
    private String provinsi;

    private Timestamp tanggalRegistrasi;

    private String status;
}
