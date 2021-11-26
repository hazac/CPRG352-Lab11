package services;

import dataaccess.UserDB;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.User;

public class AccountService {
    
    public User login(String email, String password, String path) {
        UserDB userDB = new UserDB();
        
        try {
            User user = userDB.get(email);
            if (password.equals(user.getPassword())) {
                Logger.getLogger(AccountService.class.getName()).log(Level.INFO, "Successful login by {0}", email);
                //simple text email
                //GmailService.sendMail(email, "New login to Notes App", "User has logged in", false);   //sending a message plain text Must do some security changes to the gmail account
                
                //templeted email uses the first sendMail method in the GmailService
                String to = user.getEmail();
                String subject = "Notes App Login";
                String template = path + "/emailtemplates/login.html";
                
                HashMap<String, String> tags = new HashMap<>();
                tags.put("firstname", user.getFirstName());
                tags.put("lastname", user.getLastName());
                tags.put("date", (new java.util.Date()).toString());
                
                GmailService.sendMail(to, subject, template, tags);

                return user;
            }
        } catch (Exception e) {
        }
        
        return null;
    }
    
    public boolean resetPassword(String email, String path, String url){  

        UserDB udb = new UserDB();
        try{
        User user = udb.get(email);
        if(user != null){
            String uuid = UUID.randomUUID().toString();
            user.setResetPasswordUuid(uuid);
            
            String to = email;
            String subject = "Reset Password";
            String template = path + "/emailtemplates/resetpassword.html";
            String link = url + "?uuid=" + uuid;
            
            HashMap<String, String> tags = new HashMap<>();
            tags.put("firstname", user.getFirstName());
            tags.put("lastname", user.getLastName());
            tags.put("link", link);
            
            GmailService.sendMail(to, subject, template, tags);
            
            return true;
        }
        }
        catch(Exception e){
            return false;
        }
        return false;
    }
    
    public boolean changePassword(String uuid, String password) {
       UserDB userDB = new UserDB();
       System.out.println("inside as.changePassword");
        try {
            User user = userDB.getByUUID(uuid);
            System.out.println("inside as.changePassword try");
            user.setPassword(password);
            user.setResetPasswordUuid(null);
            userDB.update(user);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

}
