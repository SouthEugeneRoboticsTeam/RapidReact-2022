package org.sert2521.rapidreact2022.subsytems

import com.ctre.phoenix.motorcontrol.NeutralMode
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode
import com.ctre.phoenix.motorcontrol.can.TalonSRX
import com.revrobotics.CANSparkMax
import edu.wpi.first.wpilibj.AnalogPotentiometer
import edu.wpi.first.wpilibj.DigitalInput
import edu.wpi.first.wpilibj.Servo
import edu.wpi.first.wpilibj2.command.SubsystemBase
import org.sert2521.rapidreact2022.*
import org.sert2521.rapidreact2022.commands.StartClimber
import java.lang.System.currentTimeMillis

enum class LockStates {
    LOCKED,
    UNLOCKED,
    NEITHER
}

enum class Arms {
    STATIC,
    VARIABLE,
    BOTH,
    NEITHER
}

object Climber : SubsystemBase() {
    private val staticClimberMotor = CANSparkMax(Sparks.STATIC_CLIMBER.id, Sparks.STATIC_CLIMBER.type)
    private val variableClimberMotor = CANSparkMax(Sparks.VARIABLE_CLIMBER.id, Sparks.VARIABLE_CLIMBER.type)
    private val variableActuator = TalonSRX(Talons.VARIABLE_ACTUATOR.id)

    private var staticGoal = 0.0
    private var variableGoal = 0.0
    private var angleGoal = 0.0

    private val staticDownLimitSwitch = DigitalInput(OnOffs.STATIC_CLIMBER_DOWN.id)
    private val variableDownLimitSwitch = DigitalInput(OnOffs.VARIABLE_CLIMBER_DOWN.id)

    private val potentiometer = AnalogPotentiometer(Potentiometers.VARIABLE_CLIMBER_ANGLE.id, Potentiometers.VARIABLE_CLIMBER_ANGLE.maxAngle, Potentiometers.VARIABLE_CLIMBER_ANGLE.zeroAngle)

    private val servoStatic = try { Servo(PWMS.SERVO_STATIC.id) } catch (e: Exception) { null }
    private val servoVariable = try { Servo(PWMS.SERVO_VARIABLE.id) } catch (e: Exception) { null }

    private var staticLocked = LockStates.NEITHER
    private var staticLockedUpdate = 0L
    private var variableLocked = LockStates.NEITHER
    private var variableLockedUpdate = 0L

    private val startClimber = StartClimber()

    var loadBearingArm = Arms.NEITHER
    var climbing = false
    var committed = false
    private var calibratedAngle = false

    init {
        staticClimberMotor.inverted = Sparks.STATIC_CLIMBER.reversed
        variableClimberMotor.inverted = Sparks.VARIABLE_CLIMBER.reversed
        variableActuator.inverted = Talons.VARIABLE_ACTUATOR.reversed

        staticClimberMotor.idleMode = CANSparkMax.IdleMode.kBrake
        variableClimberMotor.idleMode = CANSparkMax.IdleMode.kBrake
        variableActuator.setNeutralMode(NeutralMode.Brake)

        staticClimberMotor.encoder.positionConversionFactor = SparkEncodersHall.STATIC_CLIMBER.conversionFactor
        variableClimberMotor.encoder.positionConversionFactor = SparkEncodersHall.VARIABLE_CLIMBER.conversionFactor

        variableActuator.configSelectedFeedbackSensor(TalonEncoders.ACTUATOR_MOTOR.device.toFeedbackDevice())
    }

    fun onEnable() {
        staticLocked = LockStates.NEITHER
        staticLockedUpdate = 0L
        variableLocked = LockStates.NEITHER
        variableLockedUpdate = 0L

        staticClimberMotor.encoder.position = START_POS
        variableClimberMotor.encoder.position = START_POS

        staticGoal = 0.0
        variableGoal = 0.0
        angleGoal = 0.0

        calibratedAngle = false

        reset()
    }

    val staticHeight
        get() = staticClimberMotor.encoder.position

    val variableHeight
        get() = variableClimberMotor.encoder.position

    val variableAngleArm
        get() = potentiometer.get() - CLIMBER_ANGLE_OFFSET

    val variableAngleMotor
        get() = -variableActuator.selectedSensorPosition * TalonEncoders.ACTUATOR_MOTOR.encoderDistanceFactor

    fun calibrateAngleMotor() {
        variableActuator.selectedSensorPosition = 0.0
        calibratedAngle = true
    }

    fun isAtBottomStatic(): Boolean {
        return !staticDownLimitSwitch.get()
    }

    fun isAtBottomVariable(): Boolean {
        return !variableDownLimitSwitch.get()
    }

    fun isStaticLocked(): LockStates {
        return if(currentTimeMillis() - staticLockedUpdate >= LOCK_TIME_STATIC * 1000) {
            staticLocked
        } else {
            LockStates.NEITHER
        }
    }

    fun isVariableLocked(): LockStates {
        return if(currentTimeMillis() - variableLockedUpdate >= LOCK_TIME_VARIABLE * 1000) {
            variableLocked
        } else {
            LockStates.NEITHER
        }
    }

    override fun periodic() {
        if(isAtBottomStatic()) {
            staticClimberMotor.encoder.position = MIN_CLIMBER_HEIGHT
        }

        if(isAtBottomVariable()) {
            variableClimberMotor.encoder.position = MIN_CLIMBER_HEIGHT
        }

        staticUpdate()
        variableUpdate()
        angleUpdate()
    }

    fun setLockStatic(lock: LockStates) {
        if(lock == LockStates.LOCKED) {
            servoStatic?.set(SERVO_LOCK_STATIC)
        } else {
            servoStatic?.set(SERVO_UNLOCK_STATIC)
        }

        if(staticLocked != lock) {
            staticLockedUpdate = currentTimeMillis()
        }
        staticLocked = lock
    }

    fun setLockVariable(lock: LockStates) {
        if(lock == LockStates.LOCKED) {
            servoVariable?.set(SERVO_LOCK_VARIABLE)
        } else {
            servoVariable?.set(SERVO_UNLOCK_VARIABLE)
        }

        if(variableLocked != lock) {
            variableLockedUpdate = currentTimeMillis()
        }
        variableLocked = lock
    }

    fun setStaticSpeed(amount: Double) {
        staticGoal = amount
        staticUpdate()
    }

    fun setVariableSpeed(amount: Double) {
        variableGoal = amount
        variableUpdate()
    }

    fun setAngleSpeed(amount: Double) {
        angleGoal = amount
        angleUpdate()
    }

    private fun staticUpdate() {
        var unstick = 0.0
        if(isStaticLocked() == LockStates.NEITHER && staticLocked == LockStates.UNLOCKED) {
            unstick += if(!climbing) {
                UNSTICK_SPEED_DOWN
            } else {
                UNSTICK_SPEED_CLIMB
            }
        }

        var maintain = 0.0
        if (loadBearingArm == Arms.STATIC) {
            maintain += CLIMBER_MAINTAIN
        }
        if (loadBearingArm == Arms.BOTH) {
            maintain += CLIMBER_MAINTAIN / 2.0
        }

        if(isStaticLocked() == LockStates.LOCKED) {
            staticClimberMotor.set(0.0)
        } else {
            if(isStaticLocked() == LockStates.NEITHER || (staticGoal < 0.0 && isAtBottomStatic()) || (staticGoal > 0.0 && staticHeight >= MAX_CLIMBER_HEIGHT)) {
                staticClimberMotor.set(unstick)
            } else {
                staticClimberMotor.set(staticGoal + maintain)
            }
        }
    }

    private fun variableUpdate() {
        var unstick = 0.0
        if(isVariableLocked() == LockStates.NEITHER && variableLocked == LockStates.UNLOCKED) {
            unstick += if(!climbing) {
                UNSTICK_SPEED_DOWN
            } else {
                UNSTICK_SPEED_CLIMB
            }
        }

        var maintain = 0.0
        if (loadBearingArm == Arms.VARIABLE) {
            maintain += CLIMBER_MAINTAIN
        }
        if (loadBearingArm == Arms.BOTH) {
            maintain += CLIMBER_MAINTAIN / 2.0
        }

        if(isVariableLocked() == LockStates.LOCKED) {
            variableClimberMotor.set(0.0)
        } else {
            if(isVariableLocked() == LockStates.NEITHER || (variableGoal < 0.0 && isAtBottomVariable()) || (variableGoal > 0.0 && variableHeight >= MAX_CLIMBER_HEIGHT)) {
                variableClimberMotor.set(unstick)
            } else {
                variableClimberMotor.set(variableGoal + maintain)
            }
        }
    }

    private fun angleUpdate() {
        if(calibratedAngle && ((angleGoal < 0.0 && variableAngleMotor <= MIN_CLIMBER_ANGLE_VALUE) || (angleGoal > 0.0 && variableAngleMotor >= MAX_CLIMBER_ANGLE_VALUE))) {
            variableActuator.set(TalonSRXControlMode.PercentOutput, 0.0)
        } else {
            variableActuator.set(TalonSRXControlMode.PercentOutput, angleGoal)
        }
    }

    fun lock() {
        setLockStatic(LockStates.LOCKED)
        setLockVariable(LockStates.LOCKED)
    }

    fun unlock() {
        setLockStatic(LockStates.UNLOCKED)
        setLockVariable(LockStates.UNLOCKED)
    }

    fun reset() {
        loadBearingArm = Arms.NEITHER
        climbing = false
        committed = false

        startClimber.schedule(false)
    }

    fun stop() {
        setStaticSpeed(0.0)
        setVariableSpeed(0.0)
        setAngleSpeed(0.0)
    }
}