package com.tomcat.hosting.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import com.tomcat.hosting.dao.User;
import com.tomcat.hosting.dao.UserSession;
import com.tomcat.hosting.dao.UserSessionPK;
import com.tomcat.hosting.utils.STypes;

public class UserService {
	@Inject
	private Provider<EntityManager> entityManager;

	public List<User> getAllUsers()
    {
    	return entityManager.get().createNamedQuery("User.findAll", User.class).getResultList();
    }
	
	public void insertUserSession (String userId, String sessionId) {
		UserSessionPK pk = new UserSessionPK();
		pk.setSessionId(sessionId);
		pk.setUserId(userId);
		
		UserSession uSession = entityManager.get().find(UserSession.class, pk);
		if (null == uSession) {
			uSession = new UserSession();
			uSession.setId(pk);
		}
		
		uSession.setLoginTime(new Timestamp(System.currentTimeMillis()));
		uSession.setIpAddress(STypes.getIpAddress());
	    saveOrUpdate(uSession);
	}
	
	public List<UserSession> findUserSessionByUserId(String userId)
    {
			return entityManager.get()
	    			.createNamedQuery("UserSession.findByUserId", UserSession.class)
	    			.setParameter("userId", userId)
	    			.getResultList();
		
    }
	
	public void addUser(String userId, String password ){
		User user = new User();
		user.setUserId(userId);
		user.setPassword(STypes.SHA512Encypter(password));
		user.setEmailAddress(userId);
		user.setUserRole(1);
		user.setActivationCode(0);
		user.setStatus(1);
		saveOrUpdate(user);
	} 
	
	public void changePassword(String uid, String pw)
    {
		try{
			User user = entityManager.get().find(User.class, uid);
			user.setPassword(STypes.SHA512Encypter(pw));
			saveOrUpdate(user);
		}catch (Exception ex){
			
		}
    }
	
	public User getUserById(String userName)
    {
		TypedQuery<User> query = entityManager.get()
	    			.createNamedQuery("User.findByUserId", User.class)
	    			.setParameter("id", userName);;
		return ExamService.getSingleResult(query);
    }
	
	
	public User getUserByIdAndPassword(String userName, String password)
    {
		TypedQuery<User> query =  entityManager.get()
    			.createNamedQuery("User.findByUserPassword", User.class)
    			.setParameter("id", userName)
    			.setParameter("password", STypes.SHA512Encypter(password));
			return ExamService.getSingleResult(query);
    }
	public User getUserByEmailAndPassword(String email, String password)
    {
		TypedQuery<User> query =  entityManager.get()
	    			.createNamedQuery("User.findByEmailPassword", User.class)
	    			.setParameter("eaddress", email)
	    			.setParameter("password", STypes.SHA512Encypter(password));	
		return ExamService.getSingleResult(query);
    }
	
	public int getAllEmail(String email)
    {		 
		try{
			Query query = entityManager.get().createNativeQuery("select count(*) from users where email_address = :eaddress");
			query.setParameter("eaddress", email);
			Object emails = query.getSingleResult();
			return Integer.parseInt(emails.toString());
		}catch(NoResultException ex){
			return -1;
		}    	
    }
	
	public int getAllUserId(String userId)//check if there is any user_id in db which is equals to given userId
    {		 
		try{
			Query query = entityManager.get().createNativeQuery("select count(*) from users where user_id = :userid");
			query.setParameter("userid", userId);
			Object ids = query.getSingleResult();
			return Integer.parseInt(ids.toString());
		}catch(NoResultException ex){
			return -1;
		}    	
    }

	/**
	 * get the user from DB before calling this method to update
	 * otherwise, it will throw SQL Exception    
	 * @param e
	 */
    @Transactional (rollbackOn = Exception.class)
    public void saveOrUpdate(Object e)
    {
    	try {
    	entityManager.get().persist(e);
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
    }
        
    @Transactional (rollbackOn = Exception.class)
    public void delete(Object entity) throws Exception
    {
    	EntityManager em = entityManager.get();
    	em.remove(em.contains(entity) ? entity : em.merge(entity));
    }
    
    @Transactional (rollbackOn = Exception.class)
    public int updateByQuery(String query, Map<String,Object> params) throws Exception
    {
    	int updatecount = 0;
		Query q = entityManager.get().createQuery (query);
		 if (params!=null && !params.isEmpty()) {
            Set<String> keySet = params.keySet();
            for (String key : keySet) {
                q.setParameter(key, params.get(key));
            }
        }
		updatecount = q.executeUpdate();
    	return updatecount;
    }
    
}
