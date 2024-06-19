package com.teste.iniflex.services;

import com.teste.iniflex.controllers.FuncionarioController;
import com.teste.iniflex.exceptions.ResourceNotFoundException;
import com.teste.iniflex.exceptions.SalaryIncreaseAboveLimitException;
import com.teste.iniflex.model.funcionario.Funcionario;
import com.teste.iniflex.records.FuncionarioDTO;
import com.teste.iniflex.records.FuncionarioSalarioTotalVO;
import com.teste.iniflex.records.FuncionarioVO;
import com.teste.iniflex.records.IncreaseRequestDTO;
import com.teste.iniflex.repositories.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository repository;

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private PagedResourcesAssembler<FuncionarioVO> assembler;

    public FuncionarioVO salvar(FuncionarioDTO dto) {
        Funcionario funcionario = new Funcionario(dto);
        repository.save(funcionario);
        FuncionarioVO funcionarioVO = new FuncionarioVO(funcionario);

        funcionarioVO.add(linkTo(methodOn(FuncionarioController.class).findById(funcionario.getId())).withSelfRel());

        return funcionarioVO;
    }

    public FuncionarioVO findById(Long id) {
        Funcionario funcionario = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ID não encontrado!"));
        FuncionarioVO funcionarioVO = new FuncionarioVO(funcionario);

        funcionarioVO.add(linkTo(methodOn(FuncionarioController.class).findById(funcionario.getId())).withSelfRel());
        return funcionarioVO;
    }

    public void delete(Long id) {
        Funcionario funcionario = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ID não encontrado!"));
        repository.delete(funcionario);
    }

    public PagedModel<EntityModel<FuncionarioVO>> findAll(Pageable pageable) {
        var funcionario = repository.findAll(pageable);

        var funcionarioVOPage = funcionario.map(FuncionarioVO::new);

        funcionarioVOPage.map(
                f -> f.add(linkTo(methodOn(FuncionarioController.class).findById(f.getKey())).withSelfRel()));

        Link link = paginationLinkTo(pageable);

        return assembler.toModel(funcionarioVOPage, link);
    }

    public List<FuncionarioVO> updateAllSalaries(IncreaseRequestDTO request) {
        validateIncrease(request.increase());

        BigDecimal percentualAumento = request.increase().divide(BigDecimal.valueOf(100));
        List<Funcionario> pessoas = repository.findAll();

        pessoas.forEach(funcionario -> {
            BigDecimal salarioComAumento = calcularNovoSalario(funcionario.getSalario(), percentualAumento);
            funcionario.setSalario(salarioComAumento);
        });

        repository.saveAll(pessoas);

        return pessoas.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    public Map<String, List<FuncionarioVO>> groupByFunctionVO() {
        List<Funcionario> funcionarios = repository.findAll();
        Map<String, List<FuncionarioVO>> funcionariosPorFuncao = new HashMap<>();

        for (Funcionario funcionario : funcionarios) {
            String funcao = funcionario.getFuncao();
            FuncionarioVO funcionarioVO = convertToVO(funcionario);

            if (funcionariosPorFuncao.containsKey(funcao)) {
                funcionariosPorFuncao.get(funcao).add(funcionarioVO);
            } else {
                List<FuncionarioVO> funcinariosDaFuncao = new ArrayList<>();
                funcinariosDaFuncao.add(funcionarioVO);
                funcionariosPorFuncao.put(funcao, funcinariosDaFuncao);
            }
        }
        return funcionariosPorFuncao;
    }

    public PagedModel<EntityModel<FuncionarioVO>> findEmployeesWithBirthdaysInOctoberAndDecember(Pageable pageable) {
        Page<Funcionario> funcionariosPage = repository.findAll(pageable);

        List<FuncionarioVO> funcionariosAniversarioOutubroDezembro = filterByBirthdayMonths(
                funcionariosPage, Month.OCTOBER, Month.DECEMBER);

        addSelfRelLinks(funcionariosAniversarioOutubroDezembro);

        Link link = paginationLinkTo(pageable);

        return assembler.toModel(new PageImpl<>(funcionariosAniversarioOutubroDezembro,
                pageable, funcionariosPage.getTotalElements()), link);
    }

    public Map<String, Object> findEmployeeWithOldestAge() {
        List<Funcionario> funcionarios = repository.findAll();

        Funcionario funcionarioMaisVelho = null;
        int idadeMaisVelho = -1;

        for (Funcionario funcionario : funcionarios) {
            int idade = calcularIdade(funcionario.getDataNascimento());

            if (idade > idadeMaisVelho) {
                idadeMaisVelho = idade;
                funcionarioMaisVelho = funcionario;
            }
        }

        if (funcionarioMaisVelho != null) {
            Map<String, Object> result = new HashMap<>();
            result.put("nome", funcionarioMaisVelho.getNome());
            result.put("idade", idadeMaisVelho);

            return result;
        } else {
            throw new ResourceNotFoundException("Nenhum funcionário encontrado.");
        }
    }

    public FuncionarioSalarioTotalVO calculateGlobalSalaryForEmployees() {
        List<Funcionario> funcionarios = repository.findAll();

        if (funcionarios.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum funcionário encontrado.");
        }

        BigDecimal salarioTotal = BigDecimal.ZERO;

        for (Funcionario funcionario : funcionarios) {
            salarioTotal = salarioTotal.add(funcionario.getSalario());
        }

        FuncionarioSalarioTotalVO globalSalarioVO = new FuncionarioSalarioTotalVO();

        globalSalarioVO.setNome("salary global");
        globalSalarioVO.setSalario(salarioTotal);

        globalSalarioVO.add(linkTo(methodOn(FuncionarioController.class)
                .calculateGlobalSalaryForEmployees()).withSelfRel());

        return globalSalarioVO;
    }

    public static int calcularIdade(LocalDate dataNascimento) {
        LocalDate hoje = LocalDate.now();
        return Period.between(dataNascimento, hoje).getYears();
    }

    private void addSelfRelLinks(List<FuncionarioVO> funcionariosVO) {
        funcionariosVO.forEach(funcionarioVO -> {
            funcionarioVO.add(linkTo(methodOn(FuncionarioController.class).findById(funcionarioVO.getKey())).withSelfRel());
        });
    }

    public List<FuncionarioVO> filterByBirthdayMonths(Page<Funcionario> funcionariosPage, Month... meses) {
        return funcionariosPage.getContent().stream()
                .filter(funcionario -> {
                    Month mesAniversario = funcionario.getDataNascimento().getMonth();
                    return Arrays.asList(meses).contains(mesAniversario);
                })
                .map(FuncionarioVO::new)
                .collect(Collectors.toList());
    }

    public Link paginationLinkTo(Pageable pageable) {
        return linkTo(methodOn(FuncionarioController.class)
                .findAll(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        "asc"
                )).withSelfRel();
    }

    public BigDecimal calcularNovoSalario(BigDecimal salarioAtual, BigDecimal percentualAumento) {
        BigDecimal aumento = salarioAtual.multiply(percentualAumento);
        return salarioAtual.add(aumento);
    }

    public FuncionarioVO convertToVO(Funcionario funcionario) {
        FuncionarioVO funcionarioVO = new FuncionarioVO();
        funcionarioVO.setKey(funcionario.getId());
        funcionarioVO.setNome(funcionario.getNome());
        funcionarioVO.setSalario(funcionario.getSalario());
        funcionarioVO.setFuncao(funcionario.getFuncao());
        funcionarioVO.setDataNascimento(funcionario.getDataNascimento());
        funcionarioVO.add(linkTo(methodOn(FuncionarioController.class).findById(funcionario.getId())).withSelfRel());
        return funcionarioVO;
    }

    public void validateIncrease(BigDecimal increase) {
        if (increase.compareTo(BigDecimal.ZERO) <= 0) {
            throw new SalaryIncreaseAboveLimitException("O valor de aumento deve ser maior que zero!");
        }

        BigDecimal percentualAumento = increase.divide(BigDecimal.valueOf(100));
        if (percentualAumento.compareTo(BigDecimal.valueOf(0.1)) > 0) {
            throw new SalaryIncreaseAboveLimitException("O aumento salarial não pode exceder 10% do valor atual do salário!");
        }
    }
}
