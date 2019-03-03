/**  

* 创建时间：2018年12月8日 下午3:31:35  

* 项目名称：csys  

* @author 罗强  

* @version 1.0   

* @since JDK 1.8  

* 文件名称：StudentMapper.java  

* 类说明：  

*/

package edu.swu.mapper;

import org.apache.ibatis.annotations.Mapper;

import edu.swu.model.Student;

@Mapper
public interface StudentMapper {

	//select a student by id
	public Student getStudentById(Integer sid) throws Exception;
	
	//select a student by acount
	public Student getStudentByAcount(String acount) throws Exception;
	
}
