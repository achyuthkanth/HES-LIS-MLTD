package com.hes.lis.mltd.dao;

import org.hibernate.Session;


/**
 * 
 * @author psvr
 *
 */
public class BaseHibernateDAO implements IBaseHibernateDAO {
	
	public Session getSession() {
		return HibernateSessionFactory.getSession();
	}
	
//	public void closeSession() {
//		HibernateSessionFactory.closeSession();
//	}
	
}