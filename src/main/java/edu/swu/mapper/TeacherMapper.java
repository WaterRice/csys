/**  

* 创建时间：2018年12月8日 下午3:31:58  

* 项目名称：csys  

* @author 罗强  

* @version 1.0   

* @since JDK 1.8  

* 文件名称：TeacherMapper.java  

* 类说明：  

*/

package edu.swu.mapper;

import org.apache.ibatis.annotations.Mapper;

import edu.swu.model.Teacher;

@Mapper
public interface TeacherMapper {

	//select a teacher by id
	public Teacher getTeacherById(Integer tid) throws Exception;
	
	//select a teacher by acount
	public Teacher getTeacherByAcount(String acount) throws Exception;
	
}
