package com.teste.iniflex.controllers;

import com.teste.iniflex.records.*;
import com.teste.iniflex.services.FuncionarioService;
import com.teste.iniflex.utils.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Funcionários", description = "Endpoints para gerenciamento de Funcionários.")
public class FuncionarioController {

    @Autowired
    private FuncionarioService service;

    @PostMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML},
                consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(summary = "Criando Funcinário", description = "Criando Funcinário",
            tags = {"Funcionários"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "201", content =
                        @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = FuncionarioVO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Not Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity<FuncionarioVO> create(@RequestBody FuncionarioDTO dto) {
        FuncionarioVO detalhamentoDTO = service.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(detalhamentoDTO);
    }

    @GetMapping(value = "/{id}",
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(summary = "Buscando Funcionário", description = "Buscando Funcionário",
            tags = {"Funcionários"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content =
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = FuncionarioVO.class)
                    )),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Not Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity<FuncionarioVO> findById(@PathVariable(value = "id") Long id) {
        FuncionarioVO vo = service.findById(id);
        return ResponseEntity.ok(vo);
    }

    @DeleteMapping(value = "/{id}",
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(summary = "Deletando Funcinário", description = "Deletando Funcinário",
            tags = {"Funcionários"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Not Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(summary = "Buscando todos os Funcinários", description = "Buscando todos os Funcinários",
            tags = {"Funcionários"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content =
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = FuncionarioVO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Not Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity<PagedModel<EntityModel<FuncionarioVO>>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ) {
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "nome"));
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @PutMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML},
                consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(summary = "Atualizando salário de todos os Funcinários", description = "Atualizando salário de todos os Funcinários",
            tags = {"Funcionários"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content =
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = FuncionarioVO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Not Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity<List<FuncionarioVO>> updateAllSalaries(@RequestBody IncreaseRequestDTO increase) {
        return ResponseEntity.ok(service.updateAllSalaries(increase));
    }

    @GetMapping(value = "/funcao",
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(summary = "Agrupando Funcinários por função", description = "Agrupando Funcinários por função",
            tags = {"Funcionários"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content =
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = FuncionarioVO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Not Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity<Map<String, List<FuncionarioVO>>> employeesByFunction() {
        return ResponseEntity.ok(service.groupByFunctionVO());
    }

    @GetMapping(value = "/aniversarios",
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(summary = "Trazendo todos os Funcinários que fazem aniversário nos meses 10 e 12",
            description = "Trazendo todos os Funcinários que fazem aniversário nos meses 10 e 12",
            tags = {"Funcionários"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content =
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = FuncionarioVO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Not Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity<PagedModel<EntityModel<FuncionarioVO>>> findEmployeesWithBirthdaysInOctoberAndDecember(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ) {
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "nome"));
        return ResponseEntity.ok(service.findEmployeesWithBirthdaysInOctoberAndDecember(pageable));
    }

    @GetMapping(value = "/maior-idade",
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(summary = "Trazendo o Funcinário com a maior idade",
            description = "Trazendo o Funcinário com a maior idade",
            tags = {"Funcionários"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content =
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = FuncionarioVO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Not Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity<Map<String, Object>> findEmployeeWithOldestAge() {
        return ResponseEntity.ok(service.findEmployeeWithOldestAge());
    }

    @GetMapping(value = "/ordem-alfabetica",
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(summary = "Trazendo todos os Funcinários em ordem alfabética",
            description = "Trazendo todos os Funcinários em ordem alfabética",
            tags = {"Funcionários"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content =
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = FuncionarioVO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Not Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity<PagedModel<EntityModel<FuncionarioVO>>> findEmployeesInAlphabeticOrder(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ) {
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "nome"));
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @GetMapping(value = "/salario-total",
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(summary = "Trazendo a soma do salário total de todos os Funcionários",
            description = "Trazendo a soma do salário total de todos os Funcionários",
            tags = {"Funcionários"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content =
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = FuncionarioVO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Not Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity<FuncionarioSalarioTotalVO>  calculateGlobalSalaryForEmployees() {
        return ResponseEntity.ok(service.calculateGlobalSalaryForEmployees());
    }

    @GetMapping(value = "/salario-minimo",
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(summary = "Calculando quantos salários mínimos cada Funcionário possui",
            description = "Calculando quantos salários mínimos cada Funcionário possui",
            tags = {"Funcionários"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content =
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = FuncionarioVO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Not Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity<PagedModel<EntityModel<FuncionarioSalarioMinimoVO>>> calculateIndividualMinimumSalaries(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ) {
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), "nome"));
        return ResponseEntity.ok(service.calculateIndividualMinimumSalaries(pageable));
    }
}
