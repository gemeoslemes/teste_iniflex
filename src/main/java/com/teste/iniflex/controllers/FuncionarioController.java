package com.teste.iniflex.controllers;

import com.teste.iniflex.records.FuncionarioDTO;
import com.teste.iniflex.records.FuncionarioVO;
import com.teste.iniflex.records.IncreaseRequestDTO;
import com.teste.iniflex.services.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/funcionarios/v1")
public class FuncionarioController {

    @Autowired
    private FuncionarioService service;

    @PostMapping
    public ResponseEntity<FuncionarioVO> create(@RequestBody FuncionarioDTO dto) {
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

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<FuncionarioVO>>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ) {
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "nome"));
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @PutMapping
    public ResponseEntity<List<FuncionarioVO>> updateAllSalaries(@RequestBody IncreaseRequestDTO increase) {
        return ResponseEntity.ok(service.updateAllSalaries(increase));
    }

    @GetMapping(value = "/funcionarios-por-funcao")
    public ResponseEntity<Map<String, List<FuncionarioVO>>> employeesByFunction() {
        return ResponseEntity.ok(service.groupByFunctionVO());
    }

    @GetMapping(value = "/aniversarios-outubro-dezembro")
    public ResponseEntity<PagedModel<EntityModel<FuncionarioVO>>> findEmployeesWithBirthdaysInOctoberAndDecember(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ) {
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "nome"));
        return ResponseEntity.ok(service.findEmployeesWithBirthdaysInOctoberAndDecember(pageable));
    }

    @GetMapping(value = "/funcionario-maior-idade")
    public ResponseEntity<Map<String, Object>> findEmployeeWithOldestAge() {
        return ResponseEntity.ok(service.findEmployeeWithOldestAge());
    }
}
