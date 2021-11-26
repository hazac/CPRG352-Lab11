package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import services.AccountService;

public class ResetPasswordServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        if (uuid != null){
            request.setAttribute("uuid", uuid);
            getServletContext().getRequestDispatcher("/WEB-INF/resetNewPassword.jsp").forward(request, response);
            return;
        }
        else{
            getServletContext().getRequestDispatcher("/WEB-INF/reset.jsp").forward(request, response);
            return;
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AccountService as = new AccountService();
        String action = request.getParameter("action");
        System.out.println(action);
        if (action.equals("resetPassword")) {
            String email = request.getParameter("email");
            String path = getServletContext().getRealPath("/WEB-INF");
            String url = request.getRequestURL().toString();            
            if(as.resetPassword(email, path, url)){
                request.setAttribute("message", "Please check your inbox for an email with instructions");                
            }
        } 
        else if (action.equals("changePassword")) {
            String uuid = request.getParameter("uuid");
            System.out.println(action + " " + uuid);
            String password = request.getParameter("newPassword");
            System.out.println(password);
            if(as.changePassword(uuid, password)){
                request.setAttribute("message", "Password has been succesfully changed");                 
            }
        }
        getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
                return;
    }

}
