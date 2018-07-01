/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api.resources;

import api.model.Comentario;
import database.DAO.ComentarioDAO;
import interfaces.api.resources.INoticiaComentariosResources;
import java.util.List;
import javax.ws.rs.GET;
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
@Path("noticias/{idNoticia}/comentarios")
public class NoticiaComentariosResources implements INoticiaComentariosResources {
 
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON}) 
    @Override
    public Response buscarComentariosNoticia(@PathParam("idNoticia") Integer idNoticia) {
        try {            
            List<Comentario> comentarios = new ComentarioDAO().listarComentariosNoticia(idNoticia);
            GenericEntity<List<Comentario>> comentariosResponse = new GenericEntity<List<Comentario>>(comentarios){};
            return Response.status(Response.Status.OK).entity(comentariosResponse).build();                
        } catch (Exception e) {
            switch (e.getMessage())
            {
                //CODE 404
                case "Noticia nao encontrada.":
                    return Response.status(Response.Status.NOT_FOUND).build();
                //CODE 500
                default:
                    return Response.serverError().entity(e.getMessage()).build();
            }
        }
    }
}
