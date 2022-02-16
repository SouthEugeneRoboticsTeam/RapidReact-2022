package org.sert2521.rapidreact2022.commands

import edu.wpi.first.wpilibj2.command.CommandBase
import org.sert2521.rapidreact2022.LED_LENGTH
import org.sert2521.rapidreact2022.subsytems.LEDStrip

class SpiralLED : CommandBase() {
    init {
        addRequirements(LEDStrip)
    }

    override fun initialize() {
        for (i in 0 until LED_LENGTH) {
            LEDStrip.setLEDHSV(i, 215, 255, ((i.toDouble() / (LED_LENGTH - 1)) * 153).toInt())
        }
    }

    override fun execute() {
        LEDStrip.cycle(100.0)
    }

    override fun end(interrupted: Boolean) {
        LEDStrip.reset()
    }
}
