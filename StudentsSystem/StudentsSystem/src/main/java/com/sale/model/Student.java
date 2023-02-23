package com.sale.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Document(indexName = "student-index")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Student {
	@Id
	private String id;
	@Field(type = FieldType.Text, name = "name")
	private String name;
	@Field(type = FieldType.Auto, name = "semesters")
	private List<Semester> semesters;

	public Student(String s1, String s2) {
		this.id = s1;
		this.name = s2;

	}
}
