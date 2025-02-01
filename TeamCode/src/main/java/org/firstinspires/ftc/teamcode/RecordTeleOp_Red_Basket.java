package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@TeleOp(name = "RecordTeleOpTest_Red_Basket", group = "Robot")
public class RecordTeleOp_Red_Basket extends OpMode {

    public DcMotor BL, BR, FR, FL, SL, SR, UL,UR;
    public Servo SWL,SWR,SC,SB;
    private List<InputLog> logData;
    private long startTime;

    @Override
    public void init() {
        // Initialize motors
        BL = hardwareMap.get(DcMotor.class, "BL");
        BR = hardwareMap.get(DcMotor.class, "BR");
        FR = hardwareMap.get(DcMotor.class, "FR");
        FL = hardwareMap.get(DcMotor.class, "FL");

        SL = hardwareMap.get(DcMotor.class, "LH");
        SR = hardwareMap.get(DcMotor.class, "RH");

        UL = hardwareMap.get(DcMotor.class, "4b");
        UR = hardwareMap.get(DcMotor.class, "4b2");

        SWL = hardwareMap.get(Servo.class, "Lc");
        SWR = hardwareMap.get(Servo.class, "Rc");
        SC = hardwareMap.get(Servo.class, "L");
        SB = hardwareMap.get(Servo.class, "R");

        BL.setDirection(DcMotor.Direction.FORWARD);
        BR.setDirection(DcMotor.Direction.FORWARD);
        FR.setDirection(DcMotor.Direction.FORWARD);
        FL.setDirection(DcMotor.Direction.FORWARD);

        SL.setDirection(DcMotor.Direction.REVERSE);
        SR.setDirection(DcMotor.Direction.REVERSE);

        SWL.setDirection(Servo.Direction.FORWARD);
        SWR.setDirection(Servo.Direction.FORWARD);
        SC.setDirection(Servo.Direction.FORWARD);
        SB.setDirection(Servo.Direction.FORWARD);

        UL.setDirection(DcMotor.Direction.FORWARD);
        UR.setDirection(DcMotor.Direction.FORWARD);

        logData = new ArrayList<>();
        telemetry.addData("Status", "Initialized Yupi");
    }

    @Override
    public void start() {
        // Record start time for timestamp calculation
        startTime = System.currentTimeMillis();
    }

    @Override
    public void loop() {

        SL.setPower(gamepad1.left_stick_x);
        SR.setPower(gamepad1.left_stick_x);

        FR.setPower(gamepad1.right_stick_y/2);
        BR.setPower(gamepad1.right_stick_y/2);

        FL.setPower(gamepad1.left_stick_y/2);
        BL.setPower(gamepad1.left_stick_y/2);

        UL.setPower(gamepad2.right_stick_y);
        UR.setPower(gamepad2.right_stick_y);

        // -----------------------------
        // Arm

        if(gamepad1.b) {
            SWL.setPosition(.96);
            SWR.setPosition(.04);
        }
        if(gamepad1.a) {
            SWL.setPosition(.4);
            SWR.setPosition(.6);
        }

        //-------------------------------------------------
        // Basket

        if(gamepad2.dpad_up) {
            SB.setPosition(.1); // basket
        }
        if(gamepad2.dpad_down) {
            SB.setPosition(.7); // basket
        }

        //---------------------------------------
        // Claw

        if(gamepad1.x) {
            SC.setPosition(0);
        }
        if(gamepad1.y) {
            SC.setPosition(.3);
        }

        //------------------------------------------------------------------------------------
        // Side to side
        if(gamepad1.left_bumper) {
            setMotors(1, -1,1,-1);
        }
        if(gamepad1.right_bumper) {
            setMotors(-1,1,-1,1);
        }

        //---------------------------------------------------------LEAVE THIS ALONE
        double BLPower = BL.getPower();
        double BRPower = BR.getPower();
        double FRPower = FR.getPower();
        double FLPower = FL.getPower();

        double SLPower = SL.getPower();
        double SRPower = SR.getPower();

        double SWLPos = SWL.getPosition();
        double SWRPos = SWR.getPosition();
        double SCPos = SC.getPosition();
        double SBPos = SB.getPosition();

        double ULPower = UL.getPower();
        double URPower = UL.getPower();

        InputLog log = new InputLog();
        log.timestamp = System.currentTimeMillis() - startTime;

        log.BLPower = BLPower;
        log.BRPower = BRPower;
        log.FRPower = FRPower;
        log.FLPower = FLPower;

        log.SLPower = SLPower;
        log.SRPower = SRPower;

        log.SWLPos = SWLPos;
        log.SWRPos = SWRPos;
        log.SCPos = SCPos;
        log.SBPos = SBPos;

        log.ULPower = ULPower;
        log.URPower = URPower;

        logData.add(log);

        telemetry.addData("Recording", "Timestamp: %d ms", log.timestamp);
        telemetry.addData("BL Power", BLPower);
        telemetry.addData("BR Power", BRPower);
        telemetry.addData("FR Power", FRPower);
        telemetry.addData("FL Power", FLPower);
        telemetry.addData("SL Power", SLPower);
        telemetry.addData("SR Power", SRPower);
        telemetry.addData("SWL Power", SWLPos);
        telemetry.addData("SWR Power", SWRPos);
        telemetry.addData("SC", SCPos);
        telemetry.addData("SB", SBPos);
        telemetry.addData("ULPower", ULPower);
        telemetry.addData("URPower", URPower);

        telemetry.update();

        if(log.timestamp <= 30000) {
            File directory = new File("/sdcard/FIRST"); // "/sdcard/FIRST" maybe idk

            if(!directory.exists()) {
                directory.mkdirs();
            }

            File filePath = new File(directory, "red_basket.txt");


            try (FileWriter writer = new FileWriter(filePath)) {
                for (InputLog log2 : logData) {
                    writer.write(String.format("Timestamp: %d, BLPower: %.2f, BRPower: %.2f, FRPower: %.2f, FLPower: %.2f, SLPower: %.2f, SRPower: %.2f, SWLPos: %.2f, SWRPos: %.2f, SCPos: %.2f, SBPos: %.2f, ULPower: %.2f, URPower: %.2f\n",
                            log2.timestamp, log2.BLPower, log2.BRPower, log2.FRPower, log2.FLPower, log2.SLPower, log2.SRPower, log2.SWLPos, log2.SWRPos, log2.SCPos, log2.SBPos, log2.ULPower, log2.URPower));
                }
                telemetry.addData("Status: ", "Recording saved to %s", filePath);
            } catch (IOException e) {
                telemetry.addData("Error: ", "Failed Recording" + e.getMessage());
            }
            telemetry.update();

        }

    }

    public void stop() {

    }

    // Helper class to store input log entries
    private static class InputLog {
        long timestamp;
        double BLPower;
        double BRPower;
        double FRPower;
        double FLPower;
        double SLPower;
        double SRPower;
        double SWLPos;
        double SWRPos;
        double SCPos;
        double SBPos;
        double ULPower;
        double URPower;
    }

    public void setMotors(double fl, double fr, double br, double bl) {
        FL.setPower(fl/2);
        FR.setPower(fr/2);
        BR.setPower(br/2);
        BL.setPower(bl/2);
    }
}
