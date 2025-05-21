package io.github.jiangdequan;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.FullStack_Eva.model.Evaluacion;

@Repository
public interface EvaluacionRepository extends JpaRepository<Evaluacion, Integer> {
        // Aquí puedes agregar métodos personalizados si es necesario
}
    
