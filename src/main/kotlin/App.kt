import java.awt.BorderLayout
import javax.swing.*

import edu.wpi.first.networktables.NetworkTable
import edu.wpi.first.networktables.NetworkTableEntry
import edu.wpi.first.networktables.NetworkTableInstance
import java.awt.GridLayout
import java.lang.Integer.parseInt


/**
 * Application to receive and set HSV values for ball vision.
 */


class App {
    // GUI elements
    private val layout = GridLayout(2, 3)
    private val frame = JFrame("Ball Vision Config")
    private val recvButton = JButton("Receive HSV")
    private val confirmButton = JButton("Confirm HSV")
    private val values = JLabel("H: 0     S: 0     V: 0", SwingConstants.CENTER)
    private val hfield = JTextField()
    private val sfield = JTextField()
    private val vfield = JTextField()

    // network tables shit
    private val inst = NetworkTableInstance.getDefault()
    private var table = inst.getTable("ball-vision")
    private var h = table.getEntry("h")
    private var s = table.getEntry("s")
    private var v = table.getEntry("v")


    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            App()
        }
    }


    init {
        frame.layout = layout

        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.setSize(1000, 300)
        frame.contentPane.add(recvButton)
        frame.contentPane.add(confirmButton)
        frame.contentPane.add(values)
        frame.contentPane.add(hfield)
        frame.contentPane.add(sfield)
        frame.contentPane.add(vfield)
        frame.isVisible = true

        table = inst.getTable("ball-vision")

        // bind buttons
        recvButton.addActionListener { recvValues() }
        confirmButton.addActionListener { confirmValues() }
    }


    private fun recvValues() {
        values.text = "H: ${h.getNumber(0)}     S: ${s.getNumber(0)}     V: ${v.getNumber(0)}"
    }


    private fun getLabelTextInt(src: JTextField): Int {
        try {
            return parseInt(src.text)
        } catch (e: java.lang.NumberFormatException) {
            return 0
        }
    }


    private fun confirmValues() {
        h.setNumber(getLabelTextInt(hfield))
        s.setNumber(getLabelTextInt(sfield))
        v.setNumber(getLabelTextInt(vfield))
    }
}