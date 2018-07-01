/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api.resources;

import api.model.Foto;
import database.DAO.FotoDAO;
import interfaces.api.resources.IAlbumFotosResources;
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
@Path("albuns/{idAlbum}/fotos")
public class AlbumFotosResources implements IAlbumFotosResources {
 
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON}) 
    @Override
    public Response buscarFotosAlbum(@PathParam("idAlbum") Integer idAlbum) {
        try {            
            List<Foto> fotos = new FotoDAO().listarFotosAlbum(idAlbum);
            GenericEntity<List<Foto>> fotosResponse = new GenericEntity<List<Foto>>(fotos){};
            return Response.status(Response.Status.OK).entity(fotosResponse).build();                
        } catch (Exception e) {
            switch (e.getMessage())
            {
                //CODE 404
                case "Album nao encontrado.":
                    return Response.status(Response.Status.NOT_FOUND).build();
                //CODE 500
                default:
                    return Response.serverError().entity(e.getMessage()).build();
            }
        }
    }
}
