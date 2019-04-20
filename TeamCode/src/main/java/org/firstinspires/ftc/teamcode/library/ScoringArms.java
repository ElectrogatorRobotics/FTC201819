package org.firstinspires.ftc.teamcode.library;

import com.qualcomm.robotcore.hardware.HardwareMap;

public interface ScoringArms {
    void initScoringSystems (HardwareMap hardwareMap);

    void setFrontTargetPosition(double angle);

    void waitForMoveEnd();

    void setScoringArmServoPosition (boolean cycle);

    void setIntakeMotorPower (double power);

    void setIntakeArmMotorPower (double power);

    int getIntakeArmPosition();
}
