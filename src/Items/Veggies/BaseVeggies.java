package Items.Veggies;

import GUISharedObject.Entity;
import GUISharedObject.RenderableHolder;
import Games.Config;
import Games.GameController;
import Items.Interfaces.Collectable;
import Items.Interfaces.WeatherEffectable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

public abstract class BaseVeggies extends Entity implements WeatherEffectable, Collectable {
    private boolean isCollected;
    private float growthPoint;
    private final float MAXGROWTHPOINT;
    private float growthRate;
    private final float MAXGROWTHRATE;
    private float Hp;
    private float waterPoint;
    private float waterDroppingRate;
    private final float MAXWATERDROPPINGRATE;
    private final float MAXWATER;
    private final int MAXHP;
    private final int width;
    private final int height;

    public BaseVeggies(int hp, float maxWater, float growthRate, float waterDroppingRate, int maxGrowthPoint){
        super();
        MAXHP = hp;
        setHp(MAXHP);
        MAXWATER = maxWater;
        setWaterPoint(MAXWATER);
        MAXGROWTHRATE = growthRate;
        setGrowthRate(MAXGROWTHRATE);
        MAXWATERDROPPINGRATE = waterDroppingRate;
        setWaterDroppingRate(MAXWATERDROPPINGRATE);
        spawnOnMap();
        setGrowthPoint(0);
        MAXGROWTHPOINT = maxGrowthPoint;
        z = getZ() + 600;
        width = 40;
        height = 40;
    }

    @Override
    public void spawnOnMap() {
        double posX = Config.SPAWNLEFTBOUND + Math.random() * (Config.SPAWNRIGHTBOUND - Config.SPAWNLEFTBOUND);
        double posY = Math.random() * Config.GAMESCREENHEIGHT;
        while (!GameController.getInstance().isPositionAccesible(posX,posY,getWidth(),getHeight(),false)) {
            posX = ((float)Math.random()*100)* Config.GAMESCREENWIDTH/100;
            posY = ((float)Math.random()*100)*Config.GAMESCREENHEIGHT/100;
            System.out.println("Veggie cannot be spawn here. Find new pos...");
        }
        setX(posX);
        setY(posY);
        setCollected(false);
    }

    @Override
    public void collected() {
        if(this.getGrowthPoint()<MAXGROWTHPOINT) return;
        GameController.getInstance().collectVeggie(this);
        RenderableHolder.collectSound.play();
    }

    @Override
    public boolean isCollected() { return this.isCollected; }

    @Override
    public void weatherEffected() {
        Config.Weather weather = GameController.getInstance().getClock().getWeather();
        if( weather == Config.Weather.SUNNY ) {
            this.setGrowthRate(MAXGROWTHRATE * (float) 0.5);
            this.setWaterDroppingRate(MAXWATERDROPPINGRATE);
        } else if( weather == Config.Weather.SNOWY ) {
            this.setGrowthRate(MAXGROWTHRATE * (float) 0.2);
            this.setWaterDroppingRate(MAXWATERDROPPINGRATE * (float) 0.4);
        } else if( weather == Config.Weather.RAINY) {
            this.setGrowthRate(MAXGROWTHRATE * (float) 0.7);
            this.setWaterDroppingRate(MAXWATERDROPPINGRATE * (float) 0.0);
        }
    }

    @Override
    public void draw(GraphicsContext gc) {
        if(this instanceof RedFlower){
            gc.drawImage(RenderableHolder.redFlowerIdleSprite, getX() - (double) getWidth() /2, getY() - (double) getHeight() /2, getWidth(), getHeight());
        }
        if(this instanceof RainbowDrake){
            gc.drawImage(RenderableHolder.rainbowDrakeIdleSprite, getX() - (double) getWidth() /2, getY() - (double) getHeight() /2, getWidth(), getHeight());
        }
        if(this instanceof Daffodil){
            gc.drawImage(RenderableHolder.daffodilIdleSprite, getX() - (double) getWidth() /2, getY() - (double) getHeight() /2, getWidth(), getHeight());
        }

        gc.setStroke(Color.GREEN);
        gc.setLineWidth(2);
        float growthDegree = ((float) MAXGROWTHPOINT - getGrowthPoint())/MAXGROWTHPOINT * 360;
        gc.strokeArc(getX() - getWidth(), getY() - getHeight(),
                getWidth() * 2, getHeight() * 2,
                0,growthDegree, ArcType.OPEN );

        // Calculate the width of the progress bar
        double HPPercentage = (double) getHp() / getMAXHP(); // Get HP percentage
        double HPBarWidth = 30 * HPPercentage; // Calculate progress bar width

        // Draw the progress bar
        double HPBarX = getX() - 17; // Start of progress bar
        double HPBarY = getY() + (double) getHeight() / 2; // Position below the circle

        gc.setFill(Color.GRAY);
        gc.fillRect(HPBarX, HPBarY, 30, 5);
        gc.setFill(Color.ORANGERED);
        gc.fillRect(HPBarX, HPBarY, HPBarWidth, 5);


        // Calculate the width of the water bar
        double waterPercentage = (double) getWaterPoint() / getMAXWATER(); // Get HP percentage
        double waterBarWidth = 30 * waterPercentage; // Calculate progress bar width

        // Draw the progress bar
        double waterBarX = getX() - 17; // Start of progress bar
        double waterBarY = getY() + (double) getHeight() / 2 + 8; // Position below the circle

        gc.setFill(Color.GRAY);
        gc.fillRect(waterBarX, waterBarY, 30, 5);
        gc.setFill(Color.CORNFLOWERBLUE);
        gc.fillRect(waterBarX, waterBarY, waterBarWidth, 5);
    }

    public int getWidth() { return width; }

    public int getHeight() { return height; }

    public float getGrowthRate() { return growthRate; }

    public void setGrowthRate(float growthRate) { this.growthRate = Math.min(growthRate, MAXGROWTHRATE); }

    public void setCollected(boolean collected) { isCollected = collected; }

    public float getHp() { return Hp; }

    public void setHp(float hp) { Hp = hp; }

    public float getWaterPoint() { return waterPoint; }

    public void setWaterPoint(float waterPoint) { this.waterPoint = waterPoint; }

    public float getGrowthPoint() { return growthPoint; }

    public void setGrowthPoint(float growthPoint) { this.growthPoint = Math.min(growthPoint, MAXGROWTHPOINT); }

    public float getWaterDroppingRate() { return waterDroppingRate; }

    public void setWaterDroppingRate(float waterDroppingRate) { this.waterDroppingRate = waterDroppingRate; }

    public int getMAXHP() {
        return MAXHP;
    }
    public float getMAXWATER() {
        return MAXWATER;
    }
}
