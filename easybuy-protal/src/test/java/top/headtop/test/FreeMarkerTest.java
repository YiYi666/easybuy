package top.headtop.test;


import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreeMarkerTest {
	public class Student{
		private String stuNo;
		private String stuName;
		private String stuSex;
		public Student() {
		}
		public Student(String stuNo, String stuName, String stuSex) {
			this.stuNo = stuNo;
			this.stuName = stuName;
			this.stuSex = stuSex;
		}
		public String getStuNo() {
			return stuNo;
		}
		public void setStuNo(String stuNo) {
			this.stuNo = stuNo;
		}
		public String getStuName() {
			return stuName;
		}
		public void setStuName(String stuName) {
			this.stuName = stuName;
		}
		public String getStuSex() {
			return stuSex;
		}
		public void setStuSex(String stuSex) {
			this.stuSex = stuSex;
		}
	}

	@Test
	public void testFreemarker() throws Exception {
		// 第1步：创建一个Configration对象
		Configuration configuration = new Configuration(Configuration.getVersion());
		// 第2步：告诉config对象模板文件存放的路径。
		configuration.setDirectoryForTemplateLoading(
				new File("E:\\YaOrange\\JavaEE2\\project_workspace\\easybuy-protal\\src\\main\\webapp\\WEB-INF\\ftl"));
		// 第3步：设置config的默认字符集。一般是utf-8
		configuration.setDefaultEncoding("utf-8");
		Template template = configuration.getTemplate("second.ftl");
		//understand
		Map data=new HashMap<>();
		//集合数据
		List<Student> students=new ArrayList<>();
		students.add(new Student("001", "隔壁老王", "男"));
		students.add(new Student("002", "隔壁三八", "女"));
		students.add(new Student("003", "人妖", "泰国回来的"));
		data.put("students", students);
		//基本数据
		data.put("hello","柳岩");
		
		data.put("cur_time", new Date());
		data.put("demo", new Date());
		Writer fileWriter = new FileWriter("E:\\YaOrange\\JavaEE2\\project\\day09\\second.html");
		template.process(data, fileWriter);
		fileWriter.flush();
		fileWriter.close();
	}
}
