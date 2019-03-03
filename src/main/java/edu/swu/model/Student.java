/**  

* 创建时间：2018年12月8日 下午2:56:46  

* 项目名称：csys  

* @author 罗强  

* @version 1.0   

* @since JDK 1.8  

* 文件名称：Student.java  

* 类说明：  学生原型类

*/

package edu.swu.model;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Alias("Student")
@JsonInclude(value=Include.NON_NULL)
public class Student implements Serializable {

	private static final long serialVersionUID = -1211250485559215510L;

	private Integer sid;
	
	private String acount;
	
	private String password;
	
	private Short gender;
	
	private String name;
	
	public Student() {}

	public Student(Integer sid, String acount, String password, Short gender, String name) {
		this.sid = sid;
		this.acount = acount;
		this.password = password;
		this.gender = gender;
		this.name = name;
	}

	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
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

	public Short isGender() {
		return gender;
	}

	public void setGender(Short gender) {
		this.gender = gender;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Student [sid=" + sid + ", acount=" + acount + ", password=" + password + ", gender=" + gender
				+ ", name=" + name + "]";
	};
	
}
