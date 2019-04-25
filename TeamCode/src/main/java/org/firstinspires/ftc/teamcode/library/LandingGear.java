package org.firstinspires.ftc.teamcode.library;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.library.Drive;

public interface LandingGear {


    /**
     * Sets the landing gear up on the robot
     */
    void init(DriveV2 driveV2, Drive drivetrain, LinearOpMode lop);

    /**
     * Used to stand the robot vertical on the deployed legs to unhook from the rover
     */
    void stand_up();

    /**
     * Used to fold the legs under the robot. Should stagger set the wheels so they do not collide.
     */
    void retract();

    /**
     * Used to stick the legs out at a slight angle. good for stability and running.
     */
    void deploy();

    /**
     * Returns the value of what the server was set for and should match one of the values above
     *
     * @return double of the servo's set angle
     */
    void unhook() throws InterruptedException;

}
