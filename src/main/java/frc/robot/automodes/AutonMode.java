package frc.robot.automodes;

import frc.robot.autocommands.AutoCommandBase;
import frc.robot.autocommands.DriveDistance;
import frc.robot.autocommands.DriveWithIntake;
import frc.robot.autocommands.OneSideTurn;
import frc.robot.autocommands.ShootBall;
import frc.robot.autocommands.TurnToAngle;
import frc.robot.autocommands.Wait;

public abstract class AutonMode {

    public void start() {
        run();
    }


    //Creates auton commands
    protected abstract void run();

    protected void driveDistance(double seconds, double power, double distance) {
        runCommand(new DriveDistance(seconds, power, distance));
    }

    protected void turnToAngle(double seconds, double angle, double offset, double power) {
        runCommand(new TurnToAngle(seconds, angle, power));
    }

    protected void doubleSidePower(double seconds, double leftPower, double rightPower, double distance) {
        runCommand(new OneSideTurn(seconds, leftPower, rightPower, distance));
    }

    protected void waitTime(double seconds) {
        runCommand(new Wait(seconds));
    }

    protected void shootBall(double seconds, double hopperPower) {
        runCommand(new ShootBall(seconds, hopperPower));
    }

    protected void driveWithIntake(double seconds, double power, double distance) {
        runCommand(new DriveWithIntake(seconds, power, distance));
    }

    private void runCommand(AutoCommandBase command) {
        command.execute();
    }

}