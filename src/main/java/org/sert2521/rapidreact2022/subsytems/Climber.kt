package org.sert2521.rapidreact2022.subsytems

import com.ctre.phoenix.motorcontrol.NeutralMode
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX
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

//Make hold pos while doing the birds
//Make it stop if the angle isn't changing
object Climber : SubsystemBase() {
    private val staticClimberMotor = CANSparkMax(Sparks.STATIC_CLIMBER.id, Sparks.STATIC_CLIMBER.type)
    private val variableClimberMotor = CANSparkMax(Sparks.VARIABLE_CLIMBER.id, Sparks.VARIABLE_CLIMBER.type)
    private val variableActuator = WPI_TalonSRX(Talons.VARIABLE_ACTUATOR.id)

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

    private var forceLocked = false

    private val startClimber = StartClimber()

    var loadBearingArm = Arms.NEITHER
    var climbing = false

    init {
        staticClimberMotor.inverted = Sparks.STATIC_CLIMBER.reversed
        variableClimberMotor.inverted = Sparks.VARIABLE_CLIMBER.reversed
        variableActuator.inverted = Talons.VARIABLE_ACTUATOR.reversed

        staticClimberMotor.idleMode = CANSparkMax.IdleMode.kBrake
        variableClimberMotor.idleMode = CANSparkMax.IdleMode.kBrake
        variableActuator.setNeutralMode(NeutralMode.Brake)

        staticClimberMotor.encoder.positionConversionFactor = SparkEncodersHall.STATIC_CLIMBER.conversionFactor
        variableClimberMotor.encoder.positionConversionFactor = SparkEncodersHall.VARIABLE_CLIMBER.conversionFactor
    }

    fun onEnable() {
        staticLocked = LockStates.NEITHER
        staticLockedUpdate = 0L
        variableLocked = LockStates.NEITHER
        variableLockedUpdate = 0L

        loadBearingArm = Arms.NEITHER
        climbing = false

        staticClimberMotor.encoder.position = START_POS
        variableClimberMotor.encoder.position = START_POS

        staticGoal = 0.0
        variableGoal = 0.0
        angleGoal = 0.0

        forceLocked = false
        startClimber.schedule(false)
    }

    val staticHeight
        get() = staticClimberMotor.encoder.position

    val variableHeight
        get() = variableClimberMotor.encoder.position

    val variableAngle
        get() = potentiometer.get()

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
        if(!forceLocked && lock != LockStates.NEITHER) {
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
    }

    fun setLockVariable(lock: LockStates) {
        if(!forceLocked && lock != LockStates.NEITHER) {
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

        /*if(isStaticLocked() == LockStates.LOCKED) {
            staticClimberMotor.set(0.0)
        } else {
            if(isStaticLocked() == LockStates.NEITHER || (staticGoal < 0.0 && isAtBottomStatic()) || (staticGoal > 0.0 && staticHeight >= MAX_CLIMBER_HEIGHT)) {
                staticClimberMotor.set(unstick)
            } else {
                staticClimberMotor.set(staticGoal + maintain)
            }
        }*/
        staticClimberMotor.set(0.0)
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

        /*if(isVariableLocked() == LockStates.LOCKED) {
            variableClimberMotor.set(0.0)
        } else {
            if(isVariableLocked() == LockStates.NEITHER || (variableGoal < 0.0 && isAtBottomVariable()) || (variableGoal > 0.0 && variableHeight >= MAX_CLIMBER_HEIGHT)) {
                variableClimberMotor.set(unstick)
            } else {
                variableClimberMotor.set(variableGoal + maintain)
            }
        }*/
        variableClimberMotor.set(0.0)
    }

    private fun angleUpdate() {
        /*if((angleGoal < 0.0 && variableAngle <= MIN_CLIMBER_ANGLE) || (angleGoal > 0.0 && variableAngle >= MAX_CLIMBER_ANGLE)) {
            variableActuator.set(0.0)
        } else {
            variableActuator.set(angleGoal)
        }*/
        variableActuator.set(0.0)
    }

    fun lock() {
        setLockStatic(LockStates.LOCKED)
        setLockVariable(LockStates.LOCKED)
    }

    fun unlock() {
        setLockStatic(LockStates.UNLOCKED)
        setLockVariable(LockStates.UNLOCKED)
    }

    fun stop() {
        setStaticSpeed(0.0)
        setVariableSpeed(0.0)
        setAngleSpeed(0.0)
    }
}