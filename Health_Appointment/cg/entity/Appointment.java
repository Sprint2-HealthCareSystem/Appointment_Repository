package com.cg.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Component
@Entity
@Table(name="AppointmentTable")
public class Appointment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int  appointmentId ;
	
	@Column
	private LocalDateTime datetime;
	
	
	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name="user_id",nullable=false)
	private User user;

	@ManyToOne
	private DiagnosticCenter center;
	
    @ManyToOne
	private Test test;

	@Column
	private boolean approved;
	
	public int getAppointmentId() 
	{
		return appointmentId;
	}
	
	public void setAppointmentId(int appointmentId) 
	{
		this.appointmentId = appointmentId;
	}

	@JsonIgnore
	public User getUser() 
	{
		return user;
	}
	
    @JsonIgnore
	public void setUser(User user)
	{
		this.user = user;
	}
	
    @JsonIgnore
	public Test getTest() 
	{
		return test;
	}

	@JsonIgnore
	public void setTest(Test test)
	{
		this.test = test;
	}

	public boolean isApproved() 
	{
		return approved;
	}

	public void setApproved(boolean approved) 
	{
		this.approved = approved;
	}
	
	@JsonIgnore
	public DiagnosticCenter getCenter()
	{
		return center;
	}
	
    @JsonIgnore
	public void setCenter(DiagnosticCenter center)
	{
		this.center = center;
	}

	public LocalDateTime getDatetime() {
		return datetime;
	}

	public void setDatetime(LocalDateTime datetime) {
		this.datetime = datetime;
	}
    
}
