package PSM_logic;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

public class AppControllerTest {
	private AppController app;
	
	@Before
	public void setUp() throws Exception {
		this.app = new AppController();
	}

	@After
	public void tearDown() throws Exception {
		this.app = null;
	}

	@Test
	public void testCheckClear() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetTime() {
		app.setTime(1989,2,21,2,30);
		String str = "Tue Mar 21 02:30";
		assertEquals("setTimeYear", str, app.getTime().toString().substring(0, str.length()));
	}

	@Test
	public void testGetTime() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetTimeMillis() {
		fail("Not yet implemented");
	}

	@Test
	public void testTimerParser() {
		app.timerParser("02:30");
		assertEquals("timerParserMin", 2, app.returnHr());
		assertEquals("timerParserMin", 30, app.returnMin());
	}
	
	@Test
	public void testTimerParser2() {
		app.timerParser("ABCDE");
		assertEquals("timerParserMin", true, app.returnHr()<0);
		assertEquals("timerParserMin", true, app.returnMin()<0);
	}

	@Test
	public void testDateParserLessThan() {
		app.auth.endDate = "10/21/11";
		assertEquals("checkClearNowLessThanEnd", false, app.checkClear());
	}
	
	@Test
	public void testDateParserEqual() {
		app.auth.endDate = "09/22/11";
		assertEquals("checkClearNowEqualEnd", false, app.checkClear());
	}

	@Test
	public void testDateParserMoreThan11() {
		app.auth.endDate = "03/21/11";
		assertEquals("checkClearNowMoreThanEnd11", true, app.checkClear());
	}
	
	@Test
	public void testDateParserMoreThan89() {
		app.auth.endDate = "03/21/89";
		assertEquals("checkClearNowMoreThanEnd89", true, app.checkClear());
	}
	
	
	@Test
	public void testReturnMin2() {
		assertFalse("min",61 == app.returnMin());
	}


	@Test
	public void testSetSemesterClear() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetSemesterClear() {
		fail("Not yet implemented");
	}

	@Test
	public void testGet15BeforeEnd() {
		Date date = app.get15BeforeEnd(20, 15);
		String str = date.toString();
		assertEquals("testGet15BeforeEnd","Thu Sep 22 20:00", str.substring(0, 16));
	}
	@Test
	public void testGet15BeforeEnd2() {
		Date date = app.get15BeforeEnd(00, 12);
		String str = date.toString();
		assertEquals("testGet15BeforeEnd","Wed Sep 21 23:57", str.substring(0, 16));
	}

	@Test
	public void testGet5BeforeEnd() {
		fail("Not yet implemented");
	}

	@Test
	public void testAutoExit() {
		fail("Not yet implemented");
	}

	@Test
	public void testAutoClear() {
		fail("Not yet implemented");
	}

	@Test
	public void testFetchCourses() {
		fail("Not yet implemented");
	}

}
