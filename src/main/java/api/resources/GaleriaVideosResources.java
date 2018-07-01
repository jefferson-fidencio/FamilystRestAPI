/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api.resources;

import api.model.Video;
import database.DAO.VideoDAO;
import interfaces.api.resources.IGaleriaVideosResources;
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
@Path("galerias/{idGaleria}/videos")
public class GaleriaVideosResources implements IGaleriaVideosResources {
 
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON}) 
    @Override
    public Response buscarVideosGaleria(@PathParam("idGaleria") Integer idGaleria) {
        try {            
            List<Video> videos = new VideoDAO().listarVideosGaleria(idGaleria);
            GenericEntity<List<Video>> videosResponse = new GenericEntity<List<Video>>(videos){};
            return Response.status(Response.Status.OK).entity(videosResponse).build();                
        } catch (Exception e) {
            switch (e.getMessage())
            {
                //CODE 404
                case "Galeria nao encontrada.":
                    return Response.status(Response.Status.NOT_FOUND).build();
                //CODE 500
                default:
                    return Response.serverError().entity(e.getMessage()).build();
            }
        }
    }
}
