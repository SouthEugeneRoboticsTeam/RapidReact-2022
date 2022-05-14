package org.sert2521.rapidreact2022.subsytems

import com.revrobotics.CANSparkMax
import com.revrobotics.SparkMaxRelativeEncoder
import edu.wpi.first.math.controller.PIDController
import edu.wpi.first.math.controller.SimpleMotorFeedforward
import edu.wpi.first.math.filter.LinearFilter
import edu.wpi.first.wpilibj2.command.SubsystemBase
import org.sert2521.rapidreact2022.*
import java.lang.System.currentTimeMillis
import kotlin.math.abs

object Shooter : SubsystemBase() {
    private val motorFront = CANSparkMax(Sparks.SHOOTER.id, Sparks.SHOOTER.type)
    private val motorBack = CANSparkMax(Sparks.SHOOTER_BACK.id, Sparks.SHOOTER_BACK.type)
    private val motorBackEncoder = motorBack.getEncoder(SparkMaxRelativeEncoder.Type.kQuadrature, SparkEncodersQuadrature.SHOOTER_BACK.encoderPulsesPerRev)

    private val motorFrontPID = PIDController(robotPreferences.shooterPID[0], robotPreferences.shooterPID[1], robotPreferences.shooterPID[2])
    private val motorFrontFeedForward = SimpleMotorFeedforward(robotPreferences.shooterFeedForward[0], robotPreferences.shooterFeedForward[1], robotPreferences.shooterFeedForward[2])
    private var motorFrontGoal = 0.0
    private var motorFrontStopped = false

    private val motorBackPID = PIDController(robotPreferences.shooterBackPID[0], robotPreferences.shooterBackPID[1], robotPreferences.shooterBackPID[2])
    private val motorBackFeedForward = SimpleMotorFeedforward(robotPreferences.shooterBackFeedForward[0], robotPreferences.shooterBackFeedForward[1], robotPreferences.shooterBackFeedForward[2])
    private var motorBackGoal = 0.0
    private var motorBackStopped = false

    private var speedFrontAverage = LinearFilter.movingAverage(robotPreferences.shooterAveragePoints)
    private var currentSpeedFrontAverage = 0.0
    private var speedBackAverage = LinearFilter.movingAverage(robotPreferences.shooterAveragePoints)
    private var currentSpeedBackAverage = 0.0
    private var prevTime = currentTimeMillis() / 1000.0
    private var prevSpeedFront = 0.0
    private var prevSpeedBack = 0.0

    init {
        motorFront.inverted = Sparks.SHOOTER.reversed
        motorBack.inverted = Sparks.SHOOTER_BACK.reversed

        motorFront.enableVoltageCompensation(DEFAULT_VOLTAGE)
        motorBack.enableVoltageCompensation(DEFAULT_VOLTAGE)

        motorFront.encoder.positionConversionFactor = SparkEncodersHall.SHOOTER.conversionFactor
        motorBackEncoder.positionConversionFactor = SparkEncodersQuadrature.SHOOTER_BACK.conversionFactor
    }

    private fun getFrontSpeed(power: Double): Double {
        return motorFrontFeedForward.calculate(motorFrontGoal * power) + motorFrontPID.calculate(wheelSpeedFront, motorFrontGoal * power)
    }

    private fun getBackSpeed(power: Double): Double {
        return motorBackFeedForward.calculate(motorBackGoal * power) + motorBackPID.calculate(wheelSpeedBack, motorBackGoal * power)
    }

    override fun periodic() {
        val deltaTime = (currentTimeMillis() / 1000.0) - prevTime
        val deltaSpeed = abs(prevSpeedFront - wheelSpeedFront)
        val deltaSpeedBack = abs(prevSpeedBack - wheelSpeedBack)

        currentSpeedFrontAverage = speedFrontAverage.calculate(deltaSpeed / deltaTime)
        currentSpeedBackAverage = speedBackAverage.calculate(deltaSpeedBack / deltaTime)

        prevTime = currentTimeMillis() / 1000.0
        prevSpeedFront = wheelSpeedFront
        prevSpeedBack = wheelSpeedBack

        val power = Input.getShootPower()
        if(!motorFrontStopped) {
            motorFront.setVoltage(getFrontSpeed(power))
        }

        if(!motorBackStopped) {
            motorBack.setVoltage(getBackSpeed(power))
        }
    }

    val wheelSpeedFront
        get() = motorFront.encoder.velocity

    val wheelSpeedBack
        get() = -motorBackEncoder.velocity

    fun getAverageFrontSpeed(): Double {
        return currentSpeedFrontAverage
    }

    fun getAverageSpeedBack(): Double {
        return currentSpeedBackAverage
    }

    fun setWheelSpeedFront(rpm: Double) {
        motorFrontGoal = rpm
        motorFrontPID.reset()
        motorFrontStopped = false
    }

    fun setWheelSpeedBack(rpm: Double) {
        motorBackGoal = rpm
        motorBackPID.reset()
        motorBackStopped = false
    }

    fun stop() {
        motorFront.stopMotor()
        motorBack.stopMotor()
        motorFrontStopped = true
        motorBackStopped = true
    }
}