package br.com.fatec.catalogo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
public class UsuarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @NotBlank(message = "O nome de usuário é obrigatório")
    @Size(min = 3, max = 50, message = "O nome de usuário deve ter entre 3 e 50 caracteres")
    @Column(nullable = false, unique = true)
    private String username;

    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 5, message = "A senha deve ter no mínimo 5 caracteres")
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role; // "USER" ou "ADMIN"

    @Column(nullable = false)
    private LocalDateTime dataCadastro;

    public UsuarioModel() {
        this.dataCadastro = LocalDateTime.now();
    }

    // Getters e Setters

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
}