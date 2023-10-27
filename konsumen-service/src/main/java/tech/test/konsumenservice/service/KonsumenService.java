package tech.test.konsumenservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import tech.test.konsumenservice.config.DataResourceConfiguration;
import tech.test.konsumenservice.entity.Konsumen;
import tech.test.konsumenservice.entity.ResponseAPI;

@Service
public class KonsumenService {
    
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    DataResourceConfiguration dataResource;

	public ResponseEntity<ResponseAPI> get(String page, String size, Konsumen konsumen) {
		String params = "";
		if(page != null){
			size = (size == null) ? dataResource.getDefaultPageSize() : size;
			params = "page=" + page 
				+ "&size=" + size
				+ "&nama=" + konsumen.getNama()
				+ "&alamat=" + konsumen.getAlamat()
				+ "&kota=" + konsumen.getKota()
				+ "&provinsi=" + konsumen.getProvinsi()
				+ "&tanggal=" + ""
				+ "&status=" + konsumen.getStatus();
		}

		ResponseEntity<ResponseAPI> response = restTemplate.exchange(dataResource.getApiUrl() + "/consumers?" + params, HttpMethod.GET, null, ResponseAPI.class);
        
		return response;
	}

	public String save(Konsumen konsumen) {
        HttpEntity<Konsumen> request = new HttpEntity<Konsumen>(konsumen);
		ResponseEntity<ResponseAPI> response = restTemplate.exchange(dataResource.getApiUrl() + "/consumers", HttpMethod.POST, request, ResponseAPI.class);

		return response.getBody().getMessages().get(0);
	}

	public Konsumen findById(int id) {
		ResponseEntity<ResponseAPI> response = restTemplate.exchange(dataResource.getApiUrl() + "/consumers/" + id, HttpMethod.GET, null, ResponseAPI.class);

        Konsumen konsumen = response.getBody().getData().get(0);
        return konsumen;
	}

	public String update(int id, Konsumen konsumen) {
		HttpEntity<Konsumen> request = new HttpEntity<Konsumen>(konsumen);
		ResponseEntity<ResponseAPI> response = restTemplate.exchange(dataResource.getApiUrl() + "/consumers/" + id, HttpMethod.PUT, request, ResponseAPI.class);

		return response.getBody().getMessages().get(0);
	}

	public String delete(int id) {
		ResponseEntity<ResponseAPI> response = restTemplate.exchange(dataResource.getApiUrl() + "/consumers/" + id, HttpMethod.DELETE, null, ResponseAPI.class);

		return response.getBody().getMessages().get(0);
    }
}
