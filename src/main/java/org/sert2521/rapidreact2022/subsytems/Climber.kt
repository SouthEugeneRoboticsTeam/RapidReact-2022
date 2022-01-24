package org.sert2521.rapidreact2022.subsytems

import edu.wpi.first.wpilibj.AnalogPotentiometer
import edu.wpi.first.wpilibj.DigitalInput
import edu.wpi.first.wpilibj.Encoder
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax
import edu.wpi.first.wpilibj2.command.SubsystemBase
import org.sert2521.rapidreact2022.Encoders
import org.sert2521.rapidreact2022.LimitSwitches
import org.sert2521.rapidreact2022.Motors
import org.sert2521.rapidreact2022.Potentiometers

//Add encoders
object Climber : SubsystemBase() {
    private val staticClimberMotor = PWMSparkMax(Motors.STATIC_CLIMBER.id)
    private val variableClimberMotor = PWMSparkMax(Motors.VARIABLE_CLIMBER.id)
    private val variableActuator = PWMSparkMax(Motors.VARIABLE_ACTUATOR.id)

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
    }

    fun elevateStatic(speed: Double): Boolean {
        return if(staticDownLimitSwitch.get() or staticUpLimitSwitch.get()) {
            staticClimberMotor.set(0.0)
            true
        }else{
            staticClimberMotor.set(speed)
            false
        }
    }

    fun elevateVariable(speed: Double): Boolean {
        return if(variableDownLimitSwitch.get() or variableUpLimitSwitch.get()) {
            variableClimberMotor.set(0.0)
            true
        }else{
            variableClimberMotor.set(speed)
            false
        }
    }
}