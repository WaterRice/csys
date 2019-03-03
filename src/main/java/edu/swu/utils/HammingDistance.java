/**  

* 创建时间：2018年12月9日 下午8:43:26  

* 项目名称：csys  

* @author 罗强  

* @version 1.0   

* @since JDK 1.8  

* 文件名称：HammingDistance.java  

* 类说明：  

*/

package edu.swu.utils;

public class HammingDistance {

	/*
	 *   计算海明距离
	 *   @param a
	 *   @param b
	 *   @return 海明距离
	 */
	public static Short getHammingDistance(Long a, Long b) {
		
		Short count = 0;
		Long tmp = a ^ b;
		while(tmp != 0) {
			tmp = tmp & (tmp - 1);
			count++;
		}
		return count;
	}
}
