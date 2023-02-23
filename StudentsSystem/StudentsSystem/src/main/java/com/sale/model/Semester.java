package com.sale.model;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Semester {
	private int semId;
	private int English = 0;
	private int Maths = 0;
	private int Science = 0;
	
	public Semester(int semId){
		this.semId = semId;
	}
}
