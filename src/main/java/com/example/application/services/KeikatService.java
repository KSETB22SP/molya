package com.example.application.services;

import com.example.application.data.Keikat;
import com.example.application.data.KeikatRepository;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class KeikatService {

    private final KeikatRepository repository;

    public KeikatService(KeikatRepository repository) {
        this.repository = repository;
    }

    public Optional<Keikat> get(Long id) {
        return repository.findById(id);
    }

    public Keikat save(Keikat entity) {
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<Keikat> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Keikat> list(Pageable pageable, Specification<Keikat> filter) {
        return repository.findAll(filter, pageable);
    }
    public Page<Keikat> list(Specification<Keikat> spec, Pageable pageable){
        return repository.findAll(spec, pageable);
    }

    public int count() {
        return (int) repository.count();
    }

}
