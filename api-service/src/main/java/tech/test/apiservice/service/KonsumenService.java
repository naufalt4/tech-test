package tech.test.apiservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import tech.test.apiservice.entity.Konsumen;
import tech.test.apiservice.repository.KonsumenRepository;

@Service
public class KonsumenService {
    @Autowired
    private KonsumenRepository konsumenRepository;

    public Page<Konsumen> findAll(Integer page, Integer size) {
        Pageable paging = PageRequest.of(page, size);

        Page<Konsumen> konsumen = konsumenRepository.findAll(paging);
       
        return konsumen;
    }

    public Page<Konsumen> search(Konsumen konsumenFilter, Integer page, Integer size) {
        Pageable paging = PageRequest.of(page, size);

        Page<Konsumen> konsumen = konsumenRepository.search(
            konsumenFilter.getNama(), konsumenFilter.getAlamat(), konsumenFilter.getKota(), konsumenFilter.getProvinsi(),
            konsumenFilter.getStatus(), paging);
       
        return konsumen;
    }

    public Konsumen findById(long id) {
        boolean isExists = konsumenRepository.existsById(id);
        if (!isExists) {
           return null;
        }

        Konsumen konsumen = konsumenRepository.findById(id).get();

        return konsumen;
    }

    public boolean save(Konsumen konsumen) {
        Konsumen result = null;

        result = konsumenRepository.save(konsumen);
        if(result == null){
            return false;
        }

        return true;
    }

    public boolean update(Konsumen konsumen) {
        Optional<Konsumen> newData = konsumenRepository.findById(konsumen.getId()).map(currentKonsumen -> {
            if (konsumen.getNama() != null)
                currentKonsumen.setNama(konsumen.getNama());
            if (konsumen.getAlamat() != null)
                currentKonsumen.setAlamat(konsumen.getAlamat());
            if (konsumen.getKota() != null)
                currentKonsumen.setKota(konsumen.getKota());
            if (konsumen.getProvinsi() != null)
                currentKonsumen.setProvinsi(konsumen.getProvinsi());
            if (konsumen.getTanggalRegistrasi() != null)
                currentKonsumen.setTanggalRegistrasi(konsumen.getTanggalRegistrasi());
            if (konsumen.getStatus() != null)
                currentKonsumen.setStatus(konsumen.getStatus());
            
            return konsumenRepository.save(currentKonsumen);
        });

        return newData.isPresent();
    }

    public boolean delete(long id) {
        boolean exists = konsumenRepository.existsById(id);
        if (!exists) {
            return false;
        } 

        try{
            konsumenRepository.deleteById(id);
        }catch(Exception ex){
            return false;
        }

        return true;
    }
}
