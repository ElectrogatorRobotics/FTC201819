package org.firstinspires.ftc.teamcode.library;

import com.qualcomm.robotcore.hardware.HardwareMap;

public interface LandingGear {

    double LEGS_OUT = 0.15;
    double LEGS_STRAIGHT = 0.25;
    double LEGS_RETRACT = 0.825;

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
    void init(HardwareMap hm);

    /**
     * Returns the value of what the server was set for and should match one of the values above
     *
     * @return double of the servo's set angle
     */
    double getState();

}
