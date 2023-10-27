package tech.test.apiservice.repository;

import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import tech.test.apiservice.entity.Konsumen;

public interface KonsumenRepository extends JpaRepository<Konsumen, Long> {
    Page<Konsumen> findAll(Pageable pageable);

    Page<Konsumen> findByNamaOrAlamatOrKotaOrProvinsiOrStatusIgnoreCaseContaining(
        String nama,
        String alamat,
        String kota,
        String provinsi,
        String status,
        Pageable pageable
    );

    Page<Konsumen> findByNamaIgnoreCaseContaining(
        String nama,
        Pageable pageable
    );
}
