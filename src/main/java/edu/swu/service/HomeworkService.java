/**  

* 创建时间：2018年12月8日 下午3:44:26  

* 项目名称：csys  

* @author 罗强  

* @version 1.0   

* @since JDK 1.8  

* 文件名称：HomeworkService.java  

* 类说明：  

*/

package edu.swu.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.swu.mapper.HomeworkMapper;
import edu.swu.model.Homework;

@Service
public class HomeworkService {

	@Autowired
	private HomeworkMapper homeworkMapper;
	
	//get all the homeworks
	public List<Homework> getHomeworks() throws Exception {
		return homeworkMapper.getHomeworks();
	}
	
	//get a homework by id
	public Homework getHomeworkById(Integer hid) throws Exception {
		return homeworkMapper.getHomeworkById(hid);
	}
	
	//get all homeworks of a teacher
	public List<Homework> getHomeworksOfTeacher(Integer tid) throws Exception {
		return homeworkMapper.getHomeworksOfTeacher(tid);
	}
	
	//add homework
	public void addHomework(Map<String,Object> params) throws Exception {
		Homework homework = new Homework();
		homework.setTitle((String)params.get("title"));
		
		homework.setDescb((String)params.get("desc"));
		
		homework.setImgUrl((String)params.get("imgUrl"));
		
		homework.setPubTime((Long)params.get("pubTime"));
		
		homework.setTid((Integer)params.get("tid"));
		
		homework.setRepu((String)params.get("repu"));
		
		homework.setNotice((String)params.get("notice"));
		
		homeworkMapper.addHomework(homework);
	}
}
