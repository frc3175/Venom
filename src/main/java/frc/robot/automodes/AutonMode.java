package frc.robot.automodes;

import frc.robot.autocommands.AutoCommandBase;
import frc.robot.autocommands.DriveDistance;
import frc.robot.autocommands.OneSideTurn;
import frc.robot.autocommands.TurnToAngle;
import frc.robot.autocommands.Wait;

public abstract class AutonMode {
    private String autonName;

    public void start() {
        run();
    }

    protected abstract void run();

    protected void driveDistance(double seconds, double power, double distance) {
        runCommand(new DriveDistance(seconds, power, distance));
    }

    protected void turnToAngle(double seconds, double angle, double offset, double power) {
        runCommand(new TurnToAngle(seconds, angle, offset, power));
    }

    protected void doubleSidePower(double seconds, double leftPower, double rightPower) {
        runCommand(new OneSideTurn(seconds, leftPower, rightPower));
    }

    protected void waitTime(double seconds) {
        runCommand(new Wait(seconds));
    }

    private void runCommand(AutoCommandBase command) {
        command.execute();
    }

}