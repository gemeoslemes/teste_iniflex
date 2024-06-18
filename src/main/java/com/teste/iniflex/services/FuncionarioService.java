package com.teste.iniflex.services;

import com.teste.iniflex.controllers.FuncionarioController;
import com.teste.iniflex.exceptions.ResourceNotFoundException;
import com.teste.iniflex.model.funcionario.Funcionario;
import com.teste.iniflex.model.pessoa.Pessoa;
import com.teste.iniflex.records.FuncionarioDTO;
import com.teste.iniflex.records.FuncionarioVO;
import com.teste.iniflex.repositories.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository repository;

    @Autowired
    private PessoaService pessoaService;

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
}
