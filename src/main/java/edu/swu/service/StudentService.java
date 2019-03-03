/**  

* 创建时间：2018年12月8日 下午3:43:39  

* 项目名称：csys  

* @author 罗强  

* @version 1.0   

* @since JDK 1.8  

* 文件名称：StudentService.java  

* 类说明：  

*/

package edu.swu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.swu.mapper.StudentMapper;
import edu.swu.model.Student;

@Service
public class StudentService {

	@Autowired
	private StudentMapper studentMapper;
	
	//login-service
	public Student getStudentByAcount(String acount) throws Exception {
		return studentMapper.getStudentByAcount(acount);
	}
	
}
