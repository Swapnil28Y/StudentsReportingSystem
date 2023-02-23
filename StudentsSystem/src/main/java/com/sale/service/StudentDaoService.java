package com.sale.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.elasticsearch.common.UUIDs;
import org.springframework.beans.factory.annotation.Autowired;

import com.sale.model.Semester;
import com.sale.model.Student;
import com.sale.repository.StudentDao;

public class StudentDaoService {
	@Autowired
	private StudentDao studentRepo;


	public String addStudentDetails(String name) {

		String Id = UUIDs.randomBase64UUID();

		System.out.println("Student Count : " + Id);
		Semester first = new Semester(1);
		Semester second = new Semester(2);
		List<Semester> semList = new ArrayList<>();
		semList.add(first);
		semList.add(second);
		Student student = new Student(Id, name, semList);

		System.out.println("Student Count is : " + Id);
		System.out.println("Student semester is : " + student.getSemesters());

		try {
			studentRepo.save(student);
		} catch (Exception e) {
			return e.toString();
		}
		return "Student added Successfully into the database!";

	}

	public String addStudentMarks(String studentId, int semId, String subject, int marks) {

		try {
			Optional<Student> students = studentRepo.findById(studentId);
			if (students.isEmpty()) {
				return "There is no student with given id : " + semId;
			}
			Student student = students.get();
			List<Semester> semestersList = student.getSemesters();
			for (Semester sem : semestersList) {

				System.out.println(sem.getSemId());
				System.out.println(semId);
				System.out.println(sem.getSemId() == semId);
				System.out.println((subject.equals("Maths")));

				if (sem.getSemId() == semId) {
					if (subject.equals("Science")) {
						sem.setEnglish(marks);
					} else if (subject.equals("Maths")) {
						sem.setMaths(marks);
					} else if (subject.equals("English")) {
						sem.setScience(marks);
					} else {
						return "There is no subject with given name";
					}

					break;
				}
			}
			student.setSemesters(semestersList);
			studentRepo.save(student);

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return "MArks added successfully";
	}

	public Student getStudent(String studentId) {

		try {
			Optional<Student> students = studentRepo.findById(studentId);

			if (students.isEmpty()) {
				throw new Exception("There is no student");
			}

			Student student = students.get();
			return student;

		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return null;
	}
	public String findAveragePercentage(int sem) {
		Double avg = 0.0;
		try {
			List<Double> percentageList = new ArrayList<>();
			Iterable<Student> studentList = studentRepo.findAll();
			for (Student stu : studentList) {
				Semester semester = stu.getSemesters().get(sem - 1);
				Double sum = Double.valueOf((semester.getEnglish() + semester.getMaths() + semester.getScience()));
				Double percentage = sum / 3;
				percentageList.add(percentage);
			}
			for (Double doubles : percentageList) {
				avg += doubles;
			}
			
			avg = avg / percentageList.size();
			System.out.println("average : " + avg);
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		DecimalFormat dec = new DecimalFormat("####0.00");
		return dec.format(avg);
	}

	public List<Student> getAllStudentsDetails() {
		try {
			Iterable<Student> students = studentRepo.findAll();
			List<Student> studentList = new ArrayList<>();
			for (Student stu : students) {
				studentList.add(stu);
			}
			return studentList;
		} catch (Exception e) {
			System.out.println("error " + e.toString());
		}
		return new ArrayList<Student>();
	}

	public String deleteStudent(String id) {
		try {
			studentRepo.deleteById(id);
		} catch (Exception e) {
			return (e.toString());
		}
		return "Student has been removed which has id " + id;
	}
}
