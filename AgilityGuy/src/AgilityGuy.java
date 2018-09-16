import java.awt.Graphics2D;

import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.GroundItem;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.event.WalkingEvent;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;


/*
 * THINGS TO DO:
 * create world hopper if someone is chopping there
 * create walk method??
 * add walker to get more hatchets
 * add powerdrop option
 */


@ScriptManifest(author = "Wittyname123", info = "DraynorAgility", name = "DraynorAgility", version = 1, logo = "https://ia.media-imdb.com/images/M/MV5BNjM3MjQxNjkxM15BMl5BanBnXkFtZTgwMDIyMTA0NDE@._V1_UX182_CR0,0,182,268_AL_.jpg")
public class AgilityGuy extends Script {
    private long startTime;
    private int beginningXp;
    private int beginningLevel;
    private long timeTNL;
    private int currentXp;
    private int currentLevel;
    private int xpGained;
    private int xpPerHour;
    private double nextLevelXp;
    private double xpTillNextLevel;
    private int levelsGained;
    private int check;
    
    final int[] XP_TABLE =
        {
              0, 0, 83, 174, 276, 388, 512, 650, 801, 969, 1154,
              1358, 1584, 1833, 2107, 2411, 2746, 3115, 3523, 3973, 4470, 5018,
              5624, 6291, 7028, 7842, 8740, 9730, 10824, 12031, 13363, 14833,
              16456, 18247, 20224, 22406, 24815, 27473, 30408, 33648, 37224,
              41171, 45529, 50339, 55649, 61512, 67983, 75127, 83014, 91721,
              101333, 111945, 123660, 136594, 150872, 166636, 184040, 203254,
              224466, 247886, 273742, 302288, 333804, 368599, 407015, 449428,
              496254, 547953, 605032, 668051, 737627, 814445, 899257, 992895,
              1096278, 1210421, 1336443, 1475581, 1629200, 1798808, 1986068,
              2192818, 2421087, 2673114, 2951373, 3258594, 3597792, 3972294,
              4385776, 4842295, 5346332, 5902831, 6517253, 7195629, 7944614,
              8771558, 9684577, 10692629, 11805606, 13034431, 200000000
        };
    
    
    
    public void Antiban() throws InterruptedException {
    	switch (random(1, 15)) {
    	case 1:
    		getCamera().moveYaw(100 + (random(1, 68)));
    		break;
    	case 2:
    		getCamera().movePitch(50 + (random(1, 69)));
    		break;
    	case 3:
    		getMouse().moveOutsideScreen();
    		sleep(random(756, 2854));
    		break;
    	case 4:
    		getMouse().moveOutsideScreen();
    		sleep(random(1153,8439));
    	case 5: 
    		getMouse().moveOutsideScreen();
    		sleep(random(2023,11233));
    	}	
    	sleep(random(1753,2563));
    }
    
	@Override
	public void onStart() throws InterruptedException {
		log("Let's get started!");
        startTime = System.currentTimeMillis();
        beginningXp = skills.getExperience(Skill.AGILITY);
        beginningLevel = skills.getStatic(Skill.AGILITY);
        timeTNL = 0;
        getTabs().open(Tab.INVENTORY);
	}
    
	
	@Override
	public int onLoop() throws InterruptedException {
		
		RS2Object firstWall = getObjects().closest(10073);
		RS2Object secondWall = getObjects().closest(10074);
		RS2Object thirdWall = getObjects().closest(10075);
		RS2Object fourthWall = getObjects().closest(10077);
		RS2Object fifthWall = getObjects().closest(10084);
		RS2Object sixthWall = getObjects().closest(10085);
		RS2Object finalWall = getObjects().closest(10086);
		GroundItem marks = getGroundItems().closest(11849);
		Position pos1 = new Position(3104, 3260, 0);
		Position pos2 = new Position(3104, 3279, 0);
		check = 0;
		if (marks != null) {
			marks.interact("Take");
		} else if (check == 0 && firstWall != null && !myPlayer().isAnimating()) {
			firstWall.interact("Climb");
			sleep(random(3000,4500));
			check = 1;
			
		} else if (check == 1 && secondWall != null && !myPlayer().isAnimating()){
			secondWall.interact("Cross");
			sleep(random(7000,8000));
			check = 2;
		} else if (check == 2 && thirdWall != null && !myPlayer().isAnimating()){
			thirdWall.interact("Cross");
			sleep(random(6000,7000));
			check = 3;
		} else if (check == 3 && fourthWall != null && !myPlayer().isAnimating()){
			fourthWall.interact("Balance");
			sleep(random(2000,3000));
			check = 4;
		} else if (check == 4 && fifthWall != null && !myPlayer().isAnimating()) {
			fifthWall.interact("Jump-up");
			sleep(random(1000,1500));
			check = 5;
		} else if (check == 5 && sixthWall != null && !myPlayer().isAnimating()) {
			sixthWall.interact("Jump");
			sleep(random(1500,2000));
			check = 6;
		} else if (check == 6 && finalWall != null && !myPlayer().isAnimating()) {
			finalWall.interact("Climb-down");
			sleep(random(2600,3200));
			WalkingEvent myEvent = new WalkingEvent(pos2);
			execute(myEvent);
			sleep(random(2000,3000));
			check = 7;
		} else {
			Antiban();
		}
		
		
		
		
		return random(200, 300);
	}

	@Override
	public void onExit() {
		log("Script End");
	}

    // BG Image
    private final Image bg = getImage("https://i.imgur.com/jphDGCa.png");
    private Image getImage(String url)
    {
      try
      {
        return ImageIO.read(new URL(url));
      }
      catch (IOException e) {}
      return null;
    }
	
	
	
    // Paint Method
    @Override
    public void onPaint(Graphics2D g) {
        g.setFont(new Font("Comic Sans", 0, 12));
        g.setColor(new Color(255, 255, 255));
        
        long runTime = System.currentTimeMillis() - startTime;   
        currentXp = skills.getExperience(Skill.AGILITY);
        currentLevel = skills.getStatic(Skill.AGILITY);
        
        xpGained = currentXp - beginningXp;
        xpPerHour = (int)( xpGained / ((System.currentTimeMillis() - this.startTime) / 3600000.0D));
        nextLevelXp = XP_TABLE[currentLevel + 1];
        xpTillNextLevel = nextLevelXp - currentXp;
        if (xpGained >= 1)
        {
            timeTNL = (long) ((xpTillNextLevel / xpPerHour) * 3600000);
        }
        
        currentLevel = skills.getStatic(Skill.AGILITY);
        levelsGained = currentLevel - beginningLevel;
         
        g.drawImage(bg, 5, 345, null);
        g.drawString("" + ft(timeTNL), 425, 435);     
        g.drawString("" + currentLevel, 477, 449);
        g.drawString("" + levelsGained, 480, 463);
        g.drawString(ft(runTime), 210, 375);
        g.drawString("" + xpPerHour, 319, 449);
        g.drawString("" + xpGained, 304, 463);
        
        Point mP = getMouse().getPosition();
        // Draw a line from top of screen (0), to bottom (500), with mouse x coordinate
        g.drawLine(mP.x, 0, mP.x, 500);
        // Draw a line from left of screen (0), to right (800), with mouse y coordinate
        g.drawLine(0, mP.y, 800, mP.y);
    }
    
    private String ft(long duration)
    {
            String res = "";
            long days = TimeUnit.MILLISECONDS.toDays(duration);
            long hours = TimeUnit.MILLISECONDS.toHours(duration)
            - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(duration));
            long minutes = TimeUnit.MILLISECONDS.toMinutes(duration)
            - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS
            .toHours(duration));
            long seconds = TimeUnit.MILLISECONDS.toSeconds(duration)
            - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
            .toMinutes(duration));
            if (days == 0) {
            res = (hours + ":" + minutes + ":" + seconds);
            } else {
            res = (days + ":" + hours + ":" + minutes + ":" + seconds);
            }
            return res;
    }
}
    
    
    
 

  