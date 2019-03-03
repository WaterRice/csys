/**  

* 创建时间：2018年12月8日 下午3:35:21  

* 项目名称：csys  

* @author 罗强  

* @version 1.0   

* @since JDK 1.8  

* 文件名称：TeacherController.java  

* 类说明：  

*/

package edu.swu.controller;

import java.io.ByteArrayOutputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.swu.model.Homework;
import edu.swu.model.SubmissionVo;
import edu.swu.model.Teacher;
import edu.swu.service.HomeworkService;
import edu.swu.service.SubmissionService;
import edu.swu.service.TeacherService;
import edu.swu.utils.ExportExcelFileUtils;

@RestController
@RequestMapping("/apis/teacher")
public class TeacherController {

	@Autowired
	private TeacherService teacherService;
	
	@Autowired
	private SubmissionService submissionService;
	
	@Autowired
	private HomeworkService homeworkService;
	
	@GetMapping("/token")
	public boolean login(@RequestParam String acount, 
		@RequestParam String password, HttpSession session) throws Exception {
		boolean flag;
		Teacher teacher = teacherService.getTeacherByAcount(acount);
		if(teacher == null) {
			flag = false;
		} else if(!teacher.getPassword().equals(password)) {
			flag = false;
		} else {
			session.setAttribute("tid", teacher.getTid());
			flag = true;
		}
		return flag;
	}
	
	@DeleteMapping("/token")
	public boolean loginOut(HttpSession session) {
		session.setAttribute("tid", null);
		return true;
	}
	
	@GetMapping("/submissions/of/{hid}")
	public List<SubmissionVo> getSubmissions(@PathVariable Integer hid) throws Exception {
		return submissionService.getSubmissions(hid);
	}
	
	@GetMapping("/homeworks")
	public List<Homework> getHomeworksOfTeacher(HttpSession session) throws Exception {
		Integer tid = (Integer)session.getAttribute("tid");
		List<Homework> list = homeworkService.getHomeworksOfTeacher(tid);
		Collections.sort(list,new Comparator<Homework>() {
			@Override
			public int compare(Homework o1, Homework o2) {
				if(o1.getPubTime() < o2.getPubTime()) return -1;
				else if(o1.getPubTime() > o2.getPubTime()) return 1;
				else return 0;
			}	
		});
		return list;
	}
	
	@PostMapping("/homeworks")
	public boolean addHomework(@RequestBody Map<String,Object> homework, HttpSession session) throws Exception {
		Integer tid = (Integer)session.getAttribute("tid");
		homework.put("tid",tid);
		homeworkService.addHomework(homework);
		return true;
	}
	
	@GetMapping("/grade/{hid}")
	public ResponseEntity<byte[]> getGrade(@PathVariable Integer hid) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentDispositionFormData("attachment",
				new String("成绩表.xlsx".getBytes("utf-8"),"iso-8859-1"));
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		List<SubmissionVo> list = submissionService.getGrade(hid);
		Collections.sort(list, new Comparator<SubmissionVo>() {
			@Override
			public int compare(SubmissionVo o1, SubmissionVo o2) {
				if(o1.getSsid() < o2.getSsid()) return -1;
				else if(o1.getSsid() == o2.getSsid()) return 0;
				else return 1;
			}
		});
		ByteArrayOutputStream os = ExportExcelFileUtils.getByteArrayOutputStream(list);
		
		return new ResponseEntity<byte[]>(os.toByteArray(), headers, HttpStatus.CREATED);
	}
	
	@PutMapping("/submissions/{sid}")
	public boolean putGrade(@RequestBody Map<String,Object> submission, 
		@PathVariable Integer sid) throws Exception {
		submission.put("sid", sid);
		submissionService.updateSubmission(submission);
		return true;
	}
}
