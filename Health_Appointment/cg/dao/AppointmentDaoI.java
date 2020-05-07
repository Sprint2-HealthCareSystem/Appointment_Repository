package com.cg.dao;

import java.util.List;

import com.cg.entity.Appointment;
import com.cg.entity.DiagnosticCenter;
import com.cg.entity.Test;
import com.cg.entity.User;

public interface AppointmentDaoI {
	
	    public String addCenter(DiagnosticCenter diagnosticCenter);

		public List<DiagnosticCenter> listOfDiagnosticCenters();
		
		public List<Test> listOfTests(String centerName);
		
		public String createUser(User user);
		
		public User findByUserId(int userId);
		
		public String makeAppointment(Appointment appointment);

		public Appointment findByAppointmentId(int appointmentId);

}
