package com.cg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.dao.AppointmentDao;
import com.cg.dao.AppointmentDaoI;
import com.cg.entity.Appointment;
import com.cg.entity.DiagnosticCenter;
import com.cg.entity.Test;
import com.cg.entity.User;
import com.cg.exception.ResourceNotFoundException;

@Service
public class AppointmentService implements AppointmentServiceI {
	
	@Autowired
	AppointmentDao appoitnmentDao;

	@Override
	public String addCenter(DiagnosticCenter diagnosticCenter) 
	{
		return appoitnmentDao.addCenter(diagnosticCenter);
	}

	@Override
	public DiagnosticCenter findByCenterName(String centerName) throws ResourceNotFoundException 
	{
		List<DiagnosticCenter> listOfDiagnosticCenters = appoitnmentDao.listOfDiagnosticCenters();
		boolean isDiagnosticCentPresent = listOfDiagnosticCenters.stream().filter(center->center.getCenterName().equalsIgnoreCase(centerName)).findFirst().isPresent();
		if(!isDiagnosticCentPresent)
			throw new ResourceNotFoundException("Not found center "+centerName);		
	    DiagnosticCenter diagnosticCenter = listOfDiagnosticCenters.stream().filter(center->center.getCenterName().equalsIgnoreCase(centerName)).findFirst().get();
	    return diagnosticCenter;
	}

	@Override
	public Test findByTestName(DiagnosticCenter diagnosticCenter, String testName) throws ResourceNotFoundException 
	{
		boolean isTestPresent = diagnosticCenter.getListOfTests().stream().filter(t->testName.equalsIgnoreCase(t.getTestName())).findFirst().isPresent();
		if(!isTestPresent)
			throw new ResourceNotFoundException(testName+" test does not exist in the "+diagnosticCenter.getCenterName()+" center");
		Test test = diagnosticCenter.getListOfTests().stream().filter(t->testName.equalsIgnoreCase(t.getTestName())).findFirst().get();
		return test;
	}

	@Override
	public boolean isCenterNameExist(String centerName)
	{
		List<DiagnosticCenter> listOfDiagnosticCenters = appoitnmentDao.listOfDiagnosticCenters();
		if(!listOfDiagnosticCenters.stream().filter(center->center.getCenterName().equalsIgnoreCase(centerName)).findFirst().isPresent())
			return false;
		return true;
	}
	
	@Override
	public String createUser(User user)
	{
		return appoitnmentDao.createUser(user);
	}
	
	@Override
	public User findByUserId(int userId)
	{
		return appoitnmentDao.findByUserId(userId);
	}
	
	@Override
	public String makeAppointment(Appointment appointment)
	{
		return appoitnmentDao.makeAppointment(appointment);
	}
	
	@Override
	public Appointment findByAppointmentId(int appointmentId)
	{
		return appoitnmentDao.findByAppointmentId(appointmentId);
	}
	
	@Override
	public String viewAppointment(int userId) 
	{
		User user = findByUserId(userId);
		int appointmentId = user.getAppointment().getAppointmentId();
		Appointment appointment = findByAppointmentId(appointmentId);
		String appointmentInfo = "Appointment Id : "+appointment.getAppointmentId()+
				                 "\nCenter Name    : "+appointment.getCenter().getCenterName()+
				                 "\nTest Name      : "+appointment.getTest().getTestName()+
				                 "\nAddress        :"+ appointment.getCenter().getAddress()+
				                 "\nContact Number : "+appointment.getCenter().getContactNumber()+
				                 "\nApproval Staus : "+appointment.isApproved();
		return appointmentInfo;
	}

	@Override
	public List<DiagnosticCenter> listOfDiagnosticCenters() {
		
		return appoitnmentDao.listOfDiagnosticCenters();
	}
	@Override
	public List<Test> listOfTests(String centerName)
	{
		return appoitnmentDao.listOfTests(centerName);
	}
}
