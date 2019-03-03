/**  

* 创建时间：2018年12月8日 下午3:35:03  

* 项目名称：csys  

* @author 罗强  

* @version 1.0   

* @since JDK 1.8  

* 文件名称：StudentController.java  

* 类说明：  

*/

package edu.swu.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.swu.model.Homework;
import edu.swu.model.Student;
import edu.swu.model.Submission;
import edu.swu.service.HomeworkService;
import edu.swu.service.StudentService;
import edu.swu.service.SubmissionService;
import edu.swu.utils.SimHash;

@RestController
@RequestMapping("/apis/student")
public class StudentController {

	@Autowired
	private StudentService studentService;
	
	@Autowired
	private HomeworkService homeworkService;
	
	@Autowired
	private SubmissionService submissionService;
	
	@GetMapping("/token")
	public boolean login(@RequestParam String acount, 
			@RequestParam String password, HttpSession session) throws Exception {
		boolean flag;
		Student student = studentService.getStudentByAcount(acount);
		if(student == null) {
			flag = false;
		} else if(!student.getPassword().equals(password)) {
			flag = false;
		} else {
			session.setAttribute("sid", student.getSid());
			session.setAttribute("name", student.getName());
			flag = true;
		}
		return flag;
	}
	
	@DeleteMapping("/token")
	public boolean loginOut(HttpSession session) {
		session.setAttribute("sid", null);
		session.setAttribute("name", null);
		return true;
	}
	
	@GetMapping("/homeworks")
	public List<Homework> getHomeworks() throws Exception {
		return homeworkService.getHomeworks();
	}
	
	@GetMapping("/homeworks/{hid}")
	public Homework getHomeworkDetail(@PathVariable Integer hid) throws Exception {
		return homeworkService.getHomeworkById(hid);
	}
	
	@PostMapping("/submissions")
	public boolean handleHomework(@RequestBody Map<String,Object> params,
			HttpSession session) throws Exception {
		
		Integer ssid = (Integer)session.getAttribute("sid");
		if(ssid == null) return false;
		Submission submission = new Submission();
		submission.setSsid(ssid);
		submission.setHid((Integer)params.get("hid"));
		submission.setContent((String)params.get("content"));
		submission.setTime(System.currentTimeMillis());
		SimHash finger = new SimHash((String)params.get("content"));
		submission.setFinger(finger.getIntSimHash().longValue());
		submissionService.addSubmission(submission);
		submissionService.jduge(submission);
		return true;
	}
}
