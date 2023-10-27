package tech.test.apiservice.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import tech.test.apiservice.config.DataResourceConfiguration;
import tech.test.apiservice.dto.KonsumenUpdateDTO;
import tech.test.apiservice.dto.ResponseDTO;
import tech.test.apiservice.entity.Konsumen;
import tech.test.apiservice.handler.ResponseHandler;
import tech.test.apiservice.service.KonsumenService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
@RequestMapping("/api/consumers")
public class KonsumenController {

    @Autowired
    private KonsumenService konsumenService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private DataResourceConfiguration dataResource;

    @GetMapping
    public ResponseEntity<Object> searchKonsumen(
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "nama", required = false, defaultValue = "") String nama,
            @RequestParam(name = "alamat", required = false, defaultValue = "") String alamat,
            @RequestParam(name = "kota", required = false, defaultValue = "") String kota,
            @RequestParam(name = "provinsi", required = false, defaultValue = "") String provinsi,
            @RequestParam(name = "status", required = false, defaultValue = "") String status) {

        ResponseDTO response = new ResponseDTO();

        // Check page parameter
        if (size == null) {
            if (page == null) {
                page = 0;
                size = dataResource.getDefaultPageSize();
            } else {
                page -= 1;
                size = dataResource.getDefaultPageSize();
            }
        } else if (page == null) {
            page = 0;
            size = dataResource.getDefaultPageSize();
        } else {
            page -= 1;
        }

        nama = (nama == null || nama.equals("null")) ? "" : nama;
        alamat = (alamat == null || alamat.equals("null")) ? "" : alamat;
        kota = (kota == null || kota.equals("null")) ? "" : kota;
        provinsi = (provinsi == null || provinsi.equals("null")) ? "" : provinsi;
        status = (status == null || status.equals("null")) ? "" : status;
        
        Page<Konsumen> konsumen = null;
        Konsumen filterKonsumen = new Konsumen(null, nama, alamat, kota, provinsi, null, status);
        konsumen = konsumenService.search(filterKonsumen, page, size);

        response.setData(konsumen.getContent());
        response.getHeaders().set("X_Paging_Page", String.valueOf(konsumen.getNumber() + 1));
        response.getHeaders().set("X_Paging_Page_Size", String.valueOf(konsumen.getSize()));
        response.getHeaders().set("X_Paging_Total_Page", String.valueOf(konsumen.getTotalPages()));
        response.getHeaders().set("X_Paging_Total_Data", String.valueOf(konsumen.getTotalElements()));

        response.setMessage(messageSource.getMessage("api.response.konsumen.search.success", null, LocaleContextHolder.getLocale()));
        response.setStatus(HttpStatus.OK);

        return ResponseHandler.generateResponse(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findKonsumenById(@PathVariable(value = "id") long id) {
        ResponseDTO response = new ResponseDTO();
        List<Konsumen> konsumens = new ArrayList<>();

        Konsumen konsumen = konsumenService.findById(id);
        
        if (konsumen == null) {
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setMessage(messageSource.getMessage("api.error.konsumen.not.found", new Object[] { id }, LocaleContextHolder.getLocale()));
            return ResponseHandler.generateResponse(response);
        }

        konsumens.add(konsumen);
        response.setData(konsumens);
        response.setStatus(HttpStatus.OK);
        response.setMessage(messageSource.getMessage("api.response.konsumen.search.success", null, LocaleContextHolder.getLocale()));

        return ResponseHandler.generateResponse(response);
    }

    @PostMapping
    public ResponseEntity<Object> saveKonsumen(
            @RequestHeader Map<String, String> requestHeaders,
            @Valid @RequestBody Konsumen konsumen) {
        ResponseDTO response = new ResponseDTO();

        boolean saved = konsumenService.save(konsumen);
        
        if (!saved) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(messageSource.getMessage("api.error.internal.server.error", null, LocaleContextHolder.getLocale()));

            return ResponseHandler.generateResponse(response);
        }
        
        response.setMessage(messageSource.getMessage("api.response.konsumen.creation.success", null, LocaleContextHolder.getLocale()));
        response.setStatus(HttpStatus.CREATED);

        return ResponseHandler.generateResponse(response);
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateKonsumen(
            @PathVariable(value = "id") long id,
            @RequestHeader Map<String, String> requestHeaders,
            @RequestBody KonsumenUpdateDTO KonsumenUpdateDTO) {
        ResponseDTO response = new ResponseDTO();

        Konsumen currentKonsumen = konsumenService.findById(id);
        if (currentKonsumen == null) {
            response.setMessage(messageSource.getMessage("api.error.konsumen.not.found", new Object[] { id }, LocaleContextHolder.getLocale()));
            response.setStatus(HttpStatus.BAD_REQUEST);

            return ResponseHandler.generateResponse(response);
        }

        Konsumen konsumen = new Konsumen(id, KonsumenUpdateDTO.getNama(), KonsumenUpdateDTO.getAlamat(), KonsumenUpdateDTO.getKota(), KonsumenUpdateDTO.getProvinsi(), KonsumenUpdateDTO.getTanggalRegistrasi(), KonsumenUpdateDTO.getStatus());

        boolean updated = konsumenService.update(konsumen);
        if (!updated) {
            response.setMessage(messageSource.getMessage("api.error.internal.server.error", null, LocaleContextHolder.getLocale()));
            response.setStatus(HttpStatus.BAD_REQUEST);
   
            return ResponseHandler.generateResponse(response);
        }

        response.setMessage(messageSource.getMessage("api.response.konsumen.edit.success", null, LocaleContextHolder.getLocale()));
        response.setStatus(HttpStatus.OK);

        return ResponseHandler.generateResponse(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteKonsumen(
            @PathVariable(value = "id") long id,
            @RequestHeader Map<String, String> requestHeaders) {
        ResponseDTO response = new ResponseDTO();

        boolean deleted = konsumenService.delete(id);

        if (!deleted) {
            response.setMessage(messageSource.getMessage("api.error.konsumen.not.found", new Object[] { id }, LocaleContextHolder.getLocale()));
            response.setStatus(HttpStatus.BAD_REQUEST);

            return ResponseHandler.generateResponse(response);
        } 

        response.setMessage(messageSource.getMessage("api.response.konsumen.delete.success", null, LocaleContextHolder.getLocale()));
        response.setStatus(HttpStatus.OK);

        return ResponseHandler.generateResponse(response);
    }
}
