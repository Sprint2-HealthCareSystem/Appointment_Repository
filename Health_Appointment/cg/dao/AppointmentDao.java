package com.cg.dao;
import java.io.Console;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.cg.entity.Appointment;
import com.cg.entity.DiagnosticCenter;
import com.cg.entity.Test;
import com.cg.entity.User;

@Transactional
@Repository
public class AppointmentDao implements AppointmentDaoI{
	
	@PersistenceContext
	EntityManager entityManager;
	
	@Override
	public String addCenter(DiagnosticCenter diagnosticCenter)
	{
		List<Test> tests = new ArrayList<Test>();
		tests.add(new Test("blood group"));
		tests.add(new Test("blood sugar"));
		tests.add(new Test("blood pressure"));
        diagnosticCenter.setListOfTests(tests);	
		entityManager.persist(diagnosticCenter);
		return "center added successfully";
	}

	@Override
	public List<DiagnosticCenter> listOfDiagnosticCenters()
	{
		String queryStr = "select diagnosticCenter from DiagnosticCenter diagnosticCenter";
		TypedQuery<DiagnosticCenter> query = entityManager.createQuery(queryStr,DiagnosticCenter.class);
		return query.getResultList();
	}
	@Override
	public List<Test> listOfTests(String centerName)
	{
		String queryStr1="select diagnosticCenter from DiagnosticCenter diagnosticCenter where diagnosticCenter.centerName=:centerName";
		TypedQuery<DiagnosticCenter> query1=entityManager.createQuery(queryStr1,DiagnosticCenter.class);
		DiagnosticCenter diagnosticCenter = query1.setParameter("centerName", centerName).getSingleResult();
		System.out.println(diagnosticCenter.getListOfTests());
		return diagnosticCenter.getListOfTests();
	}
	@Override
	public String createUser(User user)
	{
		entityManager.persist(user);
		return "User created";
	}

	@Override
	public User findByUserId(int userId) {
		String queryStr = "select user from User user where user.userId=:userId";
		TypedQuery<User> query = entityManager.createQuery(queryStr,User.class);
		User user = query.setParameter("userId", userId).getSingleResult();
		return user;
	}

	@Override
	public String makeAppointment(Appointment appointment)
	{
		entityManager.persist(appointment);
		return "Appointment added successfully";
	}
	
	@Override
	public Appointment findByAppointmentId(int appointmentId) 
	{
		String queryStr = "select appointment from Appointment appointment where appointment.appointmentId=:appointmentId";
		TypedQuery<Appointment> query = entityManager.createQuery(queryStr,Appointment.class);
		Appointment appointment = query.setParameter("appointmentId", appointmentId).getSingleResult();
		return appointment;
	}

}
