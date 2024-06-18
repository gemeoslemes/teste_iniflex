package com.teste.iniflex.services;

import com.teste.iniflex.model.pessoa.Pessoa;
import com.teste.iniflex.records.FuncionarioDTO;
import com.teste.iniflex.repositories.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository repository;

    public Pessoa salvarPessoa(FuncionarioDTO dto) {
        Pessoa pessoa = new Pessoa(dto);
        return repository.save(pessoa);
    }
}
