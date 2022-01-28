package org.sert2521.rapidreact2022.subsytems

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX
import edu.wpi.first.wpilibj.AnalogPotentiometer
import edu.wpi.first.wpilibj.DigitalInput
import edu.wpi.first.wpilibj.Encoder
import edu.wpi.first.wpilibj2.command.SubsystemBase
import org.sert2521.rapidreact2022.*

object Climber : SubsystemBase() {
    private val staticClimberMotor = WPI_TalonSRX(Motors.STATIC_CLIMBER.id)
    private val variableClimberMotor = WPI_TalonSRX(Motors.VARIABLE_CLIMBER.id)
    private val variableActuator = WPI_TalonSRX(Motors.VARIABLE_ACTUATOR.id)

    private val staticDownLimitSwitch = DigitalInput(LimitSwitches.STATIC_CLIMBER_DOWN.id)
    private val staticUpLimitSwitch = DigitalInput(LimitSwitches.STATIC_CLIMBER_UP.id)
    private val variableDownLimitSwitch = DigitalInput(LimitSwitches.VARIABLE_CLIMBER_DOWN.id)
    private val variableUpLimitSwitch = DigitalInput(LimitSwitches.VARIABLE_CLIMBER_UP.id)

    private val staticClimberEncoder = Encoder(Encoders.STATIC_CLIMBER.idA, Encoders.STATIC_CLIMBER.idA, Encoders.STATIC_CLIMBER.reversed, Encoders.STATIC_CLIMBER.encodingType)
    private val variableClimberEncoder = Encoder(Encoders.VARIABLE_CLIMBER.idA, Encoders.VARIABLE_CLIMBER.idA, Encoders.VARIABLE_CLIMBER.reversed, Encoders.VARIABLE_CLIMBER.encodingType)

    private val potentiometer = AnalogPotentiometer(Potentiometers.VARIABLE_CLIMBER_ANGLE.id, Potentiometers.VARIABLE_CLIMBER_ANGLE.maxAngle, Potentiometers.VARIABLE_CLIMBER_ANGLE.zeroAngle)

    init {
        staticClimberMotor.inverted = Motors.STATIC_CLIMBER.reversed
        variableClimberMotor.inverted = Motors.VARIABLE_CLIMBER.reversed
        variableActuator.inverted = Motors.VARIABLE_ACTUATOR.reversed

        staticClimberEncoder.distancePerPulse = Encoders.STATIC_CLIMBER.encoderDistancePerPulse
        variableClimberEncoder.distancePerPulse = Encoders.VARIABLE_CLIMBER.encoderDistancePerPulse

        staticClimberEncoder.setMaxPeriod(Encoders.STATIC_CLIMBER.maxPeriod)
        variableClimberEncoder.setMaxPeriod(Encoders.VARIABLE_CLIMBER.maxPeriod)

        staticClimberEncoder.setMinRate(Encoders.STATIC_CLIMBER.minRate)
        variableClimberEncoder.setMinRate(Encoders.VARIABLE_CLIMBER.minRate)

        staticClimberEncoder.samplesToAverage = Encoders.STATIC_CLIMBER.samples
        variableClimberEncoder.samplesToAverage = Encoders.VARIABLE_CLIMBER.samples

        staticClimberEncoder.reset()
        variableClimberEncoder.reset()
    }

    val staticHeight
        get() = staticClimberEncoder.distance

    val variableHeight
        get() = variableClimberEncoder.distance

    val variableAngle
        get() = potentiometer.get()

    fun isAtBottomStatic(): Boolean {
        return staticDownLimitSwitch.get()
    }

    fun isAtTopStatic(): Boolean {
        return staticUpLimitSwitch.get()
    }

    fun isAtBottomVariable(): Boolean {
        return variableDownLimitSwitch.get()
    }

    fun isAtTopVariable(): Boolean {
        return variableUpLimitSwitch.get()
    }

    override fun periodic() {
        if(isAtBottomStatic()) {
            staticClimberEncoder.reset()
        }

        if(isAtBottomVariable()) {
            variableClimberEncoder.reset()
        }
    }

    fun elevateStatic(speed: Double): Boolean {
        return if((isAtBottomStatic() && speed > 0) || (isAtTopStatic() && speed < 0)) {
            staticClimberMotor.stopMotor()
            true
        }else{
            staticClimberMotor.set(speed)
            false
        }
    }

    fun elevateVariable(speed: Double): Boolean {
        return if((isAtBottomVariable() && speed > 0) || (isAtTopVariable() && speed < 0)) {
            variableClimberMotor.stopMotor()
            true
        }else{
            variableClimberMotor.set(speed)
            false
        }
    }

    fun actuateVariable(speed: Double): Boolean {
        return if((MIN_CLIMBER_ANGLE < variableAngle && speed < 0) || (MAX_CLIMBER_ANGLE > variableAngle && speed > 0)) {
            variableActuator.set(speed)
            true
        }else{
            variableActuator.stopMotor()
            false
        }
    }

    fun stop() {
        staticClimberMotor.stopMotor()
        variableClimberMotor.stopMotor()
        variableActuator.stopMotor()
    }
}