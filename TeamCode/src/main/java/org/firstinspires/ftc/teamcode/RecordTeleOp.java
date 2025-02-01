package org.firstinspires.ftc.teamcode;

import android.annotation.SuppressLint;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@TeleOp(name = "RecordTeleOp", group = "Robot")
public class RecordTeleOp extends OpMode {

    public DcMotor BL, BR, FR, FL, SL, SR, UL,UR;
    public boolean d_up, d_down, d_left, d_right;
    public Servo SWL,SWR,SC,SB;
    boolean a_pressed = false;  // Keep track of whether the button was toggled or not
    boolean last_a_state = false;
    boolean b_pressed = false;
    boolean last_b_state = false;
    private List<InputLog> logData;
    private long startTime;
    boolean ran = false;
    boolean ran2 = true;

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

        BL.setDirection(DcMotor.Direction.REVERSE);
        BR.setDirection(DcMotor.Direction.REVERSE);
        FR.setDirection(DcMotor.Direction.REVERSE);
        FL.setDirection(DcMotor.Direction.REVERSE);

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
        d_up = gamepad1.dpad_up;
        d_down = gamepad1.dpad_down;
        d_right = gamepad1.dpad_right;
        d_left = gamepad1.dpad_left;
        boolean current_a_state = gamepad1.a;
        boolean current_b_state = gamepad1.b;


        if (d_up) {
            setMotors(1, 1, 1, 1);  // forward
        } else if (d_down) {
            setMotors(-1, -1, -1, -1);  // down
        } else if (d_left) {
            setMotors(-1, 1, -1, 1);  // left
        } else if (d_right) {
            setMotors(1, -1, 1, -1);  // right
        } else {
            setMotors(0, 0, 0, 0);
        }

        if(gamepad1.left_bumper) {
            setMotors(-1,1,1,-1);
        }

        if(gamepad1.right_bumper) {
            setMotors(1,-1,-1,1);
        }

        SL.setPower(gamepad1.right_stick_y);
        SR.setPower(gamepad1.right_stick_y);

        UL.setPower(gamepad1.left_stick_y*2);
        UR.setPower(gamepad1.left_stick_y*2);

        if(gamepad1.right_trigger > .2) {
            SWL.setPosition(.96);
            SWR.setPosition(.04);
        }

        if(gamepad1.left_trigger > .2) {
            SWL.setPosition(.4);
            SWR.setPosition(.6);
        }

        if (current_a_state && !last_a_state) {  // If the A button was just pressed
            a_pressed = !a_pressed;  // Toggle the claw
        }
        if (current_b_state && !last_b_state) {  // If the A button was just pressed
            b_pressed = !b_pressed;  // Toggle the claw
        }

        if (a_pressed) {
            SC.setPosition(0);  // Open the claw
        } else {
            SC.setPosition(0.3);  // Close the claw
        }

        if (b_pressed) {
            SB.setPosition(0.1);  // up the basket // higher down // lower up
        } else {
            SB.setPosition(0.7);  // down the basket
        }

        last_a_state = current_a_state;
        last_b_state = current_b_state;

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
        log.timestamp = (System.currentTimeMillis() - startTime);

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

            File filePath = new File(directory, "log_file.txt");


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
        // Write the logs to a plain text file when recording stops
        //File directory = new File("/sdcard/FIRST"); // "/sdcard/FIRST" maybe idk

        //if(!directory.exists()) {
        //    directory.mkdirs();
        //}

        //File filePath = new File(directory, "log_file.txt");


        //try (FileWriter writer = new FileWriter(filePath)) {
        //    for (InputLog log : logData) {
        //        writer.write(String.format("Timestamp: %d, BLPower: %.2f, BRPower: %.2f, FRPower: %.2f, FLPower: %.2f, SLPower: %.2f, SRPower: %.2f, SWLPos: %.2f, SWRPos: %.2f, SCPos: %.2f, SBPos: %.2f, ULPower: %.2f, URPower: %.2f\n",
        //                log.timestamp, log.BLPower, log.BRPower, log.FRPower, log.FLPower, log.SLPower, log.SRPower, log.SWLPos, log.SWRPos, log.SCPos, log.SBPos, log.ULPower, log.URPower));
        //    }
        //    telemetry.addData("Status: ", "Recording saved to %s", filePath);
        //} catch (IOException e) {
        //    telemetry.addData("Error: ", "Failed Recording" + e.getMessage());
        //}
        //telemetry.update();
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
        FL.setPower(fl*.7);
        FR.setPower(fr*.7);
        BR.setPower(br*.7);
        BL.setPower(bl*.7);
    }
}
