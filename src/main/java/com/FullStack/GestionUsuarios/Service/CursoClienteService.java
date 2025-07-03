package com.FullStack.GestionUsuarios.Service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.FullStack.GestionUsuarios.Model.CursoDTO;

@Service
public class CursoClienteService {

    @Autowired
    private RestTemplate restTemplate;

    public List<CursoDTO> listarCursos() {
        CursoDTO[] cursos = restTemplate.getForObject(
            "http://gestion-cursos:8081/api/cursos", CursoDTO[].class);
        return Arrays.asList(cursos);
    }

    public CursoDTO obtenerCursoPorId(Long id) {
        return restTemplate.getForObject(
            "http://gestion-cursos:8081/api/cursos/" + id, CursoDTO.class);
    }
}