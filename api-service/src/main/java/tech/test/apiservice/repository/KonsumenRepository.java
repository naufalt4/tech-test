package tech.test.apiservice.repository;

import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tech.test.apiservice.entity.Konsumen;

public interface KonsumenRepository extends JpaRepository<Konsumen, Long> {
    Page<Konsumen> findAll(Pageable pageable);

    @Query(value = "SELECT * " +
            "FROM konsumen " +
            "WHERE nama iLIKE %?1% " + 
            "AND alamat iLIKE %?2% " + 
            "AND kota iLIKE %?3% " + 
            "AND provinsi iLIKE %?4% " + 
            "AND status iLIKE %?5% ", nativeQuery = true)
    Page<Konsumen> search(
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
