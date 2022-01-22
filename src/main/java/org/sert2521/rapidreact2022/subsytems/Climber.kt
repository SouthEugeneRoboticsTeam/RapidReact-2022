package org.sert2521.rapidreact2022.subsytems

import edu.wpi.first.wpilibj.DigitalInput
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax
import edu.wpi.first.wpilibj2.command.SubsystemBase
import org.sert2521.rapidreact2022.LimitSwitches
import org.sert2521.rapidreact2022.Motors

//Add encoders
object Climber : SubsystemBase() {
    private val staticClimbMotor = PWMSparkMax(Motors.STATIC_CLIMBER.id)
    private val variableClimbMotor = PWMSparkMax(Motors.VARIABLE_CLIMBER.id)
    private val variableActuator = PWMSparkMax(Motors.VARIABLE_ACTUATOR.id)

    private val staticDownLimitSwitch = DigitalInput(LimitSwitches.STATIC_CLIMBER_DOWN.id)
    private val staticUpLimitSwitch = DigitalInput(LimitSwitches.STATIC_CLIMBER_UP.id)
    private val variableDownLimitSwitch = DigitalInput(LimitSwitches.VARIABLE_CLIMBER_DOWN.id)
    private val variableUpLimitSwitch = DigitalInput(LimitSwitches.VARIABLE_CLIMBER_UP.id)

    init {
        staticClimbMotor.inverted = Motors.STATIC_CLIMBER.reversed
        variableClimbMotor.inverted = Motors.VARIABLE_CLIMBER.reversed
        variableActuator.inverted = Motors.VARIABLE_ACTUATOR.reversed
    }

    fun elevateStatic(speed: Double): Boolean {
        return if(staticDownLimitSwitch.get() or staticUpLimitSwitch.get()) {
            staticClimbMotor.set(0.0)
            true
        }else{
            staticClimbMotor.set(speed)
            false
        }
    }

    fun elevateVariable(speed: Double): Boolean {
        return if(variableDownLimitSwitch.get() or variableUpLimitSwitch.get()) {
            variableClimbMotor.set(0.0)
            true
        }else{
            variableClimbMotor.set(speed)
            false
        }
    }
}