CREATE TABLE funcionario (
    id BIGINT PRIMARY KEY,
    salario DECIMAL(10, 2) NOT NULL,
    funcao VARCHAR(255) NOT NULL,
    FOREIGN KEY (id) REFERENCES pessoa(id)
);