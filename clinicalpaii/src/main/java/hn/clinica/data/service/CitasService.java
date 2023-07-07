package hn.clinica.data.service;

import hn.clinica.data.entity.Citas;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class CitasService {

    private final CitasRepository repository;

    public CitasService(CitasRepository repository) {
        this.repository = repository;
    }

    public Optional<Citas> get(Long id) {
        return repository.findById(id);
    }

    public Citas update(Citas entity) {
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<Citas> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Citas> list(Pageable pageable, Specification<Citas> filter) {
        return repository.findAll(filter, pageable);
    }

    public int count() {
        return (int) repository.count();
    }

}
