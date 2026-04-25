package br.com.fatec.catalogo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

@Entity
@Table(name = "tb_categoria")
public class CategoriaModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idCategoria;

    @NotBlank(message = "O nome da categoria e obrigatorio")
    @Size(min = 3, max = 100, message = "O nome da categoria deve ter entre 3 e 100 caracteres")
    @Column(nullable = false, unique = true)
    private String nome;

    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
