package com.example.application.data;

import com.example.application.data.Keikat;
import com.example.application.data.KeikatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/keikat")
public class KeikatController {

    private final KeikatRepository keikatRepository;

    public KeikatController(KeikatRepository keikatRepository) {
        this.keikatRepository = keikatRepository;
    }

    @GetMapping
    public List<Keikat> getAll() {
        return keikatRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Keikat> getById(@PathVariable Long id) {
        return keikatRepository.findById(id);
    }

    @PostMapping
    public Keikat create(@RequestBody Keikat keikka) {
        try{
            return keikatRepository.save(keikka);

        }catch (Exception e){
            return  null;
        }
    }

    @PutMapping("/{id}")
    public Keikat update(@PathVariable Long id, @RequestBody Keikat updatedKeikka) {
        updatedKeikka.setId(id);
        return keikatRepository.save(updatedKeikka);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        keikatRepository.deleteById(id);
    }
}