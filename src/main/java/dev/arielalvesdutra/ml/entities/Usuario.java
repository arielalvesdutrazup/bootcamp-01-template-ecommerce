package dev.arielalvesdutra.ml.entities;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.OffsetDateTime;
import java.util.*;


@Entity
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Email
    private String login;
    @NotBlank
    @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres!")
    private String senha;
    private OffsetDateTime cadastradoEm = OffsetDateTime.now();

    @JoinTable(name ="usuario_perfil",
            inverseJoinColumns = @JoinColumn(name = "perfil_id", referencedColumnName = "id"),
            joinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id"))
    @ManyToMany
    private List<Perfil> perfis = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<Produto> produtos;
    @OneToMany(mappedBy = "usuario", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<ProdutoPergunta> perguntas;
    @OneToMany(mappedBy = "usuario", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<ProdutoOpiniao> opinioes;

    public Usuario() {
    }

    public Long getId() {
        return id;
    }

    public Usuario setId(Long id) {
        this.id = id;
        return this;
    }

    public String getLogin() {
        return login;
    }

    public Usuario setLogin(String login) {
        this.login = login;
        return this;
    }

    public String getSenha() {
        return senha;
    }

    public Usuario setSenha(String senha) {
        this.senha = senha;
        return this;
    }

    public OffsetDateTime getCadastradoEm() {
        return cadastradoEm;
    }

    public Usuario setCadastradoEm(OffsetDateTime cadastradoEm) {
        this.cadastradoEm = cadastradoEm;
        return this;
    }

    public Set<Produto> getProdutos() {
        return produtos;
    }

    public Usuario setProdutos(Set<Produto> produtos) {
        this.produtos = produtos;
        return this;
    }

    public Set<ProdutoPergunta> getPerguntas() {
        return perguntas;
    }

    public Usuario setPerguntas(Set<ProdutoPergunta> perguntas) {
        this.perguntas = perguntas;
        return this;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.perfis;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", senha='" + senha + '\'' +
                ", cadastradoEm=" + cadastradoEm +
                '}';
    }
}
