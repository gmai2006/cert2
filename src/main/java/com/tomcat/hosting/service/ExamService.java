package com.tomcat.hosting.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import com.tomcat.hosting.dao.Answer;
import com.tomcat.hosting.dao.Certification;
import com.tomcat.hosting.dao.Exam;
import com.tomcat.hosting.dao.ExamHistory;
import com.tomcat.hosting.dao.ExamHistoryPK;
import com.tomcat.hosting.dao.ExamView;
import com.tomcat.hosting.dao.Exhibit;
import com.tomcat.hosting.dao.Explanation;
import com.tomcat.hosting.dao.Objective;
import com.tomcat.hosting.dao.Question;
import com.tomcat.hosting.dao.QuestionHistory;
import com.tomcat.hosting.dao.QuestionHistoryPK;
import com.tomcat.hosting.dao.QuestionPK;
import com.tomcat.hosting.dao.User;
import com.tomcat.hosting.dao.Vendor;
import com.tomcat.hosting.utils.STypes;

public class ExamService {
	@Inject
	private Provider<EntityManager> entityManager;
//	@Inject
//	private EntityManager em;
	
	public static <T> T getSingleResult(Query query) {
	    query.setMaxResults(1);
	    List<T> list = query.getResultList();
	    if (list == null || list.isEmpty()) return null;
	    return list.get(0);
	}
	
	public static <T> T getSingleResult(TypedQuery<T> query) {
	    query.setMaxResults(1);
	    List<T> list = query.getResultList();
	    if (list == null || list.isEmpty()) return null;
	    return list.get(0);
	}
	
	public List<Vendor> getAllVendors()
    {
    	return entityManager.get().createNamedQuery("Vendor.findAll", Vendor.class).getResultList();
    }
	
	public List<Vendor> getTopVendors()
    {
    	return entityManager.get().createNamedQuery("Vendor.findVendorByRank", Vendor.class).setParameter("rank", 100).getResultList();
    }


	public List<Exam> getAllExams()
    {
    	return entityManager.get().createNamedQuery("Exam.findAll", Exam.class).getResultList();
    }

    public Vendor getVendorByName(String vendorName)
    {
    	return (Vendor)entityManager.get().createNamedQuery("Vendor.findVendorByName", Vendor.class).setParameter("name", vendorName).getSingleResult();
    }

    public Vendor getVendorByPageName(String pageName)
    {
    	return (Vendor)entityManager.get().createNamedQuery("Vendor.findVendorByPageName", Vendor.class).setParameter("name", pageName).getSingleResult();
    }
    
    public Certification getCertificationByName(String name)
    {
    	return (Certification)entityManager.get().createNamedQuery("Certification.findCertificationByName", Certification.class).setParameter("name", name).getSingleResult();
    }

    public Certification getCertificationByPageName(String name)
    {
    	return (Certification)entityManager.get().createNamedQuery("Certification.findCertificationByPageName", Certification.class).setParameter("name", name).getSingleResult();
    }

	public List<Exam> getExamByVendorId(int vendorId)
    {
    	return entityManager.get().createNamedQuery("Exam.findAllByVendorId", Exam.class).setParameter("id", vendorId).getResultList();
    }
	
	public List<ExamHistory> getExamHistoryByUserId(String userId)
    {
    	return entityManager.get()
    			.createNativeQuery("select * from EXAM_HISTORY where user_id = :userId")
    			.setParameter("userId", userId)
    			.getResultList();
    }
	
	public List<Exam> getExamByVendorIdByRank(int vendorId, int rank)
    {
    	return entityManager.get()
    			.createNamedQuery("Exam.findAllByVendorId", Exam.class)
    			.setParameter("id", vendorId)
    			.setParameter("rank", rank)
    			.getResultList();
    }

	public List<ExamView> getExamViewByVendorRankByRank(int vendorRank, int rank)
    {
    	return entityManager.get()
    			.createNamedQuery("ExamView.findAllByVendorRankByRank", ExamView.class)
    			.setParameter("r", vendorRank)
    			.setParameter("rank", rank)
    			.getResultList();
    }
	
	public List<ExamView> getTopExams()
    {
    	return getExamViewByVendorRankByRank(100, 100);
    }
	
    public List<Exam> getExamByPageName(String name)
    {
    	return entityManager.get().createNamedQuery("Exam.findExamByPageName", Exam.class).setParameter("name", name).getResultList();
    }
    
    public List<Exam> getExamByPage(String name, int pageNumber, int pageSize )//phuc
    {
    	Query query = entityManager.get()
    			.createQuery("SELECT e FROM Exam e where e.examName like :name or e.vendorName like :name");
//    	int pageNumber = 1;
//    	int pageSize = 10;
    	query.setParameter("name", "%" + name + "%");    	
//    	query.setFirstResult((pageNumber-1) * pageSize); 
    	query.setFirstResult(pageNumber * pageSize); 
    	query.setMaxResults(pageSize);
    	List <Exam> examList = query.getResultList();
    	return examList;
    	//return entityManager.get().createNamedQuery("Exam.findExamByPage", Exam.class).setParameter("name", name).getResultList();
    }
    
    public long getTotalCount(String name) {
    	Query queryTotal = entityManager.get().createQuery
    		    ("Select count(e) from Exam e where e.examName like :name or e.vendorName like :name");
    	queryTotal.setParameter("name", "%" + name + "%");
    	long countResult = STypes.parseLong(queryTotal.getSingleResult().toString());
    	return countResult;
    }
  
    public void insertExamHistory(HttpServletRequest req){
    	
    	ExamHistoryPK pk = new ExamHistoryPK();
    	pk.setExamName(req.getSession().getAttribute("examName").toString());
    	pk.setSessionId(req.getSession().getId());
    	pk.setUserId(((User)req.getSession().getAttribute("user")).getUserId());
    	ExamHistory eHistory = entityManager.get().find(ExamHistory.class, pk);
    	if (null == eHistory) {
	    	eHistory = new ExamHistory();
	    	eHistory.setId(pk);
	    	eHistory.setCreateTime(new Timestamp(System.currentTimeMillis()));
	    	saveOrUpdate(eHistory); 
    	}
    }
    
    public void insertQuestionHistory(HttpServletRequest req, String selectedAnswer, String result, int questionId){
	    int correct = 0;
	    String examName = req.getSession().getAttribute("examName").toString();
	    String userId = ((User)req.getSession().getAttribute("user")).getUserId();
	    QuestionHistoryPK pk = new QuestionHistoryPK();
	    pk.setExamName(examName);
	    pk.setQuestionId(questionId);
	    pk.setSessionId(req.getSession().getId());
	    pk.setUserId(userId);
	    QuestionHistory qHistory = new QuestionHistory();
	    qHistory.setId(pk);
	    correct = getResults(selectedAnswer, result);
	    qHistory.setCorrect(correct);
	    saveOrUpdate(qHistory);    
    }
    
    public ExamHistory findExamHistoryById(ExamHistoryPK pk) {
    	TypedQuery<ExamHistory> query = entityManager.get()
    			.createNamedQuery("ExamHistory.findExamHistoryId", ExamHistory.class)
    			.setParameter("examName", pk.getExamName())
    			.setParameter("sessionId", pk.getSessionId())
    			.setParameter("userId", pk.getUserId());
    	return getSingleResult(query);
    }
    
    public QuestionHistory findQuestionHistoryById(QuestionHistoryPK pk) {
    	return entityManager.get().find(QuestionHistory.class, pk);
    }
    
    public void updateQuestionHistory(HttpServletRequest req, int questionId, int correct){
    	String examName = req.getSession().getAttribute("examName").toString();
	    String userId = ((User)req.getSession().getAttribute("user")).getUserId();
	    QuestionHistoryPK pk = new QuestionHistoryPK();
	    pk.setExamName(examName);
	    pk.setQuestionId(questionId);
	    pk.setSessionId(req.getSession().getId());
	    pk.setUserId(userId);
	    try {
			QuestionHistory qHistory = entityManager.get().find(QuestionHistory.class, pk);
			qHistory.setCorrect(correct);
			saveOrUpdate(qHistory);
		} catch (Exception ex) {
			
		}
    }
    
    public static int getResults(String selected, String result){
    	if (selected.equals(result)) return 1;
    	else return 0;
    }
    
    public List<Object[]> getQuestionHistoriesByUser(String userId) {
	    Query query = entityManager.get()
	    		.createNativeQuery("SELECT exam_name, count(*), sum(correct), count(*)-sum(correct), session_id" 
				+ " FROM QUESTION_HISTORY where user_id = :userId group by exam_name, session_id");
	    
	    query.setParameter("userId", userId);    	
    	return  query.getResultList();   	
    }    
    
    public  Object[] getQuestionHistoryByUser(String userId, String examName, String sessionId) {
	    Query query = entityManager.get()
	    		.createNativeQuery("SELECT exam_name, count(*), sum(correct), count(*)-sum(correct), session_id" 
				+ " FROM QUESTION_HISTORY where user_id = :userId and exam_name = :examName and session_id = :sessionId");
	    
	    query.setParameter("userId", userId);  
	    query.setParameter("examName", examName);
	    query.setParameter("sessionId", sessionId);
	    System.out.println("input :" + userId + " " + examName + " " + sessionId);
	    return getSingleResult(query);  	
    }   
    
    public List<Object[]> getIncorrectQuestions(String userId, String examName) {
	    Query query = entityManager.get().createNativeQuery("SELECT e.order_number,e.text, q.exam_name, q.question_id from EXHIBIT e join QUESTION_HISTORY q " 
				+ " on e.question_id = q.question_id and e.exam_name = q.exam_name "
	    		+ " WHERE q.user_id =:userId and q.exam_name=:examName and correct = 0 order by q.question_id");
	    
	    query.setParameter("userId", userId);
	    query.setParameter("examName", examName);  
    	return query.getResultList(); 	
    }
    public List<Exhibit> getExhibitsByExamNameAndQuestion(String examName, int questionId)
    {
    	return 
    	entityManager.get()
    	.createNamedQuery("Exhibit.findbyExamNameAndQuestion", Exhibit.class)
    	.setParameter("examName", examName)
    	.setParameter("questionId", questionId)
    	.getResultList();

    }
    public List<Answer> getAnswersByExamNameAndQuestion(String examName, int questionId)
    {
    	return 
    	entityManager.get()
    	.createNamedQuery("Answer.findbyExamNameAndQuestion", Answer.class)
    	.setParameter("examName", examName)
    	.setParameter("questionId", questionId)
    	.getResultList();
    }
    public List<Explanation> getExplanationsByExamNameAndQuestion(String examName, int questionId)
    {
    	return 
    	entityManager.get()
    	.createNamedQuery("Explanation.findbyExamNameAndQuestion", Explanation.class)
    	.setParameter("examName", examName)
    	.setParameter("questionId", questionId)
    	.getResultList();
    }
    public List<Question> getQuestionsByExam(String examName)
    {
    	return 
    	entityManager.get()
    	.createNamedQuery("Question.findQuestionsByExamName", Question.class)
    	.setParameter("examName", examName)
    	.getResultList();

    }
    
    public List<Objective> getObjectivesByExamName(String examName)
    {
    	return 
    	entityManager.get()
    	.createNamedQuery("Objective.findbyExamName", Objective.class)
    	.setParameter("examName", examName)
    	.getResultList();

    }
    
    public Exam getExamByName(String name)
    {
    	TypedQuery<Exam> query = 
    	entityManager.get()
    	.createNamedQuery("Exam.findExamByName", Exam.class)
    	.setParameter("name", name);
    	return getSingleResult(query);
    }
    
    public Exam getExamByExamKey(String examKey)
    {
    	TypedQuery<Exam> query = 
    	entityManager.get()
    	.createNamedQuery("Exam.findExamByExamKey", Exam.class)
    	.setParameter("examKey", examKey);
    	return getSingleResult(query);
    }
    
    public Question getQuestionByExamAndId(QuestionPK id)
    {
    	TypedQuery<Question> query = 
    			entityManager.get()
    			.createNamedQuery("Question.findQuestionsByExamNameAndQuestionId", Question.class)
    			.setParameter("examName", id.getExamName())
    			.setParameter("questionId", id.getId());
    			return getSingleResult(query);
    }
    
    @Transactional (rollbackOn = Exception.class)
    public void saveOrUpdate(Object e)
    {
    	entityManager.get().persist(e);
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
