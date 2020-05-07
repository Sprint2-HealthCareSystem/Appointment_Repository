package com.cg.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cg.entity.Appointment;
import com.cg.entity.DiagnosticCenter;
import com.cg.entity.Test;
import com.cg.entity.User;
import com.cg.exception.BadRequestException;
import com.cg.exception.ResourceNotFoundException;
import com.cg.service.AppointmentService;

@CrossOrigin(origins="http://localhost:4200")
@RestController
public class AppointmentController 
{
	@Autowired
	AppointmentService appointmentService;

	@PostMapping(value="HealthCareSystem/add-center", consumes = {"application/json"})
	public String addCenter(@Valid @RequestBody DiagnosticCenter diagnosticCenter) throws BadRequestException
	{
		if(appointmentService.isCenterNameExist(diagnosticCenter.getCenterName()))
		{
			throw new BadRequestException(diagnosticCenter.getCenterName()+" center name already exits");
		}
		return appointmentService.addCenter(diagnosticCenter);
	}
	
	@PostMapping(value="HealthCareSystem/make-appointment/{centerName}/{testName}/{userId}", consumes = {"application/json"})
	public String makeAppointment(@PathVariable String centerName, @PathVariable String testName, @PathVariable int userId
			,@RequestBody Appointment appointment) throws ResourceNotFoundException
	{
		DiagnosticCenter diagnosticCenter = appointmentService.findByCenterName(centerName);
		Test test = appointmentService.findByTestName(diagnosticCenter, testName);
		User user = appointmentService.findByUserId(userId);
		
		appointment.setCenter(diagnosticCenter);
		appointment.setTest(test);
		appointment.setUser(user);
//		appointment.setDatetime(dateTime);
		return appointmentService.makeAppointment(appointment);
	} 
	
	@PostMapping(value="HealthCareSystem/create-user", consumes = {"application/json"})
	public String createUser(@Valid @RequestBody User user)
	{
		return appointmentService.createUser(user);
	}
	
	@GetMapping(value="HealthCareSystem/view-appointment/{userId}")
	public String viewAppointment(@PathVariable int userId)
	{
		return appointmentService.viewAppointment(userId);	
	}
	
	@GetMapping(value="HealthCareSystem/view-centers")
	public List<DiagnosticCenter> listOfDiagnosticCenters()
	{
		return appointmentService.listOfDiagnosticCenters();
	}
	@GetMapping(value="HealthCareSystem/view-tests/{centerName}")
	public List<Test> listOfTests(@PathVariable String centerName)
	{
		return appointmentService.listOfTests(centerName);
	}
}