/**  

* 创建时间：2018年12月8日 下午3:44:47  

* 项目名称：csys  

* @author 罗强  

* @version 1.0   

* @since JDK 1.8  

* 文件名称：SubmissionService.java  

* 类说明：  

*/

package edu.swu.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.swu.mapper.SubmissionMapper;
import edu.swu.model.Submission;
import edu.swu.model.SubmissionVo;
import edu.swu.utils.HammingDistance;

@Service
public class SubmissionService {

	@Autowired
	private SubmissionMapper submissionMapper;
	
	//get a submission by id
	public Submission getSubmissionById(Integer sid) throws Exception {
		return submissionMapper.getSubmissionById(sid);
	}
	
	//get grade
	public List<SubmissionVo> getGrade(Integer hid) throws Exception {
		return submissionMapper.getGrade(hid);
	}
	
	//get all the submissions of a teacher
	public List<SubmissionVo> getSubmissionsOfTeacher(Integer tid) throws Exception {
		return submissionMapper.getSubmissionsOfTeacher(tid);
	}
	
	//add a submission
	public void addSubmission(Submission submission) throws Exception {
		submissionMapper.addSubmission(submission);
	}
	
	public void updateSubmission(Map<String,Object> submission) throws Exception {
		submissionMapper.updateSubmission(submission);
	}
	
	//get submissions of a homework
	public List<SubmissionVo> getSubmissions(Integer hid) throws Exception {
		return submissionMapper.getSubmissions(hid);
	}
	
	//jduge !important
	public void jduge(Submission submission) throws Exception {
		List<Submission> list = submissionMapper.getSubmissionsOfHomework(submission.getHid());
		List<Map<String,Object>> update_list = new ArrayList<>();
		Map<String,Object> up_sub = new HashMap<>();
		up_sub.put("sid",submission.getSid());
		int min = 64;
		int similar = 0;
		for(Submission sub : list) {
			if(sub.getSid() == submission.getSid()) {
				continue;
			} else {
				Short dis = HammingDistance.getHammingDistance(submission.getFinger(), sub.getFinger());
				if(dis < min) {
					min = dis;
					similar = sub.getSsid();
				}
				if(dis < sub.getMin()) {
					Map<String,Object> map = new HashMap<>();
					map.put("sid",sub.getSid());
					map.put("min",dis);
					map.put("similar",submission.getSsid());
					if(dis < 4 && sub.getCopied() == 0) {
						map.put("copied",1);
					}
					update_list.add(map);
				}
			}
		}
		up_sub.put("min",min);
		up_sub.put("similar",similar);
		if(min < 4) {
			up_sub.put("copied",1);
		}
		submissionMapper.updateSubmission(up_sub);
		for(Map<String,Object> map : update_list) {
			submissionMapper.updateSubmission(map);
		}
	}
	
}
