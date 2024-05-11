package Items.Character;

import GUISharedObject.CollidableEntity;
import GUISharedObject.Entity;
import Games.Config;
import Games.GameController;
import Items.Interfaces.Attackable;
import Items.Interfaces.Walkable;
import Items.Interfaces.WeatherEffectable;
import Games.Config.*;

public abstract class BaseCharacter extends CollidableEntity implements Walkable, Attackable, WeatherEffectable {
    private float speedRate;
    private final float MAXSPEEDRATE;
    private int attackRange;
    private float damage;
    private int attackCooldown;

    public BaseCharacter(double positionX, double positionY, int maxSpeedRate, int attackRange, float damage) {
        super(positionX,positionY,0,0);
        this.MAXSPEEDRATE = Math.max(2,maxSpeedRate);
        this.attackRange = Math.max(2,attackRange);
        this.damage = Math.max(2,damage);
    }

    public BaseCharacter(int maxSpeedRate, int attackRange, float damage) {
        super();
        this.MAXSPEEDRATE = Math.max(2,maxSpeedRate);
        this.attackRange = Math.max(2,attackRange);
        this.damage = Math.max(2,damage);
    }

    @Override
    public void weatherEffected() {
        Weather weatherNow = GameController.getInstance().getClock().getWeather();
        if(weatherNow == Weather.SUNNY){
            setSpeedRate((float) 0.6 * MAXSPEEDRATE);
        } else if (weatherNow == Weather.RAINY) {
            setSpeedRate((float) 0.7 * MAXSPEEDRATE);
        }else if (weatherNow == Weather.SNOWY){
            setSpeedRate((float) 0.5 * MAXSPEEDRATE);
        }
    }

    public void setX(double x) { this.x = Math.max(0,Math.min(x, Config.GAMESCREENWIDTH)); }
    public void setY(double y) {
        this.y = Math.max(0,Math.min(y,Config.GAMESCREENHEIGHT));
    }

    public int getAttackCooldown() { return attackCooldown; }

    public void setAttackCooldown(int attackCooldown) {
        this.attackCooldown = Math.max(0,attackCooldown);
    }

    @Override
    public float getSpeedRate() { return speedRate; }

    @Override
    public void setSpeedRate(float speedRate) {
        this.speedRate = Math.max(0,Math.min(speedRate, MAXSPEEDRATE));
    }

    @Override
    public int getAttackRange() {
        return attackRange;
    }

    public void setAttackRange(int attackRange) {
        this.attackRange = attackRange;
    }

    public abstract void attack();

    @Override
    public void walk() {
        return;
    }
    public float getMAXSPEEDRATE() {
        return MAXSPEEDRATE;
    }

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }
}
