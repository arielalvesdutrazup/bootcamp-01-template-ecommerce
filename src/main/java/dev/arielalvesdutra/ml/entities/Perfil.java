package dev.arielalvesdutra.ml.entities;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Perfil implements GrantedAuthority {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @JoinTable(name ="usuario_perfil",
            joinColumns = @JoinColumn(name = "perfil_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id"))
    @ManyToMany
    private List<Usuario> usuarios = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public Perfil setId(Long id) {
        this.id = id;
        return this;
    }

    public String getNome() {
        return nome;
    }

    public Perfil setNome(String nome) {
        this.nome = nome;
        return this;
    }

    @Override
    public String getAuthority() {
        return nome;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public Perfil setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
        return this;
    }
}
