/**  

* 创建时间：2018年12月8日 下午10:44:09  

* 项目名称：csys  

* @author 罗强  

* @version 1.0   

* @since JDK 1.8  

* 文件名称：SubmissionVo.java  

* 类说明：  

*/

package edu.swu.model;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Alias("SubmissionVo")
@JsonInclude(value=Include.NON_NULL)
public class SubmissionVo implements Serializable {

	/** serialVersionUID**/
	private static final long serialVersionUID = 3840430124405610430L;
	
	private Integer sid;
	
	private String content;
	
	private Integer ssid;
	
	private Integer hid;
	
	private Long finger;
	
	private Long time;
	
	private Short copied;
	
	private Integer similar;
	
	private Short grade;
	
	private String name;
	
	public SubmissionVo() {}

	public SubmissionVo(Integer sid, String content, Integer ssid, Integer hid, Long finger, Long time, Short copied,
			Integer similar, Short grade, String name) {
		this.sid = sid;
		this.content = content;
		this.ssid = ssid;
		this.hid = hid;
		this.finger = finger;
		this.time = time;
		this.copied = copied;
		this.similar = similar;
		this.grade = grade;
		this.name = name;
	}

	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getSsid() {
		return ssid;
	}

	public void setSsid(Integer ssid) {
		this.ssid = ssid;
	}

	public Integer getHid() {
		return hid;
	}

	public void setHid(Integer hid) {
		this.hid = hid;
	}

	public Long getFinger() {
		return finger;
	}

	public void setFinger(Long finger) {
		this.finger = finger;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public Short getCopied() {
		return copied;
	}

	public void setCopied(Short copied) {
		this.copied = copied;
	}

	public Integer getSimilar() {
		return similar;
	}

	public void setSimilar(Integer similar) {
		this.similar = similar;
	}

	public Short getGrade() {
		return grade;
	}

	public void setGrade(Short grade) {
		this.grade = grade;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	};

}
