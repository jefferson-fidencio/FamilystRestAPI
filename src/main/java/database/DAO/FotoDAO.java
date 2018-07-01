/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.DAO;

import api.model.Foto;
import interfaces.database.DAO.IFotoDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jdfid
 */
public class FotoDAO implements IFotoDAO{
 
    /**
     * Nomes de colunas e PreparedStatements para execucao de querys
     */
    private final String colid = "idFoto";
    private final String coldescricao = "descricao";
    private final String coldataCriacao = "dataCriacao";
    private final String coldados = "dados";
    private final String colidalbum = "album_idalbum";
    private final String stmtBuscarFoto = "SELECT * FROM FOTO WHERE " + colid + " = ?";
    private final String stmtListarFotosAlbum = "SELECT * FROM FOTO WHERE " + colidalbum + " = ?";
    private final String stmtInserirFoto = "INSERT INTO FOTO (" + coldescricao + "," + coldataCriacao + "," + coldados + "," + colidalbum + ") VALUES (?,NOW(),?,?)";
    private final String stmtEditarFoto = "UPDATE FOTO SET " + coldescricao + " = ?," + coldados + " = ? WHERE " + colid + " = ?";
    private final String stmtRemoverFoto = "DELETE FROM FOTO WHERE " + colid + " = ?";
    
    /**
     * Classes utilizadas
     */
    Connection con;
    PreparedStatement stmt;
    
    @Override
    public List<Foto> listarFotosAlbum(int idAlbum) throws Exception {
        try {
            
            List<Foto> fotos = new ArrayList<>();            
            
            con = new ConnectionFactory().getConnection();
            stmt = con.prepareStatement(stmtListarFotosAlbum);
            stmt.setInt(1, idAlbum);
            stmt.execute();
            
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                Timestamp timestamp = rs.getTimestamp(coldataCriacao);
                java.util.Date dataCriacao = new java.util.Date(timestamp.getTime());
                Foto foto = new Foto(rs.getInt(colid), rs.getString(coldescricao), rs.getString(coldados), dataCriacao, rs.getInt(colidalbum));
                fotos.add(foto);
            }            
            
            return fotos;
        } catch (Exception e) {
            Logger.getLogger(FotoDAO.class.getName()).log(Level.SEVERE, null, e);
           throw e;
        } finally {
            try {
                stmt.close();
            } catch (Exception ex) {
            }
            try {
                con.close();
            } catch (Exception ex) {
            }
        }
    }

    @Override
    public int inserirFoto(Foto foto) throws Exception {
        try {        
            
            //insere foto
            con = new ConnectionFactory().getConnection();
            stmt = con.prepareStatement(stmtInserirFoto, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, foto.getDescricao());            
            stmt.setString(2, foto.getDados());         
            stmt.setInt(3, foto.getIdAlbum());     
            
            stmt.executeUpdate();
            ResultSet keys = stmt.getGeneratedKeys();
            keys.next();
            return keys.getInt(1);
            
        } catch (Exception e) {
            Logger.getLogger(FotoDAO.class.getName()).log(Level.SEVERE, null, e);
            throw e;
        } finally {
            try {
                stmt.close();
            } catch (Exception ex) {
            }
            try {
                con.close();
            } catch (Exception ex) {
            }
        }
    }

    @Override
    public void editarFoto(int idFoto, Foto foto) throws Exception {
        try {        
            
            //verifica se foto existe
            Foto fotoExistente = buscarFoto(idFoto);
            if ( fotoExistente == null)
                throw new Exception("Foto nao encontrada.");
            
            //verifica se foto teve algum dado alterado
            if (fotoExistente.getDescricao().equals(foto.getDescricao()) && 
                    fotoExistente.getDados().equals(foto.getDados()))
                throw new Exception("Foto nao alterada.");
            
            con = new ConnectionFactory().getConnection();
            stmt = con.prepareStatement(stmtEditarFoto);
            stmt.setString(1, foto.getDescricao());
            stmt.setString(2, foto.getDados());
            stmt.setInt(3, idFoto);
            stmt.executeUpdate();
            
        } catch (Exception e) {
            Logger.getLogger(FotoDAO.class.getName()).log(Level.SEVERE, null, e);
            throw e;
        } finally {
            try {
                stmt.close();
            } catch (Exception ex) {
            }
            try {
                con.close();
            } catch (Exception ex) {
            }
        }
    }
    
    private Foto buscarFoto(int idFoto) throws Exception {
         try {         
            
            Foto foto = null;
             
            con = new ConnectionFactory().getConnection();
            stmt = con.prepareStatement(stmtBuscarFoto);
            stmt.setInt(1, idFoto);
            stmt.execute();
            
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) { 
                Timestamp timestamp = rs.getTimestamp(coldataCriacao);
                java.util.Date dataCriacao = new java.util.Date(timestamp.getTime());                
                foto = new Foto(rs.getInt(colid), rs.getString(coldescricao), rs.getString(coldados), dataCriacao, rs.getInt(colidalbum));               
            }        
            
            if (foto == null)
                throw new Exception("Foto nao encontrada.");
            
            return foto;
        } catch (Exception e) {
            Logger.getLogger(FotoDAO.class.getName()).log(Level.SEVERE, null, e);
            throw e;
        } finally {
            try {
                stmt.close();
            } catch (Exception ex) {
            }
            try {
                con.close();
            } catch (Exception ex) {
            }
        }
    }

    @Override
    public void removerFoto(int idFoto) throws Exception {
        try {     
            
            //verifica se foto existe
            Foto fotoExistente = buscarFoto(idFoto);
            if ( fotoExistente == null)
                throw new Exception("Foto nao encontrada.");
            
            con = new ConnectionFactory().getConnection();
            stmt = con.prepareStatement(stmtRemoverFoto);
            stmt.setInt(1, idFoto);
            stmt.executeUpdate();
            
        } catch (Exception e) {
            Logger.getLogger(FotoDAO.class.getName()).log(Level.SEVERE, null, e);
            throw e;
        } finally {
            try {
                stmt.close();
            } catch (Exception ex) {
            }
            try {
                con.close();
            } catch (Exception ex) {
            }
        }
    }
    
}
