package org.firstinspires.ftc.teamcode.library;

import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public interface Scoops {

    double FRONT_DOWN = 0;
    double FRONT_TRANFER = 1;
    double FRONT_AVOID = 0.7;
    double BACK_DOWN = 0;
    double BACK_DUMP = 1;

    void init(HardwareMap hm, Telemetry telm);

    void setFrontScoopPos(double pos);

    void frontScoopDown();

    void frontScoopTransfer();

    void setBackScoopPos(double pos);

    void backScoopDown();

    void backScoopDump();
}
