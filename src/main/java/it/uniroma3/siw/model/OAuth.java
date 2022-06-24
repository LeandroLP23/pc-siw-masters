package it.uniroma3.siw.model;

import javax.persistence.*;

@Entity
public class OAuth {
    @Id
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String role;

    public static final String ADMIN_ROLE = "ADMIN";
    public static final String DEFAULT_ROLE = "USER";


    public OAuth(Long id, String nome) {
        this();
        this.id = id;
        this.nome = nome;
    }

    public OAuth() {
        this.role = DEFAULT_ROLE;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
