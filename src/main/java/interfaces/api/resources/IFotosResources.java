/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces.api.resources;

import api.model.Foto;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

public interface IFotosResources {

    public Response inserirFoto(Foto foto);
    public Response editarFoto(@PathParam("idFoto") Integer idFoto, Foto foto);
    public Response removerFoto(@PathParam("idFoto") Integer idFoto);
    
}
