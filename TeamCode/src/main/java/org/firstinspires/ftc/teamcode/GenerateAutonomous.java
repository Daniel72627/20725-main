package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Autonomous(name = "GenerateAutonomous", group = "Robot")
public class GenerateAutonomous extends OpMode {

    public DcMotor BL, BR, FR, FL, SL, SR, UL,UR;
    public Servo SWL,SWR,SC,SB;
    private List<InputLog> logData;
    private int currentLogIndex = 0;
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

        //---------------------------------------

        logData = new ArrayList<>();
        telemetry.addData("Status", "Initialized. Replay.");

        // Read log file
        try (BufferedReader reader = new BufferedReader(new FileReader("/sdcard/FIRST/log_file.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                InputLog log = new InputLog();
                log.timestamp = Long.parseLong(parts[0].split(":")[1].trim());
                log.BLPower = Double.parseDouble(parts[1].split(":")[1].trim());
                log.BRPower = Double.parseDouble(parts[2].split(":")[1].trim());
                log.FRPower = Double.parseDouble(parts[3].split(":")[1].trim());
                log.FLPower = Double.parseDouble(parts[4].split(":")[1].trim());
                log.SLPower = Double.parseDouble(parts[5].split(":")[1].trim());
                log.SRPower = Double.parseDouble(parts[6].split(":")[1].trim());
                log.SWLPos = Double.parseDouble(parts[7].split(":")[1].trim());
                log.SWRPos = Double.parseDouble(parts[8].split(":")[1].trim());
                log.SCPos = Double.parseDouble(parts[9].split(":")[1].trim());
                log.SBPos = Double.parseDouble(parts[10].split(":")[1].trim());
                log.ULPower = Double.parseDouble(parts[11].split(":")[1].trim());
                log.URPower = Double.parseDouble(parts[12].split(":")[1].trim());
                logData.add(log);
            }
        } catch (IOException e) {
            telemetry.addData("Error", "Failed to load log file: " + e.getMessage());
        }
    }

    @Override
    public void start() {
        startTime = System.currentTimeMillis();
    }

    @Override
    public void loop() {
        //replay
        if (currentLogIndex < logData.size()) {
            InputLog log = logData.get(currentLogIndex);


            long elapsedTime = System.currentTimeMillis() - startTime;
            if (elapsedTime >= log.timestamp) {

                BL.setPower(log.BLPower);
                BR.setPower(log.BRPower);
                FR.setPower(log.FRPower);
                FL.setPower(log.FLPower);
                SL.setPower(log.SLPower);
                SR.setPower(log.SRPower);
                SWL.setPosition(log.SWLPos);
                SWR.setPosition(log.SWRPos);
                SC.setPosition(log.SCPos);
                SB.setPosition(log.SBPos);
                UL.setPower(log.ULPower);
                UR.setPower(log.URPower);
                currentLogIndex++;
            }
        } else {

            stop();
        }


        telemetry.addData("Replaying", "Timestamp: %d ms", System.currentTimeMillis() - startTime);
        telemetry.addData("BL Power", BL.getPower());
        telemetry.addData("BR Power", BR.getPower());
        telemetry.addData("FR Power", FR.getPower());
        telemetry.addData("FL Power", FL.getPower());
        telemetry.addData("SL Power", SL.getPower());
        telemetry.addData("SR Power", SR.getPower());
        telemetry.addData("SWL Pos", SWL.getPosition());
        telemetry.addData("SWR Pos", SWR.getPosition());
        telemetry.addData("SC Pos", SC.getPosition());
        telemetry.addData("SB Pos", SB.getPosition());
        telemetry.addData("UL Power", UL.getPower());
        telemetry.addData("UR Power", UR.getPower());
        telemetry.update();
    }

    @Override
    public void stop() {

        BL.setPower(0);
        BR.setPower(0);
        FR.setPower(0);
        FL.setPower(0);
        SL.setPower(0);
        SR.setPower(0);
        UL.setPower(0);
        UR.setPower(0);
        telemetry.addData("Status", "Autonomous Replay Finished");
        telemetry.update();
    }


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
}
