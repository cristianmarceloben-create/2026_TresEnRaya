package bo.edu.uajms.a2026_tresenraya

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var BTNTablero: Array<Button>
    private lateinit var TXVPlayer: TextView
    private lateinit var BTNRestart: Button
    private lateinit var Tablero: Array<Array<String>>

    private val rows = 3
    private val cols = 3
    private var currentPlayer = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        BTNTablero = arrayOf(
            findViewById(R.id.BTN00),
            findViewById(R.id.BTN01),
            findViewById(R.id.BTN02),
            findViewById(R.id.BTN10),
            findViewById(R.id.BTN11),
            findViewById(R.id.BTN12),
            findViewById(R.id.BTN20),
            findViewById(R.id.BTN21),
            findViewById(R.id.BTN22)
        )

        TXVPlayer = findViewById(R.id.TXVPlayer)
        BTNRestart = findViewById(R.id.BTNRestart)

        Tablero = Array(rows) {
            Array(cols) { "" }
        }

        BTNRestart.visibility = View.INVISIBLE

        for (i in BTNTablero.indices) {
            val row = i / cols
            val col = i % cols

            BTNTablero[i].setOnClickListener {
                click(row, col, BTNTablero[i])
            }
        }

        BTNRestart.setOnClickListener {
            enableGame()
        }
    }

    private fun click(row: Int, col: Int, button: Button) {
        if (button.text == "") {
            if (currentPlayer == 0) {
                button.setText("O")
                currentPlayer = 1
                Tablero[row][col] = "O"
                TXVPlayer.setText(getString(R.string.playerX))
            } else {
                button.setText("X")
                currentPlayer = 0
                Tablero[row][col] = "X"
                TXVPlayer.setText(getString(R.string.playerO))
            }

            printArray()

            if (!verifyVictory()) {
                verifyNoWinner()
            }
        }
    }

    private fun verifyVictory(): Boolean {
        if (verifyCols() || verifyRows() || verifyDiagonals()) {
            if (currentPlayer == 0) {
                TXVPlayer.setText(getString(R.string.playerXWin))
            } else {
                TXVPlayer.setText(getString(R.string.playerOWin))
            }

            disableGame()
            return true
        }

        return false
    }

    private fun verifyNoWinner() {
        var ban: Boolean = false

        for (i in 0 until rows) {
            for (j in 0 until cols) {
                if (Tablero[i][j] == "") {
                    ban = true
                    break
                }
            }
        }

        if (!ban) {
            TXVPlayer.setText(getString(R.string.noWinner))
            disableGame()
        }
    }

    private fun verifyCols(): Boolean {
        for (i in 0 until cols) {
            if (
                Tablero[0][i] == Tablero[1][i] &&
                Tablero[0][i] == Tablero[2][i] &&
                Tablero[0][i] != ""
            ) {
                return true
            }
        }

        return false
    }

    private fun verifyRows(): Boolean {
        for (i in 0 until rows) {
            if (
                Tablero[i][0] == Tablero[i][1] &&
                Tablero[i][0] == Tablero[i][2] &&
                Tablero[i][0] != ""
            ) {
                return true
            }
        }

        return false
    }

    private fun verifyDiagonals(): Boolean {
        if (
            (
                    Tablero[0][0] == Tablero[1][1] &&
                            Tablero[0][0] == Tablero[2][2] &&
                            Tablero[0][0] != ""
                    ) ||
            (
                    Tablero[0][2] == Tablero[1][1] &&
                            Tablero[0][2] == Tablero[2][0] &&
                            Tablero[0][2] != ""
                    )
        ) {
            return true
        }

        return false
    }

    private fun printArray() {
        Log.d(
            "Click",
            "${Tablero[0][0]} - ${Tablero[0][1]} - ${Tablero[0][2]} - " +
                    "${Tablero[1][0]} - ${Tablero[1][1]} - ${Tablero[1][2]} - " +
                    "${Tablero[2][0]} - ${Tablero[2][1]} - ${Tablero[2][2]} - "
        )
    }

    private fun enableGame() {
        Tablero = Array(rows) {
            Array(cols) { "" }
        }

        for (i in BTNTablero.indices) {
            BTNTablero[i].isEnabled = true
            BTNTablero[i].setText("")
            BTNRestart.visibility = View.INVISIBLE
        }

        currentPlayer = 0
        TXVPlayer.setText(getString(R.string.playerO))
    }

    private fun disableGame() {
        for (i in BTNTablero.indices) {
            BTNTablero[i].isEnabled = false
            BTNRestart.visibility = View.VISIBLE
        }
    }
}