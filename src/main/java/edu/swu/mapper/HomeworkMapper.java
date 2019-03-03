/**  

* 创建时间：2018年12月8日 下午3:32:18  

* 项目名称：csys  

* @author 罗强  

* @version 1.0   

* @since JDK 1.8  

* 文件名称：HomeworkMapper.java  

* 类说明：  

*/

package edu.swu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import edu.swu.model.Homework;

@Mapper
public interface HomeworkMapper {

	//select a homework by id
	public Homework getHomeworkById(Integer hid) throws Exception;
	
	//select all the homeworks
	public List<Homework> getHomeworks() throws Exception;
	
	//select a teacher's homeworks
	public List<Homework> getHomeworksOfTeacher(Integer tid) throws Exception;
	
	//add a homework
	public void addHomework(Homework homework) throws Exception;
	
}
