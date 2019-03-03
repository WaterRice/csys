/**  

* 创建时间：2018年12月8日 下午3:18:35  

* 项目名称：csys  

* @author 罗强  

* @version 1.0   

* @since JDK 1.8  

* 文件名称：Homework.java  

* 类说明：  作业原型类

*/

package edu.swu.model;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Alias("Homework")
@JsonInclude(value=Include.NON_NULL)
public class Homework implements Serializable {

	private static final long serialVersionUID = 6151361886583135926L;

	private Integer hid;
	
	private String title;
	
	private String descb;
	
	private String repu;
	
	private String notice;
	
	private String imgUrl;
	
	private Long pubTime;
	
	private Integer tid;
	
	public Homework() {}

	public Homework(Integer hid, String title, String desc, String repu, 
			String notice, String imgUrl, Long pubTime,Integer tid) {
		this.hid = hid;
		this.title = title;
		this.descb = desc;
		this.repu = repu;
		this.notice = notice;
		this.imgUrl = imgUrl;
		this.pubTime = pubTime;
		this.tid = tid;
	}

	public Integer getHid() {
		return hid;
	}

	public void setHid(Integer hid) {
		this.hid = hid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescb() {
		return descb;
	}

	public void setDescb(String desc) {
		this.descb = desc;
	}

	public String getRepu() {
		return repu;
	}

	public void setRepu(String repu) {
		this.repu = repu;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Long getPubTime() {
		return pubTime;
	}

	public void setPubTime(Long pubTime) {
		this.pubTime = pubTime;
	}

	public Integer getTid() {
		return tid;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
	}

	@Override
	public String toString() {
		return "Homework [hid=" + hid + ", title=" + title + ", desc=" + descb + ", repu=" + repu + ", notice=" + notice
				+ ", imgUrl=" + imgUrl + ", pubTime=" + pubTime + ", tid=" + tid + "]";
	};
	
}
