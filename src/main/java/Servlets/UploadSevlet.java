/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Entities.User;
import com.mycompany.jetstreaming.labredes.Utils;

import com.sun.media.jfxmedia.logging.Logger;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;




/**
 *
 * @author paotoyav
 */
@WebServlet(name = "UploadSevlet", urlPatterns = {"/upload"})
public class UploadSevlet extends HttpServlet {

    public static final String UPLOAD_DIRECTORY = "C:\\Users\\paotoyav\\Documents\\NetBeansProjects\\jetstreaming-labredes-6\\src\\main\\webapp\\videos";

    //
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UploadSevlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UploadSevlet at " + request.getAttribute("message") + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (ServletFileUpload.isMultipartContent(request)) {
            try {
                Subject currentUser = SecurityUtils.getSubject();
                User u = User.find(currentUser.getPrincipal().toString());
                List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
                for (FileItem item : multiparts) {
                    if (!item.isFormField()) {
                        String name = new File(item.getName()).getName();
                        String ruta = UPLOAD_DIRECTORY + File.separator + currentUser.getPrincipal() + "-EOL-" + name;
                        item.write(new File(ruta));
                        generateThumbnail(ruta);
                       // u.addVideo("videos" + File.separator + currentUser.getPrincipal() + "-EOL-" + name);
                    }
                }
                request.setAttribute("message", "File uploaded correctly");
                request.setAttribute("playlist", u.getPathVideos());
                request.setAttribute("thumbNails", u.getPathThumbnails());
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("message", "File uploaded failed");
            }
        } else {
            request.setAttribute("message", "Sorry this servlet only handles file upload");
        }

        request.getRequestDispatcher("app-page.jsp").forward(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void generateThumbnail(String path) throws IOException, JCodecException {
        int frameNumber = 150;

        BufferedImage frame = FrameGrab.getFrame(new File( path ), frameNumber);
        String pathThumbnail = path.replaceAll(".mp4", ".png");
        System.out.println(">>");
        System.out.println( pathThumbnail );
        ImageIO.write(frame, "png", new File( pathThumbnail ) );
    }

}
