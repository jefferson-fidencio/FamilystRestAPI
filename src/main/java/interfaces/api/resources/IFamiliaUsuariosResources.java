/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces.api.resources;

import api.model.Usuario;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

public interface IFamiliaUsuariosResources {
    public Response buscarUsuariosFamilia(@PathParam("idFamilia") Integer idFamilia);
    public Response inserirUsuarioFamilia(@PathParam("idFamilia") Integer idFamilia, Usuario usuario);
    public Response removerUsuarioFamilia(@PathParam("idFamilia") Integer idFamilia, @PathParam("idUsuario") Integer idUsuario);
}
