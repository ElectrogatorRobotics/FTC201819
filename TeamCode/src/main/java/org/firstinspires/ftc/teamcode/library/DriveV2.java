package org.firstinspires.ftc.teamcode.library;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public interface DriveV2 {
    Orientation angle = null;
    enum driveServoState {RETRACT, STRAIGHT, DRIVE}

    /**
     * initialise the drivetrain motors and servos
     * @param hardwareMap
     */
    void initDrive(HardwareMap hardwareMap);

    /**
     * set the state of the servos on the "legs"
     * @param state
     */
    driveServoState driveServoState(driveServoState state);

    /**
     * set the power of all drive motors to the same value
     * @param speed
     */
    void setDriveSpeed(double speed);

    /**
     * set the power of each drive motor to a unique value
     * @param frontRight
     * @param backRight
     * @param frontLeft
     * @param backLeft
     */
    void setDriveSpeed(double frontRight, double backRight, double frontLeft, double backLeft);

    /**
     * initialise the the lift servos and motors
     * @param hardwareMap
     */
    void initLift(HardwareMap hardwareMap);

    /**
     * set the position of the scoring lift servo
     * @param liftPosition
     */
    void setLiftPosition(double liftPosition);
}
