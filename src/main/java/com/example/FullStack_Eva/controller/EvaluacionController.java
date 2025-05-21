package io.github.jiangdequan;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
    @RequestMapping("/api/v1/evaluaciones")

public class EvaluacionController {

    @Autowired EvaluacionService evaluacionService;
    @GetMapping
    public ResponseEntity<List<Evaluacion>> listar() {
        List<Evaluacion> evaluaciones = evaluacionService.obtenerTodas();
        if (evaluaciones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(evaluaciones);
    }
    @PostMapping
    public ResponseEntity<Evaluacion> guardar(@RequestBody Evaluacion evaluacion) {
        Evaluacion evaluacionNueva = evaluacionService.guardar(evaluacion);
        return ResponseEntity.status(HttpStatus.CREATED).body(evaluacionNueva);
    }
    
    private final EvaluacionService evaluacionService;

    public EvaluacionController(EvaluacionService evaluacionService) {
        this.evaluacionService = evaluacionService;
    }

    @GetMapping
    public List<Evaluacion> getAllEvaluaciones() {
        return evaluacionService.obtenerTodas();
    }
    
}