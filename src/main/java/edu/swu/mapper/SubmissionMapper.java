/**  

* 创建时间：2018年12月8日 下午3:32:32  

* 项目名称：csys  

* @author 罗强  

* @version 1.0   

* @since JDK 1.8  

* 文件名称：SubmissionMapper.java  

* 类说明：  

*/

package edu.swu.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import edu.swu.model.Submission;
import edu.swu.model.SubmissionVo;

@Mapper
public interface SubmissionMapper {

	//select a submission by id
	public Submission getSubmissionById(Integer sid) throws Exception;
	
	//select all submissions of a teacher
	public List<SubmissionVo> getSubmissionsOfTeacher(Integer tid) throws Exception;
	
	//select all submissions of a homework
	public List<Submission> getSubmissionsOfHomework(Integer hid) throws Exception;
	
	//add a submission
	public void addSubmission(Submission submission) throws Exception;
	
	//update a submission selectivly
	public void updateSubmission(Map<String,Object> submission) throws Exception;
	
	//get grade
	public List<SubmissionVo> getGrade(Integer hid) throws Exception;
	
	public List<SubmissionVo> getSubmissions(Integer hid) throws Exception;
	
}
