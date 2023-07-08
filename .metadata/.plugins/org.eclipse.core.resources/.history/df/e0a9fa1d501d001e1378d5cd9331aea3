package hn.clinica.data.service;

import hn.clinica.data.entity.Pacientes;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class PacientesService {

    private final PacientesRepository repository;

    public PacientesService(PacientesRepository repository) {
        this.repository = repository;
    }

    public Optional<Pacientes> get(Long id) {
        return repository.findById(id);
    }

    public Pacientes update(Pacientes entity) {
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<Pacientes> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Pacientes> list(Pageable pageable, Specification<Pacientes> filter) {
        return repository.findAll(filter, pageable);
    }

    public int count() {
        return (int) repository.count();
    }

}
