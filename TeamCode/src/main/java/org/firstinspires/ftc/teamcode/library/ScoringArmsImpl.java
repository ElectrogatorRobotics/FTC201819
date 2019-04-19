package org.firstinspires.ftc.teamcode.library;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

public class ScoringArmsImpl implements ScoringArms {
    public DcMotorSimple intakeMotor = null;
    public DcMotorEx intakeArmMotor = null;
    public Servo scoringArmServo = null;
    public TouchSensor scoringArmLimit = null;
    private ElapsedTime runtime = new ElapsedTime();

    double motorPower = 0;
    boolean cycling = false;

    double BACK_CYCLE_TIME = 1100;

    double TICKS_PER_DEGREE = 13.333;

    public void initScoringSystems (HardwareMap hardwareMap) {
        intakeArmMotor = (DcMotorEx) hardwareMap.dcMotor.get("intake arm motor");
        intakeArmMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        intakeArmMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intakeArmMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        intakeMotor = hardwareMap.get(DcMotorSimple.class,"intake motor");
        intakeMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        scoringArmServo = hardwareMap.servo.get("scoring arm servo");
        scoringArmServo.scaleRange(0.0, 0.75);
        scoringArmServo.setDirection(Servo.Direction.REVERSE);

        scoringArmLimit = hardwareMap.touchSensor.get("scoring arm limit");
    }

    public void setFrontTargetPosition(double angle){
        int ticks = (int) Math.round(TICKS_PER_DEGREE * angle);
        intakeArmMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        intakeArmMotor.setTargetPositionTolerance(10);
        intakeArmMotor.setTargetPosition(ticks);
        runtime.reset();
        setIntakeMotorPower(.5);
    }

    public void waitForMoveEnd(){
        while(intakeArmMotor.isBusy() && runtime.milliseconds() < 1500){
            Thread.yield();
        }
        intakeArmMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void setScoringArmServoPosition (boolean cycle) {
        if (cycle){
            scoringArmServo.setPosition(0);
            runtime.reset();
            cycling = true;
        } else{
            scoringArmServo.setPosition(.37);
        }
    }

    public void checkCycling() {
        if (cycling){
            if(runtime.milliseconds() > BACK_CYCLE_TIME ){
                setScoringArmServoPosition(false);
                cycling=false;
            }
        }
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
