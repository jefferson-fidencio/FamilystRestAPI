/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces.database.DAO;

import api.model.Foto;
import java.util.List;

/**
 *
 * @author jdfid
 */
public interface IFotoDAO {
        
    public List<Foto> listarFotosAlbum(int idAlbum) throws Exception;
    
    public int inserirFoto(Foto foto) throws Exception;
        
    public void editarFoto(int idFoto, Foto foto) throws Exception;
    
    public void removerFoto(int idFoto) throws Exception;
}
