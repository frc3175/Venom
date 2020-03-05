package frc.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;

public class RobotInit {

    private static RobotInit instance;
    private static UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
    
    // Creates an instance
    public static RobotInit getInstance() {
        if (instance == null)
            instance = new RobotInit();
        return instance;
    }

    public static void init() {
        
        Limelight.changePipeline(1);
        camera.setResolution(200, 200);

    }
}