/**  

* 创建时间：2018年12月8日 下午3:44:03  

* 项目名称：csys  

* @author 罗强  

* @version 1.0   

* @since JDK 1.8  

* 文件名称：TeacherService.java  

* 类说明：  

*/

package edu.swu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.swu.mapper.TeacherMapper;
import edu.swu.model.Teacher;

@Service
public class TeacherService {

	@Autowired
	private TeacherMapper teacherMapper;
	
	//login-service
	public Teacher getTeacherByAcount(String acount) throws Exception {
		return teacherMapper.getTeacherByAcount(acount);
	}
	
}
