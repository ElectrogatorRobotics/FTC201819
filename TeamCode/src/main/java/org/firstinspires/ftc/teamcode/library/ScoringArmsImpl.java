package org.firstinspires.ftc.teamcode.library;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class ScoringArmsImpl implements ScoringArms {
    public DcMotorSimple intakeMotor = null;
    public DcMotorEx intakeArmMotor = null;
    public Servo scoringArmServo = null;
    public TouchSensor scoringArmLimit = null;

    double motorPower = 0;

    public void initScoringSystems (HardwareMap hardwareMap) {
        intakeArmMotor = (DcMotorEx) hardwareMap.dcMotor.get("intake arm motor");
        intakeArmMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        intakeArmMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        intakeMotor = hardwareMap.get(DcMotorSimple.class,"intake motor");
        intakeMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        scoringArmServo = hardwareMap.servo.get("scoring arm servo");
        scoringArmServo.scaleRange(0.0, 0.75);
        scoringArmServo.setDirection(Servo.Direction.REVERSE);

        scoringArmLimit = hardwareMap.touchSensor.get("scoring arm limit");
    }

    public void setScoringArmServoPosition (boolean cycle) {
        if (cycle){
            cycle = false;
            scoringArmServo.setPosition(0);
        } else scoringArmServo.setPosition(.37);
    }

    public void setIntakeMotorPower (double power) {
        intakeMotor.setPower(power);
    }

    public void setIntakeArmMotorPower (double power) {
        if (scoringArmLimit.isPressed()&&power>0)
            motorPower = 0;
        else motorPower = power;

        intakeArmMotor.setPower(motorPower);
    }
}
