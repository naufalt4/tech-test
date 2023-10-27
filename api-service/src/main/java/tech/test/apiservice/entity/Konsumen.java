package tech.test.apiservice.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "konsumen")
public class Konsumen{
    @Id
    @Column(name = "id")
    @SequenceGenerator(sequenceName="konsumen_seq", name="konsumen_seq", allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="konsumen_seq")
    private Long id;
    
    @Column(name = "nama")
    @NotNull(message = "api.error.konsumen.nama.required")
    @Size(max = 50, message = "api.error.konsumen.nama.maximum")
    private String nama;

    @Column(name = "alamat")
    @NotNull(message = "api.error.konsumen.alamat.required")
    private String alamat;

    @Column(name = "kota")
    @NotNull(message = "api.error.konsumen.kota.required")
    @Size(max = 50, message = "api.error.konsumen.kota.maximum")
    private String kota;

    @Column(name = "provinsi")
    @NotNull(message = "api.error.konsumen.provinsi.required")
    @Size(max = 50, message = "api.error.konsumen.provinsi.maximum")
    private String provinsi;

    @Column(name = "tgl_registrasi")
    private Timestamp tanggalRegistrasi;

    @Column(name = "status")
    @NotNull(message = "api.error.konsumen.status.required")
    private String status;
}