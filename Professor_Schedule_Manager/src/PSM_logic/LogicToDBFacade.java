package PSM_logic;

import PSM_storage.DBConnection;
import java.util.ArrayList;

public class LogicToDBFacade {
    private ArrayList<String> schedule = new ArrayList<String>();
	private DBConnection db;
    
    public LogicToDBFacade() {
        db = new DBConnection();     
    }
    
    public boolean validate_Login(String username, String password)
    {
        int state; 
        state = db.connect(username,password);
        if(state == 0)
            return true;
        else
            return false;   
    }
    
    public boolean logout()
    {
        int state;
        state = db.disconnect();
        if(state == 0)
            return true;
        else
            return false;
    }
    
    public void clearDatabase(){
    	db.clearDatabase();
    }
    
    public void storeClassSched(int defCourseID, String newCourseStart, String newCourseEnd, 
            String newMonStart, String newMonEnd, String newTueStart, String newTueEnd, 
            String newWedStart, String newWedEnd, String newThuStart, String newThuEnd, 
            String newFriStart, String newFriEnd, String newSatStart, String newSatEnd)
    {
    	db.storeClassSched(defCourseID, newCourseStart, newCourseEnd, 
                newMonStart, newMonEnd, newTueStart, newTueEnd, 
                newWedStart, newWedEnd, newThuStart, newThuEnd, 
                newFriStart, newFriEnd, newSatStart, newSatEnd);
    }
    
    public void storeClassInfo(int newCourseID, String newSub, String newCourseName,String newSemester)
    {
    	db.storeClassInfo(newCourseID, newSub, newCourseName,newSemester);
    }
    
    public ArrayList<String> getEndDates(){
    	return db.getEndDates();
    }
    
    public ArrayList<Integer> getCourses(){
    	return db.getCourses();
    }
    
    public ArrayList<String> getData(int course)
    {
    	System.out.println("Course in Facade: " + course);
        schedule.add(db.fetchCourseSubj(course));
        schedule.add(db.fetchCourseSemester(course));
        schedule.add(db.fetchCourseName(course));
        schedule.add(db.fetchCourseStart(course));
        schedule.add(db.fetchCourseEnd(course));
        schedule.add(db.fetchStartMon(course));
        schedule.add(db.fetchEndMon(course));
        schedule.add(db.fetchStartTue(course));
        schedule.add(db.fetchEndTue(course));
        schedule.add(db.fetchStartWed(course));
        schedule.add(db.fetchEndWed(course));
        schedule.add(db.fetchStartThu(course));
        schedule.add(db.fetchEndThu(course));
        schedule.add(db.fetchStartFri(course));
        schedule.add(db.fetchEndFri(course));
        schedule.add(db.fetchStartSat(course));
        schedule.add(db.fetchEndSat(course)); 
        return this.schedule;
    }
    
    public String fetchCourses()
    {
    	return db.fetchCourses();
    }
}
