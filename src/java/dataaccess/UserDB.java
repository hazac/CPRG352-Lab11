package dataaccess;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
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
            User user = em.createNamedQuery("User.findByResetPasswordUuid", User.class).setParameter("resetPasswordUuid", uuid).getSingleResult();
            System.out.println(user.getFirstName());
            return user;
        } finally {
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
