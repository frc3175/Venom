package frc.robot.automodes;

import frc.robot.autocommands.AutoCommandBase;
import frc.robot.autocommands.DriveDistance;
import frc.robot.autocommands.DriveWithIntake;
import frc.robot.autocommands.HopperSpin;
import frc.robot.autocommands.IntakeDown;
import frc.robot.autocommands.IntakeUp;
import frc.robot.autocommands.LimelightAlign;
import frc.robot.autocommands.LimelightAlignFast;
import frc.robot.autocommands.LimelightDistance;
import frc.robot.autocommands.OneSideTurn;
import frc.robot.autocommands.OneSideTurnLeft;
import frc.robot.autocommands.ShootBall;
import frc.robot.autocommands.ShootWithAlign;
import frc.robot.autocommands.ShootWithLimelight;
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

    protected void turnToAngle(double seconds, double angle, double power) {
        runCommand(new TurnToAngle(seconds, angle, power));
    }

    protected void rightSideTurn(double seconds, double leftPower, double rightPower, double distance) {
        runCommand(new OneSideTurn(seconds, leftPower, rightPower, distance));
    }

    protected void waitTime(double seconds) {
        runCommand(new Wait(seconds));
    }

    protected void shootBall(double seconds, double hopperPower) {
        runCommand(new ShootBall(seconds, hopperPower));
    }

    protected void hopperSpin(double seconds, double hopperPower) {
        runCommand(new HopperSpin(seconds, hopperPower));
    }

    protected void driveWithIntake(double seconds, double power, double distance) {
        runCommand(new DriveWithIntake(seconds, power, distance));
    }

    protected void intakeDown(double seconds) {
        runCommand(new IntakeDown(seconds));
    }

    protected void leftsideTurn(double seconds, double leftPower, double rightPower, double distance) {
        runCommand(new OneSideTurnLeft(seconds, leftPower, rightPower, distance));
    }

    protected void limelightAlign(double seconds) {
        runCommand(new LimelightAlign(seconds));
    }

    protected void intakeUp(double seconds) {
        runCommand(new IntakeUp(seconds));
    }

    protected void limelightDistance(double seconds) {
        runCommand(new LimelightDistance(seconds));
    }

    protected void limelightAlignFast(double seconds) {
        runCommand(new LimelightAlignFast(seconds));
    }

    protected void shootWithAlign(double seconds, double hopperPower) {
        runCommand(new ShootWithAlign(seconds, hopperPower));
    }

    protected void shootWithLimelight(double seconds, double hopperPower) {
        runCommand(new ShootWithLimelight(seconds, hopperPower));
    }

    private void runCommand(AutoCommandBase command) {
        command.execute();
    }

}