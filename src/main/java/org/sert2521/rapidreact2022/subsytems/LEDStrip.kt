package org.sert2521.rapidreact2022.subsytems

import edu.wpi.first.wpilibj.AddressableLED
import edu.wpi.first.wpilibj.AddressableLEDBuffer
import edu.wpi.first.wpilibj2.command.SubsystemBase
import org.sert2521.rapidreact2022.LED_LENGTH
import org.sert2521.rapidreact2022.LedStrips

object LEDStrip : SubsystemBase() {
    private val addressableLED = AddressableLED(LedStrips.MAIN.id)
    private var buffer = AddressableLEDBuffer(LED_LENGTH)

    init {
        addressableLED.setLength(LED_LENGTH)
        addressableLED.start()

        reset()
    }

    fun setLEDRGB(i: Int, r: Int, g: Int, b: Int) {
        buffer.setRGB(i, r, g, b)
    }

    fun setLEDHSV(i: Int, h: Int, s: Int, v: Int) {
        buffer.setHSV(i, h, s, v)
    }

    fun update() {
        addressableLED.setData(buffer)
    }

    fun reset() {
        buffer = AddressableLEDBuffer(LED_LENGTH)
        update()
    }
}