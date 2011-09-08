/*
 * FutureTimer.java
 *
 * Created on April 17, 2008, 3:33 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package PSM_logic;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Date;

/**
 *
 * @author lrizo002
 */
public class FutureTimer extends Timer {
    
    public void schedule(TimerTask task, Date myDate)
    {
       
        Calendar cal = new GregorianCalendar();
        cal.setTimeInMillis(System.currentTimeMillis());
        Date currentDate = cal.getTime();
        if (myDate.compareTo(currentDate) < 0)
            return;
        
        super.schedule(task, myDate); 
    }
}
