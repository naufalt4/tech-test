package tech.test.konsumenservice.entity;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Konsumen{
    private Long id;
    private String nama;
    private String alamat;
    private String kota;
    private String provinsi;
    private Date tanggalRegistrasi;
    private String status;
}