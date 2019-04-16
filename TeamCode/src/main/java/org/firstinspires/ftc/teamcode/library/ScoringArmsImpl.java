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

    public void initScoringSystems (HardwareMap hardwareMap) {
        intakeArmMotor = (DcMotorEx) hardwareMap.dcMotor.get("intake arm motor");
        intakeArmMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        intakeArmMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        intakeMotor = hardwareMap.get(DcMotorSimple.class,"intake motor");
        intakeMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        scoringArmServo = hardwareMap.servo.get("scoring arm servo");
        scoringArmServo.scaleRange(0.0, 0.95);
        scoringArmServo.setDirection(Servo.Direction.FORWARD);
    }

    public void setScoringArmServoPosition (boolean cycle) {
        if (cycle){
            scoringArmServo.setPosition(0);
        } else scoringArmServo.setPosition(0.5);
    }

    public void setIntakeMotorPower (double power) {
        intakeMotor.setPower(power);
    }

    public void setIntakeArmMotorPower (double power) {
        intakeArmMotor.setPower(power);
    }
}
