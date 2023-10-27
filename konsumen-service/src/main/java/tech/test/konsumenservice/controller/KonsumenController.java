package tech.test.konsumenservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import tech.test.konsumenservice.entity.Konsumen;
import tech.test.konsumenservice.entity.ResponseAPI;
import tech.test.konsumenservice.service.KonsumenService;

@Controller
public class KonsumenController {

    @Autowired
    private KonsumenService konsumenService;

    @GetMapping("/konsumen")
    public ModelAndView getKonsumen(
        @RequestParam(value = "page", required = false) String page,
        @RequestParam(value = "size", required = false) String size,
        @RequestParam(value = "nama", required = false) String nama,
        @RequestParam(value = "alamat", required = false) String alamat,
        @RequestParam(value = "kota", required = false) String kota,
        @RequestParam(value = "provinsi", required = false) String provinsi,
        @RequestParam(value = "tanggal", required = false) String tanggal,
        @RequestParam(value = "status", required = false) String status,
        @RequestParam(value = "message", required = false) String message) {
        
        ModelAndView modelAndView = new ModelAndView();
        
        Konsumen konsumenFilters = new Konsumen(null, nama, alamat, kota, provinsi, null, status);

        ResponseEntity<ResponseAPI> response = konsumenService.get(page, size, konsumenFilters);
        List<Konsumen> konsumen = response.getBody().getData();
        String currentPage = response.getHeaders().get("X_Paging_Page").get(0);
        String sizePage = response.getHeaders().get("X_Paging_Page_Size").get(0);
        String totalPage = response.getHeaders().get("X_Paging_Total_Page").get(0);
        String totalData = response.getHeaders().get("X_Paging_Total_Data").get(0);

        modelAndView.addObject("konsumen", konsumen);
        modelAndView.addObject("page", currentPage);
        modelAndView.addObject("sizePage", sizePage);
        modelAndView.addObject("totalPage", totalPage);
        modelAndView.addObject("totalData", totalData);
        modelAndView.addObject("message", message);

        modelAndView.setViewName("konsumen/index");
        return modelAndView;
    }

    @GetMapping("/konsumen/add")
    public ModelAndView addKonsumen() {
        ModelAndView modelAndView = new ModelAndView();
        
        Konsumen konsumen = new Konsumen();
        modelAndView.addObject("konsumen", konsumen);

        modelAndView.setViewName("konsumen/add");
        return modelAndView;
    }
    
    @PostMapping("/konsumen/save")
    public ModelAndView saveKonsumen(Konsumen konsumen, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        
        String saved = konsumenService.save(konsumen);

        modelAndView.setViewName("redirect:/konsumen?message=" + saved);
        return modelAndView;
    }

    @GetMapping("/konsumen/edit/{id}")
    public ModelAndView editKonsumen(@PathVariable("id") int id) {
        ModelAndView modelAndView = new ModelAndView();
        
        Konsumen konsumen = konsumenService.findById(id);
        modelAndView.addObject("konsumen", konsumen);
        modelAndView.addObject("konsumenId", id);

        modelAndView.setViewName("konsumen/edit");
        return modelAndView;
    }

    @PostMapping("/konsumen/update/{id}")
    public ModelAndView updateKonsumen(@PathVariable("id") int id, @ModelAttribute("konsumen") Konsumen konsumen) {
        ModelAndView modelAndView = new ModelAndView();
        
        String updated = konsumenService.update(id, konsumen);

        modelAndView.setViewName("redirect:/konsumen?message=" + updated);
        return modelAndView;
    }

    @GetMapping("/konsumen/delete/{id}")
    public ModelAndView removeKonsumen(@PathVariable("id") int id) {
        ModelAndView modelAndView = new ModelAndView();
        
        String deleted = konsumenService.delete(id);

        modelAndView.setViewName("redirect:/konsumen?message=" + deleted);
        return modelAndView;
    }
}
