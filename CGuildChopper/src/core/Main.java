package core;
import java.awt.Graphics2D;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Message;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.api.ui.Tab;
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


@ScriptManifest(author = "Wittyname123", info = "Willow Chopper", name = "CGuild W Chopper", version = 1, logo = "https://i.imgur.com/XQF9fo9.png")
public class Main extends Script {
	private int logsCut;
    private long startTime;
    private int beginningXp;
    private int beginningLevel;
    private long timeTNL;
    private int logsPerHour;
    private int currentXp;
    private int currentLevel;
    private int xpGained;
    private int xpPerHour;
    private double nextLevelXp;
    private double xpTillNextLevel;
    private int levelsGained;
    
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
        beginningXp = skills.getExperience(Skill.WOODCUTTING);
        beginningLevel = skills.getStatic(Skill.WOODCUTTING);
        timeTNL = 0;
        getTabs().open(Tab.INVENTORY);
	}
    
	//counting total willow logs
    public void onMessage(Message message) throws java.lang.InterruptedException {
        String txt = message.getMessage();
        if (txt.contains("You get some willow logs.")) {
            logsCut++;
        }
    }
	
	@Override
	public int onLoop() throws InterruptedException {
		
		RS2Object tree = getObjects().closest(1750);
		
		if (getInventory().contains(1519) && inventory.isFull()) {
			
			do {
				getInventory().drop(1519);
				sleep(random(573,984));
			} while(getInventory().contains(1519));
			
		} else if (tree != null && !myPlayer().isAnimating()) {
			sleep(random(1334,2043));
			tree.interact("Chop down");
			sleep(random(1234,5604));
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
        logsPerHour = (int)(logsCut / ((System.currentTimeMillis() - this.startTime) / 3600000.0D));
        
        currentXp = skills.getExperience(Skill.WOODCUTTING);
        currentLevel = skills.getStatic(Skill.WOODCUTTING);
        
        xpGained = currentXp - beginningXp;
        xpPerHour = (int)( xpGained / ((System.currentTimeMillis() - this.startTime) / 3600000.0D));
        nextLevelXp = XP_TABLE[currentLevel + 1];
        xpTillNextLevel = nextLevelXp - currentXp;
        if (xpGained >= 1)
        {
            timeTNL = (long) ((xpTillNextLevel / xpPerHour) * 3600000);
        }
        
        currentLevel = skills.getStatic(Skill.WOODCUTTING);
        levelsGained = currentLevel - beginningLevel;
         
        g.drawImage(bg, 5, 345, null);
        g.drawString("" + ft(timeTNL), 425, 435);     
        g.drawString("" + currentLevel, 477, 449);
        g.drawString("" + levelsGained, 480, 463);
        g.drawString(ft(runTime), 210, 375);
        g.drawString("" + logsCut, 187, 463);
        g.drawString("" + logsPerHour, 202, 449);
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
    
    
    
 

  