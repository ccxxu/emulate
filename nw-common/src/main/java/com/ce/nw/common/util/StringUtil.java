package com.ce.nw.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	/** 下划线 */
	private static final char SEPARATOR = '_';

	public static final String EMPTY = "";

	/**
	 * 生成  yyyy/MM 串
	 * @return
	 */
	public static String dealStrYearMM(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		Date now = new Date();
		String str = sdf.format(now);
		return str;
	}
	
	public static Integer string2Int(String str) {
		try {
			return Integer.valueOf(str);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 生成  yyyy/MM 串
	 * @return
	 */
	public static String dealStrYearMM(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		String str = sdf.format(date);
		return str.substring(0, 4) + "/" + str.substring(4);
	}
	
	/**
	 * 日期转化为指定格式的字符串
	 * @param date Object 要转化的日期(应是一个有效的date)
	 * @param format 格式字符串，yyyy-MM-dd HH:mm:ss.SSS（详见SimpleDateFormat类）
	 * @return
	 */
	public static String dateToString(Object date,String format){
		if(date == null || date.equals("")) return "";
		else{
			try{
				return new SimpleDateFormat(format,Locale.CHINA).format((Date)date);
			}catch(Exception e){
				return date.toString();
			}		
		}
	}
	
	 public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }
	
	public static boolean isEmpty(String str){
		if(str != null && !"".equals(str)){
			return false;
		}else{
			return true;
		}
	}
	
	public static boolean isNotNullOrEmpty(Object obj){
		if ("".equals(obj) || obj == null) {
			return false;
		}
		return true;
	}

	/**尝试将字符串转化为整型
	 * @param s 要转化的字符串
	 * @param defaultValue 转化失败的默认值
	 * @return
	 */
	public static int tryToInt(String s,int defaultValue){
		int r=defaultValue;
		try{
			r=Integer.parseInt(s);
		}catch(Exception e){
			r=defaultValue;
		}
		return r;
	}
	/**
	* <p>方法名称: stringToDate|描述: 字符串转化为日期</p>
	* @param str 要转化的字符串
	* @param format 格式字符串，yyyy-MM-dd HH:mm:ss.SSS（详见SimpleDateFormat类）
	* @return 转化失败返回null
	*/
	public static Date stringToDate(String str,String format){
		if(str == null || str.equals("")) return null;
		else{
			try{
				if(str.length() != format.length()) return null;
				return new SimpleDateFormat(format).parse(str);
			}catch(Exception e){
				return null;
			}		
		}
	}
	
	/**
	 * 判断是否是时间格式字符串
	 * @param patternString
	 * @return
	 */
	public static boolean isTimeLegal(String patternString) {
		Pattern a1=Pattern.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s((([0-1][0-9])|(2?[0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
		Pattern a2=Pattern.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s((([0-1][0-9])|(2?[0-3]))\\:([0-5]?[0-9])))?$");
		Matcher b1=a1.matcher(patternString);
		Matcher b2=a2.matcher(patternString);
		if(b1.matches() || b2.matches()) {
			return true;
		} else {
			return false;
		}
	}

	public static String escapeReplace(String src) {
		src = src.replaceAll("\r\n", "\\\\r\\\\n");
		src = src.replaceAll("\"", "\\\\\"");
		return src;
	}
	
	public static String escapeExprSpecialWord(String keyword) {
		if (isNotNullOrEmpty(keyword)) {
			String[] fbsArr = { "\\", "/", ":", "*", "?", "\"", "<", ">", "|" };
			for (String key : fbsArr) {
				if (keyword.contains(key)) {
					keyword = keyword.replace(key, "");
				}
			}
		}
		return keyword;
	}
	
	public static String subStr(String src, int startIndex, int endIndex) {
		if (null == src) {
			return "";
		}
		if (startIndex <= 0 && src.equals("")) {
			return "";
		}
		if (startIndex > endIndex) {
			return "";
		}
		if (endIndex >= src.length()) {
			return src;
		}
		if (startIndex>=0 && endIndex<src.length()) {
			return src.substring(startIndex, endIndex);
		}
		return src;
	}
	
	public static void main(String[] args) {
		String str = "2017-01-11 09:49";
		int[] ids = new int[]{1,32,4,13,53,123,13,23,42,21};
		SortedSet<Integer> set = new TreeSet<Integer>();
		for (int i = 0; i < ids.length; i++) {
			set.add(ids[i]);
		}
		Iterator<Integer> it = set.iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}
		
		System.out.println(isTimeLegal(str));
		System.out.println(escapeExprSpecialWord("asd\\as?d*asd*s|ad"));
	}
	
	/**
	 * 截取字符串，按照长度
	 * @param str
	 * @param length
	 * @param replace
	 * @return
	 */
	public static String subStr(String str,int length,String replace){
		String strNew="";
		if(str!=null && !"".equals(str)){
			int valueLength = 0;
			String chinese = "[\u0391-\uFFE5]";
			
			/* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
			for (int i = 0; i < str.length(); i++) {
				/* 获取一个字符 */
				String temp = str.substring(i, i + 1);
				/* 判断是否为中文字符 */
				if (temp.matches(chinese)) {
					/* 中文字符长度为2 */
					valueLength += 2;
				} else {
					/* 其他字符长度为1 */
					valueLength += 1;
				}
				strNew = strNew+temp;
				if(valueLength>(length*2-2)){
					break;
				}
			}
			if(valueLength < (length*2-2)){
				replace="";
			}
		}
		
		return strNew+replace;
	}
	
	public static int length(String value) {
		int valueLength = 0;
		String chinese = "[\u0391-\uFFE5]";
		/* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
		for (int i = 0; i < value.length(); i++) {
			/* 获取一个字符 */
			String temp = value.substring(i, i + 1);
			/* 判断是否为中文字符 */
			if (temp.matches(chinese)) {
				/* 中文字符长度为2 */
				valueLength += 2;
			} else {
				/* 其他字符长度为1 */
				valueLength += 1;
			}
		}
		return valueLength;
	}


	/**
	 * <p>功能描述: 是否是ie浏览器</p>
	 * @param agent
	 * @return:boolean
	 */
	public static boolean isIE(String agent){
		if(isEmpty(agent)) return false;
		agent = agent.toLowerCase();
		return agent.indexOf("msie")>0 || (agent.indexOf("gecko")>0 && agent.indexOf("rv:11")>0);
	}

	/**
	 * <p>功能描述: 从一到十数字转中文字符</p>
	 * @param number
	 * @return:java.lang.String
	 */
	public static String number2China(Integer number){
		String [] ss = {"一","二","三","四","五","六","七","八","九","十"};
		if(number==null||number>10){
			return "";
		}
		return ss[number-1];
	}
	
	public static String path2Url(String prevUrl, String c_path) {
		int index2 = -1;
		if (StringUtil.isNotNullOrEmpty(prevUrl)) {
			index2 = prevUrl.indexOf(".action");
		}
		String url = "";
		if (StringUtil.isNotNullOrEmpty(c_path) && index2 >= 0) {
			url = prevUrl.substring(prevUrl.indexOf(c_path)+c_path.length(),prevUrl.indexOf(".action"));
		}
		return url;
	}
	
	public static boolean isNumeric(String str){
		for(int i=str.length();--i>=0;){
			int chr=str.charAt(i);
			if(chr<48 || chr>57)
				return false;
		}
		return true;
	}

	public static String repUrl(String url){
		url = url.replaceAll("＜","<");
		url = url.replaceAll("＞",">");
		url = url.replaceAll("＆gt;",">");
		url = url.replaceAll("＂","");
		url = url.replaceAll("＆amp;","&");
		url = url.replaceAll("＆","&");
		url = url.replaceAll("\"","'");
		return url;
	}


	/**
	 * <p>功能描述: 时间日期比较</p>
	 * @param date1 日期1
	 * @param date2 日期2
	 * @param format 日期格式 例如"yyyy-MM-dd HH:mm:ss"
	 * @return:java.lang.String -1，出错  1，日期1大于日期2  2，日期2大于日期1  0，日期2等于日期1
	 */
	public static String compareDate(String date1,String date2,String format) {
		DateFormat dateFormat =  new SimpleDateFormat(format);
        Date datec1 = null;
        Date datec2 = null;
		try {
		    datec1 = dateFormat.parse(date1);
		    datec2 = dateFormat.parse(date2);
        }catch (Exception e){
		    return "-1";
        }
		if(datec1.getTime()>datec2.getTime()){
			return "1";
		}else if(datec1.getTime()<datec2.getTime()){
			return "2";
		}else {
            return "0";
        }
	}

	/**
	 * 下划线转驼峰命名
	 */
	public static String toUnderScoreCase(String str) {
		if (str == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		// 前置字符是否大写
		boolean preCharIsUpperCase = true;
		// 当前字符是否大写
		boolean curreCharIsUpperCase = true;
		// 下一字符是否大写
		boolean nexteCharIsUpperCase = true;
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (i > 0) {
				preCharIsUpperCase = Character.isUpperCase(str.charAt(i - 1));
			} else {
				preCharIsUpperCase = false;
			}
			curreCharIsUpperCase = Character.isUpperCase(c);
			if (i < (str.length() - 1)) {
				nexteCharIsUpperCase = Character.isUpperCase(str.charAt(i + 1));
			}
			if (preCharIsUpperCase && curreCharIsUpperCase && !nexteCharIsUpperCase) {
				sb.append(SEPARATOR);
			} else if ((i != 0 && !preCharIsUpperCase) && curreCharIsUpperCase) {
				sb.append(SEPARATOR);
			}
			sb.append(Character.toLowerCase(c));
		}
		return sb.toString();
	}

	/**
	 * 是否包含字符串
	 *
	 * @param str 验证字符串
	 * @param strs 字符串组
	 * @return 包含返回true
	 */
	public static boolean inStringIgnoreCase(String str, String... strs) {
		if (str != null && strs != null) {
			for (String s : strs) {
				if (str.equalsIgnoreCase(trim(s))) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 去空格
	 */
	public static String trim(String str) {
		return (str == null ? "" : str.trim());
	}

	/**
	 * * 判断一个对象数组是否为空
	 *
	 * @param objects 要判断的对象数组
	 ** @return true：为空 false：非空
	 */
	public static boolean isEmpty(Object[] objects) {
		return isNull(objects) || (objects.length == 0);
	}

	/**
	 * * 判断一个对象是否为空
	 *
	 * @param object Object
	 * @return true：为空 false：非空
	 */
	public static boolean isNull(Object object) {
		return object == null;
	}

}
