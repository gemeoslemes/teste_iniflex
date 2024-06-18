package com.teste.iniflex.controllers;

import com.teste.iniflex.records.FuncionarioDTO;
import com.teste.iniflex.records.FuncionarioVO;
import com.teste.iniflex.services.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/funcionarios/v1")
public class FuncionarioController {

    @Autowired
    private FuncionarioService service;

    @PostMapping
    public ResponseEntity<FuncionarioVO> criar(@RequestBody FuncionarioDTO dto) {
        FuncionarioVO detalhamentoDTO = service.salvar(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(detalhamentoDTO);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<FuncionarioVO> findById(@PathVariable(value = "id") Long id) {
        FuncionarioVO vo = service.findById(id);
        return ResponseEntity.ok(vo);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
