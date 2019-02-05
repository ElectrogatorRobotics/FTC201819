package org.firstinspires.ftc.teamcode.library;

import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public interface Scoops {

    double FRONT_DOWN = 0.2;
    double FRONT_TRANSFER = 0.8;
    double FRONT_AVOID = 0.7;
    double BACK_DOWN = 1;
    double BACK_DUMP = 0;

    void init(HardwareMap hm, Telemetry telm);

    void setFrontScoopPos(double pos);

    void frontScoopDown();

    void frontScoopTransfer();

    void frontScoopCycle ();

    void setBackScoopPos(double pos);

    void backScoopDown();

    void backScoopDump();

    void backScoopCycle();
}
