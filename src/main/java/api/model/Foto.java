/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api.model;

import java.util.Date;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jdfid
 */
@XmlRootElement
public class Foto {

    public Foto(int idFoto, String descricao, String dados, Date dataCriacao, int idAlbum) {
        this.idFoto = idFoto;
        this.descricao = descricao;
        this.dataCriacao = dataCriacao;
        this.dados = dados;
        this.idAlbum = idAlbum;
    }

    public Foto () {
        this.idFoto = 0;
        this.descricao = null;
        this.dataCriacao = null;
        this.dados = null;
        this.idAlbum = 0;
    }
    
    @XmlElement
    private final int idFoto;   
    
    @XmlElement
    private final String descricao;
    
    @XmlElement
    private final Date dataCriacao;
    
    @XmlElement
    private final String dados;
    
    @XmlElement
    private final int idAlbum;
    
    public int getIdFoto() {
        return idFoto;
    }

    public String getDescricao() {
        return descricao;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public String getDados() {
        return dados;
    }

    public int getIdAlbum() {
        return idAlbum;
    }  
}
