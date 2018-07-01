/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api.resources;

import api.model.Usuario;
import database.DAO.FamiliaDAO;
import database.DAO.UsuarioDAO;
import interfaces.api.resources.IFamiliaUsuariosResources;
import java.net.URI;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author jdfid
 */
@Path("familias/{idFamilia}/usuarios")
public class FamiliaUsuariosResources implements IFamiliaUsuariosResources {
 
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON}) 
    @Override
    public Response buscarUsuariosFamilia(@PathParam("idFamilia") Integer idFamilia) {
        try {            
            List<Usuario> usuarios = new FamiliaDAO().listarUsuariosFamilia(idFamilia);
            GenericEntity<List<Usuario>> usuariosResponse = new GenericEntity<List<Usuario>>(usuarios){};
            return Response.status(Response.Status.OK).entity(usuariosResponse).build();                
        } catch (Exception e) {
            switch (e.getMessage())
            {
                //CODE 404
                case "Familia nao encontrada.":
                    return Response.status(Response.Status.NOT_FOUND).build();
                //CODE 500
                default:
                    return Response.serverError().entity(e.getMessage()).build();
            }
        }
    }
    
    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Override
    public Response inserirUsuarioFamilia(@PathParam("idFamilia") Integer idFamilia, Usuario usuario){
        try {
            new FamiliaDAO().inserirUsuarioFamilia(usuario, idFamilia);
            
            //CODE 201
            return Response.created(new URI("/")).build();
            
        } catch (Exception e) {
            switch (e.getMessage())
            {
                //CODE 404
                case "Familia nao encontrada.":
                    return Response.status(Response.Status.NOT_FOUND).build();
                //CODE 500
                default:
                    return Response.serverError().entity(e.getMessage()).build();
            }
        }       
    }
    
    @DELETE
    @Path("{idUsuario}")
    @Override
    public Response removerUsuarioFamilia(@PathParam("idFamilia") Integer idFamilia, @PathParam("idUsuario") Integer idUsuario){
        try {
            new FamiliaDAO().removerUsuarioFamilia(idUsuario, idFamilia);
            return Response.status(Response.Status.OK).build();
            
        } catch (Exception e) {
            switch (e.getMessage())
            {
                //CODE 404
                case "Familia nao encontrada.":
                    return Response.status(Response.Status.NOT_FOUND).build();
                //CODE 500
                default:
                    return Response.serverError().entity(e.getMessage()).build();
            }
        }       
    }
}
