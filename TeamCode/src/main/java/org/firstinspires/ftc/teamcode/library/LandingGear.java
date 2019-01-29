package org.firstinspires.ftc.teamcode.library;

import com.qualcomm.robotcore.hardware.HardwareMap;

public interface LandingGear {

    double LEGS_STRAIGHT = 0.27;
    double LEGS_STAGE = 0.5;
//    double LEGS_STAGE = 0.4;
    double LEGS_RETRACT = 1.0;
//    double LEGS_RETRACT = 0.82;

    double LEGS_BL_DEPLOY = 0.17;
    double LEGS_BR_DEPLOY = 0.0;
//    double LEGS_BR_DEPLOY = 0.2;
    double LEGS_FL_DEPLOY = 0.18;
    double LEGS_FR_DEPLOY = 0.0;
//    double LEGS_FR_DEPLOY = 0.2;

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
     * Setup the servos so they can be actuated in the system
     *
     * @param hm The hardware map file for getting the sensors
     */
    void init(HardwareMap hm, Drive drivetrain);

    /**
     * Returns the value of what the server was set for and should match one of the values above
     *
     * @return double of the servo's set angle
     */
    double getState();

}
