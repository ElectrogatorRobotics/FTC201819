package org.firstinspires.ftc.teamcode.library;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.motors.RevRobotics20HdHexMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.library.DriveV2_Impl;

/**
 * Created by cameron.czekai on 11/1/2017.
 * edited by mira.eschliman in 18
 */

public class DriveImpl implements Drive {

    Telemetry LOG;

    private static final double DRIVE_POWER = .3;
    private static final double TURN_POWER = .35;
    private static final double TURN_THRESHOLD = .5;

    private LinearOpMode lom;
    private DriveV2 drive2;

    /**
     * This is the minimum power that the drive train can move
     */

    private static final int MAX_SPEED = (6000 / 360);
    public static final double MIN_SPEED = 0.4;

    /**
     * Calculate the number of ticks per inch of the wheel
     * <p>
     * (Wheel diameter * PI) *  ticks per wheel regulation
     * wheel diameter = 4 inches
     * ticks per revolution of wheel = 7 counts per motor revolution * 20 gearbox reduction (20:1)
     */
    public static final double ENCODER_COUNTS_PER_INCH = (28 * 20) / (Math.PI * 4);

    public enum MoveMethod {STRAIGHT, TURN, SLIDE, DEPLOY}

    public DriveImpl() {
    }

    public DriveImpl(DriveV2 d2, Telemetry telem, LinearOpMode lop) {
        setTelemetry(telem);
        drive2 = d2;
        lom = lop;
    }

    public void setTelemetry(Telemetry telem) {
        LOG = telem;
    }

    public void passLinearOp(LinearOpMode lop) {
        lom = lop;
    }

    //endregion

    // forward can go backwards????
    public void forward(double inches) {
        int ticks = (int) Math.round(inches * ENCODER_COUNTS_PER_INCH);
        drive2.setTargetTolerance(50);
        drive2.setTargetPosition(ticks);
        drive2.driveByPosition(DRIVE_POWER, lom);
    }

    public void turn(double angle) {
        drive2.turnToAngle(angle, lom);
    }

    public void slide(double inches){
        int ticks = (int) Math.round(inches * ENCODER_COUNTS_PER_INCH);
        drive2.slideOff(ticks, lom);
    }


}
