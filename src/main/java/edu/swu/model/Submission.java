/**  

* 创建时间：2018年12月8日 下午3:24:05  

* 项目名称：csys  

* @author 罗强  

* @version 1.0   

* @since JDK 1.8  

* 文件名称：Submission.java  

* 类说明：  

*/

package edu.swu.model;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Alias("Submission")
@JsonInclude(value=Include.NON_NULL)
public class Submission implements Serializable {

	private static final long serialVersionUID = 1137659148052385479L;

	private Integer sid;
	
	private String content;
	
	private Integer ssid;
	
	private Integer hid;
	
	private Long finger;
	
	private Long time;
	
	private Short copied;
	
	private Integer similar;
	
	private Short grade;
	
	private Short min;
	
	public Submission() {}

	public Submission(Integer sid, String content, Integer ssid, Integer hid, Long finger, 
			Long time, Short copied,Integer similar, Short grade, Short min) {
		this.sid = sid;
		this.content = content;
		this.ssid = ssid;
		this.hid = hid;
		this.finger = finger;
		this.time = time;
		this.copied = copied;
		this.similar = similar;
		this.grade = grade;
		this.setMin(min);
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

	@Override
	public String toString() {
		return "Submission [sid=" + sid + ", content=" + content + ", ssid=" + ssid + ", hid=" + hid + ", finger="
				+ finger + ", time=" + time + ", copied=" + copied + ", similar=" + similar + ", grade=" + grade + "," + min +"]";
	}

	/**
	 * @return the min
	 */
	public Short getMin() {
		return min;
	}

	/**
	 * @param min the min to set
	 */
	public void setMin(Short min) {
		this.min = min;
	};
	
}
