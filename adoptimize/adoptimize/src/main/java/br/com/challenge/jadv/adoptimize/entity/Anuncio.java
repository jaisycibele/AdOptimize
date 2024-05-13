package br.com.challenge.jadv.adoptimize.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "tb_anuncio")
public class Anuncio {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAnuncio;
    
    @Column(name="titulo") 
    private String titulo; 
    
    @Column(name="ds_texto_anuncio")
    private String textoAnuncio;
    
    @Column(name="ds_url_anuncio")
    private String urlAnuncio;
    
    @Column(name="tipo_anuncio")
    private String tipoAnuncio;
    
    @Column(name="data_criacao_anuncio")
    private String dataCriacao;
    
    @Column(name="impressoes")
    private Long impressoes;
    
    @Column(name="qtd_cliques")
    private Long qtdCliques;
    
    @Column(name="custo_anuncio")
    private Double custoAnuncio;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "ID_USUARIO",
            referencedColumnName = "idUsuario",
            foreignKey = @ForeignKey(name = "FK_ANUNCIO_USUARIO")
    )
    private Usuario usuario; 

}
