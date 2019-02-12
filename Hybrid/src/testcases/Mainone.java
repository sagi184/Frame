package testcases;

import java.util.List;

import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.collections.Lists;

public class Mainone {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new TestListenerAdapter();
		TestNG testng = new TestNG();
		List<String> suites = Lists.newArrayList();
		suites.add(System.getProperty("user.dir") + "\\" + "Execution.xml");
		testng.setTestSuites(suites);
		testng.run();

	}
}
