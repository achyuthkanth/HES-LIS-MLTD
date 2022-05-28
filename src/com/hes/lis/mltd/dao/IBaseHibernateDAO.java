package com.hes.lis.mltd.dao;

import org.hibernate.Session;

/**
 * 
 * @author psvr
 *
 */
public interface IBaseHibernateDAO {
	public Session getSession();
}