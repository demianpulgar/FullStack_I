package io.github.jiangdequan;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.FullStack_Eva.model.Evaluacion;
import com.example.FullStack_Eva.repository.EvaluacionRepository;
import java.util.List;

@Service
@Transactional
public class EvaluacionService {
    @Autowired
    private EvaluacionRepository evaluacionRepository;

    public List<Evaluacion> obtenerTodas() {
        return evaluacionRepository.findAll();
    }

    public Evaluacion guardar(Evaluacion evaluacion) {
        return evaluacionRepository.save(evaluacion);
    }

    public void eliminar(Integer id) {
        evaluacionRepository.deleteById(id);
    }
    public Evaluacion obtenerPorId(Integer id) {
        return evaluacionRepository.findById(id).orElse(null);
    }
    public Evaluacion actualizar(Evaluacion evaluacion) {
        return evaluacionRepository.save(evaluacion);
    }
    
}