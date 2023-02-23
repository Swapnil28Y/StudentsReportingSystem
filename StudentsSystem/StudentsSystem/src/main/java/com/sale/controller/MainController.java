package com.sale.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sale.model.Semester;
import com.sale.model.Student;
import com.sale.repository.StudentDao;
import com.sale.service.StudentDaoService;

public class MainController {
	@Autowired
	private StudentDaoService sService;

	@Autowired
	private StudentDao sdao;

	@RequestMapping("/")
	public ModelAndView Index() {
		ModelAndView mod = new ModelAndView("index.jsp");
		List<Student> studentList = sService.getAllStudentsDetails();
		mod.addObject("students", studentList);

		String averageClass1 = sService.findAveragePercentage(1);
		String averageClass2 = sService.findAveragePercentage(2);

		HashMap<String, Double> map2 = sService.top2();
		String s1 = "", s2 = "";
		Double d1 = 0.0, d2 = 0.0;
		for (Map.Entry<String, Double> entry : map2.entrySet()) {
			if (entry.getValue() > d1) {
				d2 = d1.doubleValue();
				d1 = entry.getValue().doubleValue();
				s2 = s1.toString();
				s1 = entry.getKey().toString();
			} else if (entry.getValue() > d2) {
				d2 = entry.getValue().doubleValue();
				s2 = entry.getKey().toString();
			}
		}
		Optional<Student> student1 = sdao.findById(s1);
		String n1 = student1.get().getName();

		Optional<Student> student2 = sdao.findById(s1);
		String n2 = student2.get().getName();
		mod.addObject("averageClass1", averageClass1);
		mod.addObject("averageClass2", averageClass2);
		mod.addObject("top1", s1);
		mod.addObject("top2", s2);
		mod.addObject("top1_score", d1);
		mod.addObject("top2_score", d2);
		mod.addObject("top1_name", n1);
		mod.addObject("top2_name", n2);

		return mod;
	}

	@RequestMapping(value = "/addSemMarks", method = RequestMethod.POST)
	public String addSemesterMarks(@RequestParam("semId") int semId, @RequestParam("Id") String Id,
			@RequestParam("English") String English, @RequestParam("Maths") String Maths,
			@RequestParam("Science") String Science) {
		int semester = semId;
		try {
			if (English != "" && !English.isEmpty()) {
				sService.addStudentMarks(Id, semester, "English", Integer.parseInt(English));
			}
			if (Science != "" && !Science.isEmpty()) {
				sService.addStudentMarks(Id, semester, "Science", Integer.parseInt(Science));
			}
			if (Maths != "" && !Maths.isEmpty()) {
				sService.addStudentMarks(Id, semester, "Maths", Integer.parseInt(Maths));
			}
		} catch (Exception e) {
			System.out.println(e.toString());
			return e.toString();
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/addStudentMarks", method = RequestMethod.POST)
	public String addMarks(@RequestParam("Id") String Id, @RequestParam("sem") int sem,
			@RequestParam("subject") String subject, @RequestParam("marks") int marks) {
		try {
			String res = sService.addStudentMarks(Id, sem, subject, marks);
			return "redirect:/";
		} catch (Exception e) {
			System.out.println(e.toString());
			return e.toString();
		}

	}

	@RequestMapping(value = "/deleteStudent", method = RequestMethod.POST)
	public String deleteStudentByID(@RequestParam("SId") String sid) {
		try {
			String response = sService.deleteStudent(sid);
		} catch (Exception e) {
			System.out.println(e.toString());
			return (e.toString());
		}

		return "redirect:/";
	}

	@RequestMapping(value = "/addStudent", method = RequestMethod.POST)
	public String addStudentByName(@RequestParam("SName") String sName) {
		try {
			String res = sService.addStudentDetails(sName);
		} catch (Exception e) {
			System.out.println(e.toString());
			return e.toString();
		}

		return "redirect:/";
	}

	@RequestMapping("/home")
	public ModelAndView home() {
		ModelAndView model = new ModelAndView("home.jsp");
		model.addObject("message", "this.message");
		System.out.println("home");
		return model;
	}

	@RequestMapping("/save")
	public String saveStudent() throws IOException {
		Student s = new Student("101", "Raj");
		s.setSemesters(new ArrayList<Semester>());
		System.out.println(s.toString());
		return "save.jsp";
	}

	@RequestMapping("/find")
	public String find(Model model) {

		return "find.jsp";
	}
}
