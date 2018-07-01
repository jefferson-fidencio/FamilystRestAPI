/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api.resources;

import api.model.Usuario;
import database.DAO.UsuarioDAO;
import interfaces.api.resources.IUsuariosPorEmailResources;
import java.net.URI;
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
/*import net.sargue.mailgun.Configuration;
import net.sargue.mailgun.Mail;*/

class SMTPAuthenticator extends javax.mail.Authenticator {
        @Override
        public PasswordAuthentication getPasswordAuthentication() {
            String username =  "jdfidencio@gmail.com";           // specify your email id here (sender's email id)
            String password = "infante08sempre";                                      // specify your password here
            return new PasswordAuthentication(username, password);
        }
  }

/**
 *
 * @author jdfid
 */
@Path("usuariosporemail")
public class UsuariosPorEmailResources implements IUsuariosPorEmailResources {
    
    @GET
    @Path("{emailUsuario}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Override
    public Response buscarUsuario(@PathParam("emailUsuario") String emailUsuario) {
        try {            
            Usuario usuario = new UsuarioDAO().buscarUsuarioPorEmail(emailUsuario);           
            return Response.status(Response.Status.OK).entity(usuario).build();               
        } catch (Exception e) {
            switch (e.getMessage())
            {
                //CODE 404
                case "Usuario nao encontrado.":
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
    public Response buscarUsuarioSenha(Usuario usuario) {
        try {            
            String senhaUsuario = new UsuarioDAO().buscarSenhaUsuarioPorEmail(usuario.getEmail());  
            enviarEmailSenha(usuario.getEmail(), senhaUsuario);
            
            //CODE 201
            return Response.created(new URI("/")).build();              
        } catch (Exception e) {
            switch (e.getMessage())
            {
                //CODE 404
                case "Usuario nao encontrado.":
                    return Response.status(Response.Status.NOT_FOUND).build();
                //CODE 500
                default:
                    return Response.serverError().entity(e.getMessage()).build();
            }
        }
    }     

    private void enviarEmailSenha(String email, String senhaUsuario) throws MessagingException {
        Properties props = System.getProperties();
        // -- Attaching to default Session, or we could start a new one --
        props.put("mail.transport.protocol", "smtp" );
        props.put("mail.smtp.starttls.enable","true" );
        props.put("mail.smtp.host","smtp.gmail.com");
        props.put("mail.smtp.auth", "true" );
        SMTPAuthenticator auth = new SMTPAuthenticator();
        Session session = Session.getInstance(props, auth);
        // -- Create a new message --
        Message msg = new MimeMessage(session);
        // -- Set the FROM and TO fields --
        msg.setFrom(new InternetAddress("jdfidencio@gmail.com"));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email, false));
        msg.setSubject("Sua senha Familyst");
        msg.setText("Senha Familyst: " + senhaUsuario);
        // -- Set some other header information --
        msg.setHeader("Familyst", "Familyst" );
        msg.setSentDate(new Date());
        // -- Send the message --
        Transport.send(msg);
    }
}
