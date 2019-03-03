/**  

* 创建时间：2018年12月8日 下午3:13:49  

* 项目名称：csys  

* @author 罗强  

* @version 1.0   

* @since JDK 1.8  

* 文件名称：Teacher.java  

* 类说明：  教师原型类

*/

package edu.swu.model;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Alias("Teacher")
@JsonInclude(value=Include.NON_NULL)
public class Teacher implements Serializable {

	private static final long serialVersionUID = -1855369652960541558L;

	private Integer tid;
	
	private String acount;
	
	private String password;
	
	private Long modified;
	
	public Teacher() {}

	public Teacher(Integer tid, String acount, String password, Long modified) {
		this.tid = tid;
		this.acount = acount;
		this.password = password;
		this.modified = modified;
	}

	public Integer getTid() {
		return tid;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
	}

	public String getAcount() {
		return acount;
	}

	public void setAcount(String acount) {
		this.acount = acount;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getModified() {
		return modified;
	}

	public void setModified(Long modified) {
		this.modified = modified;
	}

	@Override
	public String toString() {
		return "Teacher [tid=" + tid + ", acount=" + acount + ", "
			+ "password=" + password + ", modified=" + modified + "]";
	};
	
}
