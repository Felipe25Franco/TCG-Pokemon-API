import com.example.SCHs2j17.exception.RegraNegocioException;
import com.example.SCHs2j17.model.entity.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SerieService {
    private SerieRepository repository;

    public SerieService(SerieRepository repository) {
        this.repository = repository;
    }

    public List<Serie> getSeries() {
        return repository.findAll();
    }

    public Optional<Serie> getSerieById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Serie salvar(Serie serie) {
        validar(serie);
        return repository.save(serie);
    }

    @Transactional
    public void excluir(Serie serie) {
        Objects.requireNonNull(serie.getId());
        repository.delete(serie);
    }


    public void validar(Serie serie) {

    }
}