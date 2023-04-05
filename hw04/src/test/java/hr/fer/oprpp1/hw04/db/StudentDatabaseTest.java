package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StudentDatabaseTest {
	
	StudentDatabase db;
	List<String> lines;
	
	IFilter alwaysTrue = (sr) -> true;
	IFilter alwaysFalse = (sr) -> false;
	
	@BeforeEach
	public void beforeeach() {
		lines = new ArrayList<>();
		lines.add("0000000054	Šamija	Pavle	3");
		lines.add("0000000012	Franković	Hrvoje	5");
		lines.add("0000000029	Kos-Grabar	Ivo	2");
		lines.add("0000000046	Rajtar	Robert	3");
		
		db = new StudentDatabase(lines);
	}
	
	@Test
	public void testForJMBAGThatExists() {
		assertEquals(new StudentRecord("0000000029", "Kos-Grabar", "Ivo", "2"), db.forJMBAG("0000000029"));
	}
	
	@Test
	public void testForJMBAGThatDoesNotExist() {
		assertEquals(null, db.forJMBAG("1230050029"));
	}
	
	@Test
	public void testFilterMethodReturnsAllRecordsWhenGivenAlwaysTrueFilter() {
		List<StudentRecord> expected = new ArrayList<>();
		expected.add(new StudentRecord("0000000054", "Šamija", "Pavle", "3"));
		expected.add(new StudentRecord("0000000012", "Franković", "Hrvoje", "5"));
		expected.add(new StudentRecord("0000000029", "Kos-Grabar", "Ivo", "2"));
		expected.add(new StudentRecord("0000000046", "Rajtar", "Robert", "3"));
		
		assertEquals(expected, db.filter(alwaysTrue));
	}
	
	@Test
	public void testFilterMethodReturnsNoRecordsWhenGivenAlwaysFalseFilter() {
		assertEquals(new ArrayList<String>(), db.filter(alwaysFalse));
	}
	
}
