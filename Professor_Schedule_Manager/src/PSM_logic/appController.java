/*
 * ApplicationLogic.java
 *
 * Created on April 12, 2008, 11:03 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package PSM_logic;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ArrayList;
import java.lang.Thread;
import java.util.logging.Level;
import java.util.logging.Logger;

import PSM_interface.InterfaceController;

/**
 *
 * @author lrizo002
 */
public class appController {
    
    private static final long TENMIN = 600000;
    
    private static int hr;
    private static int min;
    private static Calendar autoClear = new GregorianCalendar();
    private static Calendar setRun = new GregorianCalendar();
    private static Timer timer = new FutureTimer();
    private static Date date = new Date();
    private static Date date2 = new Date();
    private static String defSub = "";
    private static String defSemester = "";
    private static String defCourseName = "";
    private static String defCourseStart = "";
    private static String defCourseEnd = "";
    private static String defMonStart = "";
    private static String defMonEnd = "";
    private static String defTueStart = "";
    private static String defTueEnd = "";
    private static String defWedStart = "";
    private static String defWedEnd = "";
    private static String defThuStart = "";
    private static String defThuEnd = "";
    private static String defFriStart = "";
    private static String defFriEnd = "";
    private static String defSatStart = "";
    private static String defSatEnd = "";
    private static String username;
    private static String password;
    private static boolean loggedin;
    private static boolean dataReceived = false;
    private static boolean edSchedSel = false;
    private static boolean schedSetupSel = false;
    private static boolean logoutSel = false;
    private static int clearDate, clearMonth, clearYear;
    private static int counter = 0;
    private static InterfaceController ic;
    private static LogicToDBFacade auth = new LogicToDBFacade();
    private static int courseSel;
    private static long classEnded = 0;
       
    
    /** Creates a new instance of ApplicationLogic */
    public appController() {
        ic = new InterfaceController(this);
    	hr = min = 0;
        
        while(!loggedin)
        {           
            ic.Initiate_Login_Form();            
            
             do
             {
                 dataReceived = ic.log.dataReceived();
                 sleep(300);
                 
             }while(!dataReceived);
             ic.log.setDataRec(false);
             dataReceived = false;
             username = ic.log.getUsername();
             password = ic.log.getPassword();

             
             auth = new LogicToDBFacade();
             if(auth.validate_Login(username,password)){
                 loggedin = true;
                 auth.logout();
                 auth.validate_Login(username, password);
             }
             
               
             if(!loggedin){
                 ic.Initiate_IncorrectLogin();
                 counter++;
                 while(!dataReceived)
                 {
                     dataReceived = ic.msg.ack;
                     System.out.println("in");
                 }
                 dataReceived = false;
                 ic.msg.ack = false;
                 
             }
             if(counter >= 3){
                
                 ic.passwordLock();
                 while(!dataReceived)
                 {
                     dataReceived = ic.msg.ack;
                     
                 }
                 System.exit(0);
              }
        }
 	   
        ic.Initiate_MainMenu();
        if(checkClear())
        {
           auth.clearDatabase();
        }
        
        checkTimes();
        while(!logoutSel)
        {
            while(!dataReceived)
            {
                dataReceived = ic.mm.dataRec();
                edSchedSel = ic.mm.editSchedSelected();
                schedSetupSel = ic.mm.InitSetupSelected();
                logoutSel = ic.mm.logoutSelected();
                                             
                if(classEnded != 0 && System.currentTimeMillis() - classEnded >= TENMIN)
                {
                  System.exit(0);   
                }
                
                sleep(500);
            }
            ic.mm.setdataRec(false);
            dataReceived = false;

            if(logoutSel)
            {
                // Logout 
                auth.logout();
                ic.Initiate_Logout();

            }
            else if(edSchedSel)
            {
                 //Edit Schedule
                ic.Course_Select_Form();

                while(!dataReceived)
                {
                    dataReceived = ic.cs.courseSelected();
                    sleep(300);
                }

                ic.cs.setCourseSelected(false);
                dataReceived = false;

                courseSel = ic.cs.getSelection();
                System.out.println("Course: " + courseSel);
                getData(courseSel);
                
                ic.Pre_Filled_Form(courseSel,defSub,defCourseName,defSemester,defCourseStart,
                        defCourseEnd,defMonStart,defMonEnd,defTueStart,defTueEnd,defWedStart,
                        defWedEnd,defThuStart,defThuEnd,defFriStart,defFriEnd,defSatStart,defSatEnd);
                
                while(!dataReceived)
                {
                    dataReceived = ic.edSched.dataRec(); 
                    sleep(300);
                }
                
                dataReceived = false;
                ic.edSched.setDataRec(false);

                auth.storeClassSched(ic.edSched.defCourseID, ic.edSched.newCourseStart, ic.edSched.newCourseEnd, 
                        ic.edSched.newMonStart, ic.edSched.newMonEnd, ic.edSched.newTueStart, ic.edSched.newTueEnd, 
                        ic.edSched.newWedStart, ic.edSched.newWedEnd, ic.edSched.newThuStart, ic.edSched.newThuEnd, 
                        ic.edSched.newFriStart, ic.edSched.newFriEnd, ic.edSched.newSatStart, ic.edSched.newSatEnd);


            }
            else if(schedSetupSel)
            {
                ic.sched.launchInitial();
                //Initial Schedule Setup
                while(!ic.sched.dataRec())
                {
                    dataReceived = ic.sched.dataRec();
                    sleep(300);
                }
                dataReceived = false;
                ic.sched.setDataRec(false);
                
                auth.storeClassInfo(ic.sched.newCourseID, ic.sched.newSub, ic.sched.newCourseName,ic.sched.newSemester);
                auth.storeClassSched(ic.sched.newCourseID, ic.sched.newCourseStart, ic.sched.newCourseEnd, 
                        ic.sched.newMonStart, ic.sched.newMonEnd, ic.sched.newTueStart, ic.sched.newTueEnd, 
                        ic.sched.newWedStart, ic.sched.newWedEnd, ic.sched.newThuStart, ic.sched.newThuEnd, 
                        ic.sched.newFriStart, ic.sched.newFriEnd, ic.sched.newSatStart, ic.sched.newSatEnd);

            }

            dataReceived = false;
        }
        
    }
    
   
    public static boolean checkClear()
    {
        ArrayList<String> endDates = auth.getEndDates();
        Calendar endCal = new GregorianCalendar();
        Calendar now = Calendar.getInstance();
        
        for(int i = 0; i < endDates.size(); i++)
        {
            dateParser(endDates.get(i));

            endCal.set(clearYear + 2000, clearMonth-1, clearDate);
            if(now.compareTo(endCal) <= 0)
               return false;
                
        }
        return true;
                
    }
            
    public static void checkTimes()
    {
        ArrayList<Integer> courseList = auth.getCourses();
        
        Calendar tempC = new GregorianCalendar();
        int currentDay = tempC.get(tempC.DAY_OF_WEEK);
        tempC.setTimeInMillis(System.currentTimeMillis());
        Date fifteenMin;
        Date fiveMin;
        Date endClass;
        
        for(int i = 0; i < courseList.size(); i++)
        {
            boolean isNull = true;
            getData(courseList.get(i).intValue());
            Timer newTimer = new FutureTimer();
            
            if(currentDay == 2 && defMonEnd.compareTo("") != 0)
            {
                timerParser(defMonEnd);
                isNull = false;
            }
            else if(currentDay == 3 && defTueEnd.compareTo("") != 0)
            {
                timerParser(defTueEnd);
                isNull = false;
            }
            else if(currentDay == 4 && defWedEnd.compareTo("") != 0)
            {
                timerParser(defWedEnd);
                isNull = false;
            }
            else if(currentDay == 5 && defThuEnd.compareTo("") != 0)
            {
                timerParser(defThuEnd);
                isNull = false;
            }
            else if(currentDay == 6 && defFriEnd.compareTo("") != 0)
            {
                timerParser(defFriEnd);
                isNull = false;
            }
            
            else if(currentDay == 7 && defSatEnd.compareTo("") != 0)
            {
                
                timerParser(defSatEnd);
                isNull = false;
            }
            
            
            if(!isNull){
                fiveMin = get5BeforeEnd(hr, min);
                newTimer.schedule(popup5min, fiveMin);
                fifteenMin = get15BeforeEnd(hr, min);              
                newTimer.schedule(popup15min, fifteenMin);
                endClass = getEndTime(hr, min);
                newTimer.schedule(endofclass, endClass);
            }
        }   
       
    }
    
    public static void getData(int course)
    {
    	ArrayList<String> schedule = auth.getData(course);
        defSub = schedule.get(0);
        defSemester = schedule.get(1);
        defCourseName = schedule.get(2);
        defCourseStart = schedule.get(3);
        defCourseEnd = schedule.get(4);
        defMonStart = schedule.get(5);
        defMonEnd = schedule.get(6);
        defTueStart = schedule.get(7);
        defTueEnd = schedule.get(8);
        defWedStart = schedule.get(9);
        defWedEnd = schedule.get(10);
        defThuStart = schedule.get(11);
        defThuEnd = schedule.get(12);
        defFriStart = schedule.get(13);
        defFriEnd = schedule.get(14);
        defSatStart = schedule.get(15);
        defSatEnd = schedule.get(16);           
    }
    
    
    public static void sleep(int milli)
    {
          try { 
           Thread.sleep(milli);
        } catch (InterruptedException ex) {
           Logger.getLogger(appController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static TimerTask dbClear = new TimerTask()
    {
        public void run()
        {
           auth.clearDatabase();
        }
    };
    
    private static TimerTask popup15min = new TimerTask()
    {
        public void run()
        {
            ic.msg.FifteenMinWarning();
        }
    };
	
    private static TimerTask popup5min = new TimerTask()
    {
        public void run()
        {
            ic.msg.FiveMinWarning();
        }
    };
    
    
    private static TimerTask endofclass = new TimerTask()
    {
        public void run()
        {
            ic.msg.endClassWarning();
            classEnded = System.currentTimeMillis();
        }
    };
    
    
    private static TimerTask systemExit = new TimerTask()
    {
        public void run()
        {
            System.exit(0);
        }
    };
    
       
    public void setTime(int year, int month, int date, int hours, int mins)
    {
        setRun.set(year, month, date, hours, mins);
    }
    
    public Date getTime()
    {
        return setRun.getTime();
    }
    
    public static long getTimeMillis()
    {
        return setRun.getTimeInMillis();
    }
    
    public static void timerParser(String timer)
    {
        hr = Integer.parseInt(timer.substring(0,2));
        min = Integer.parseInt(timer.substring(3,5));
    }
    public static void dateParser(String timer)
    {
        
        clearMonth = Integer.parseInt(timer.substring(0,2));
        clearDate = Integer.parseInt(timer.substring(3,5));
        clearYear = Integer.parseInt(timer.substring(6,8));
    }
    
    public int returnHr()
    {
        return hr;
    }
    
    public int returnMin()
    {
        return min;
    }
    
    public static Date getEndTime(int hrs, int mins)
    {
        int years, months, dates, dayOfWeek;
        
        Calendar tempC = new GregorianCalendar();
        
        tempC.setTimeInMillis(System.currentTimeMillis());
        
        years = tempC.get(tempC.YEAR);
        months = tempC.get(tempC.MONTH);
        dates = tempC.get(tempC.DATE);
        dayOfWeek = tempC.get(tempC.DAY_OF_WEEK);
        tempC.set(years, months, dates, hrs, mins - 1, 1);
        return tempC.getTime();
    }
    
    public static void setSemesterClear(int year, int month, int date, int hours, int mins)
    {
        autoClear.set(year, month, date, hours, mins);
        date2 = autoClear.getTime();
    }
    
    public Date getSemesterClear()
    {
        return autoClear.getTime();
    }
    
    public static Date get15BeforeEnd(int hrs, int mins)
    {
        int years, months, dates, dayOfWeek; 
        
        Calendar tempC = new GregorianCalendar();
        
        tempC.setTimeInMillis(System.currentTimeMillis());
        
        years = tempC.get(tempC.YEAR);
        months = tempC.get(tempC.MONTH);
        dates =  tempC.get(tempC.DATE);
        dayOfWeek = tempC.get(tempC.DAY_OF_WEEK);
        
        tempC.set(years, months, dates, hrs, mins - 15, 1);
        
        return tempC.getTime();
    }
    
    public static Date get5BeforeEnd(int hrs, int mins)
    {
        int years, months, dates, dayOfWeek;
        
        Calendar tempC = new GregorianCalendar();
        
        tempC.setTimeInMillis(System.currentTimeMillis());
        
        years = tempC.get(tempC.YEAR);
        months = tempC.get(tempC.MONTH);
        dates = tempC.get(tempC.DATE);
        dayOfWeek = tempC.get(tempC.DAY_OF_WEEK);
        tempC.set(years, months, dates, hrs, mins - 5, 1);
        return tempC.getTime();
    }
    
    public void autoExit()
    {
        timer.schedule(systemExit, date);
    }
    
    public void autoClear()
    {
        timer.schedule(dbClear, date2);
    }
    
    public String fetchCourses()
    {
    	return auth.fetchCourses();
    }
}
