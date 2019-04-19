package org.firstinspires.ftc.teamcode.library;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public interface DriveV2 {
    Orientation angle = null;
    enum driveServoState {RETRACT, STRAIGHT, DRIVE}
    enum MotorMode {POWER, ENCODER, POSITION}

    /**
     * initialise the drivetrain motors and servos
     * @param hardwareMap
     */
    void initDrive(HardwareMap hardwareMap);

    void init_bno055IMU (HardwareMap hardwareMap);

    void setMotorMode(MotorMode MM);

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

    void setTargetPosition (int targetPosition);

    /**
     * tolerance is measured in encoder ticks
     * @param tolerance
     */
    void setTargetTolerance(int tolerance);

    /**
     * set the power of each drive motor to a unique value
     * @param frontRight
     * @param backRight
     * @param frontLeft
     * @param backLeft
     */
    void setDriveSpeed(double frontRight, double backRight, double frontLeft, double backLeft);

    void stop();

    void slideOff(int targetPosition, LinearOpMode lom);

    void driveByPosition(double power, LinearOpMode lom);

    double turnToAngle (double angleToTurn, LinearOpMode lom);
}
