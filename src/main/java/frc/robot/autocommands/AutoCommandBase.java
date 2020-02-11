package frc.robot.autocommands;

import java.util.logging.Level;
import java.util.logging.Logger;

//import org.usfirst.frc.team548.robot.AutoCommands.AutoCommandBase;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

public abstract class AutoCommandBase {
    private boolean done = false;
    protected double timeOut;
    protected Timer timer;
    private static DriverStation ds = DriverStation.getInstance();

    public AutoCommandBase(double timeOut) {
        this.timeOut = timeOut;
        timer = new Timer();
    }
    
    public abstract void init();
    protected abstract void run();
    public abstract void end();
    
    public void execute() {
    	System.out.println("Starting command "+this.getCommandName());
    	this.init();
        timer.start();
        while(!done && !hasTimedOut() && (!ds.isDisabled() && !ds.isOperatorControl())) {
            this.run();
            try {
                Thread.sleep(5);
            } catch (InterruptedException ex) {
                Logger.getLogger(AutoCommandBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("Ending command "+this.getCommandName());
        this.end();
    }
    
    public boolean hasTimedOut() {
        return timeOut <= timer.get();
    }
    
    public boolean isDone(){ return this.done;}
    public void setDone(boolean d) {
        this.done = d;
    }
    
    protected abstract String getCommandName();
    
}