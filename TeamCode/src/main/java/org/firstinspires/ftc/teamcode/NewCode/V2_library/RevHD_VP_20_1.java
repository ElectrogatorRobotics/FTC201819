package org.firstinspires.ftc.teamcode.NewCode.V2_library;

import com.qualcomm.robotcore.hardware.configuration.annotations.DeviceProperties;
import com.qualcomm.robotcore.hardware.configuration.annotations.MotorType;

import org.firstinspires.ftc.robotcore.external.navigation.Rotation;

@MotorType(ticksPerRev = 1120, gearing = 20, maxRPM = 300, orientation = Rotation.CCW)
@DeviceProperties(xmlTag = "rev hd vp 20:1", name = "@string/rev_hd_vp_20_name", builtIn = false)

public interface RevHD_VP_20_1 {
}
