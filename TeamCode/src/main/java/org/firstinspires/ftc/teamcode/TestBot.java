package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;


@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "TEST BOTTT :(", group = "Utilities")
public class TestBot extends LinearOpMode {

    public DcMotor BL, BR, FR, FL, SL, SR, UL,UR;
    public Servo SWL,SWR,SC,SB;


    @Override
    public void runOpMode() {


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


        moveForward(100000);

    }


    public void moveForward(int time) {
        FL.setPower(1);
        FR.setPower(1);
        BR.setPower(1);
        BL.setPower(1);
    }

    public void moveBackward(int time) {
        FL.setPower(-1);
        FR.setPower(-1);
        BR.setPower(-1);
        BL.setPower(-1);
    }

    public void moveLeft(int time) {
        FL.setPower(-1);
        FR.setPower(1);
        BR.setPower(-1);
        BL.setPower(1);
    }

    public void moveRight(int time) {
        FL.setPower(1);
        FR.setPower(-1);
        BR.setPower(1);
        BL.setPower(-1);
    }

    public void turnLeft(int time) {
        FL.setPower(1);
        FR.setPower(-1);
        BR.setPower(1);
        BL.setPower(-1);
    }

    public void turnRight(int time) {
        FL.setPower(-1);
        FR.setPower(1);
        BR.setPower(-1);
        BL.setPower(1);
    }

    public void closeClaw() {
        SC.setPosition(0);
    }

    public void openClaw() {
        SC.setPosition(.3);
    }

    public void downArm() {
        SWL.setPosition(.96);
        SWR.setPosition(.04);
    }

    public void upArm() {
        SWL.setPosition(.4);
        SWR.setPosition(.6);
    }

    public void upBasket() {
        SB.setPosition(.1);
    }

    public void downBasket() {
        SB.setPosition(.7);
    }

    public void four_up(int time) {
        UL.setPower(1);
        UR.setPower(1);
        sleep(time);
        UL.setPower(0);
        UR.setPower(0);

    }

    public void four_down(int time) {
        UL.setPower(-1);
        UR.setPower(-1);
        sleep(time);
        UL.setPower(0);
        UR.setPower(0);

    }

    public void clawOut(int time) {
        SL.setPower(.6);
        SR.setPower(.6);
        sleep(time);
        SL.setPower(0);
        SR.setPower(0);

    }

    public void clawIn(int time) {
        SL.setPower(-.6);
        SR.setPower(-.6);
        sleep(time);
        SL.setPower(0);
        SR.setPower(0);

    }








}

