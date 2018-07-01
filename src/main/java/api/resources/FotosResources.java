/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api.resources;

import api.model.Foto;
import database.DAO.FotoDAO;
import interfaces.api.resources.IFotosResources;
import java.net.URI;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author jdfid
 */
@Path("fotos")
public class FotosResources implements IFotosResources{

    /**
     * Creates a new instance of FotosResources
     */
    public FotosResources() {
    }
    
    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Override
    public Response inserirFoto(Foto foto){
        try {
            int idFoto = new FotoDAO().inserirFoto(foto);
            URI insertUri = new URI("/" + idFoto);
            
            //CODE 201
            return Response.created(insertUri).build();
        } catch (Exception e) {
            switch (e.getMessage())
            {
                //CODE 500
                default:
                    return Response.serverError().entity(e.getMessage()).build();
            }
        }        
    }
    
    @PUT
    @Path("{idFoto}")
    @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    @Override
    public Response editarFoto(@PathParam("idFoto") Integer idFoto, Foto foto) {     
        try {
            new FotoDAO().editarFoto(idFoto, foto);    
            return Response.status(Response.Status.OK).build();        
        } catch (Exception e) {
            switch (e.getMessage())
            {
                //CODE 404
                case "Foto nao encontrada.":
                    return Response.status(Response.Status.NOT_FOUND).build();
                //CODE 204
                case "Foto nao alterada.":
                    return Response.status(Response.Status.NO_CONTENT).build();
                //CODE 500
                default:
                    return Response.serverError().entity(e.getMessage()).build();
            }
        }
    }
        
    @DELETE
    @Path("{idFoto}")
    @Override
    public Response removerFoto(@PathParam("idFoto") Integer idFoto) {
        FotoDAO fotoDAO = new FotoDAO();
        try {
            fotoDAO.removerFoto(idFoto);     
            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
             switch (e.getMessage())
            {
                //CODE 404
                case "Foto nao encontrada.":
                    return Response.status(Response.Status.NOT_FOUND).build();
                //CODE 500
                default:
                    return Response.serverError().entity(e.getMessage()).build();
            }
        }
    }
    
}
