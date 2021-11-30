package dataaccess;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import models.User;


public class UserDB {
    public User get(String email) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            User user = em.find(User.class, email);
            return user;
        } finally {
            em.close();
        }
    }
    
    public User getByUUID(String uuid){
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        System.out.println("inside userDB.getByUUID");
        try {
            TypedQuery<User> query = em.createNamedQuery("User.findByResetPasswordUuid", User.class);
            query.setParameter("resetPasswordUuid", uuid);
            User user = query.getSingleResult();
            //User user = em.createNamedQuery("User.findByResetPasswordUuid", User.class).setParameter("resetPasswordUuid", uuid).getSingleResult();
            System.out.println(user.getFirstName());
            return user;
        } 
        catch(Exception e){
            System.out.println("Here is the error: " + e.getMessage());
            return null;
        }finally {
            em.close();
        }
        
    }
    
    public void update(User user){
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        trans.begin();
        try{
            em.merge(user);
            trans.commit();
        }
        catch(Exception e){
            trans.rollback();
        }
        finally{
            em.close();
        } 
    }
}
