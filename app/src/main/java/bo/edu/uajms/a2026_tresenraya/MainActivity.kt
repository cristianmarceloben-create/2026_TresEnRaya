package bo.edu.uajms.a2026_tresenraya

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // Controles
    private lateinit var BTNTablero: Array<Button>
    private lateinit var TXVPlayer: TextView
    private lateinit var BTNRestart: Button
    private lateinit var Tablero: Array<Array<String>>

    // Variables
    private val rows = 3
    private val cols = 3
    private var currentPlayer = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Conectar los nueve botones del tablero
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

        // Conectar los demás controles
        TXVPlayer = findViewById(R.id.TXVPlayer)
        BTNRestart = findViewById(R.id.BTNRestart)

        // Crear la matriz vacía de 3 × 3
        Tablero = Array(rows) {
            Array(cols) { "" }
        }

        // Eventos de las nueve casillas
        for (i in BTNTablero.indices) {

            val row = i / cols
            val col = i % cols

            BTNTablero[i].setOnClickListener {

                Log.d(
                    "Click",
                    "Hiciste clic en ($row,$col)"
                )

                click(row, col, BTNTablero[i])
            }
        }

        // Evento del botón Reiniciar
        BTNRestart.setOnClickListener {

            Log.d(
                "Click",
                "Hiciste clic en Reiniciar"
            )
        }
    }

    private fun click(row: Int, col: Int, button: Button) {

        // Solamente permite jugar si la casilla está vacía
        if (button.text == "") {

            if (currentPlayer == 0) {

                // Juega el jugador O
                button.text = "O"
                currentPlayer = 1
                Tablero[row][col] = "O"

                TXVPlayer.text = getString(R.string.playerX)

            } else {

                // Juega el jugador X
                button.text = "X"
                currentPlayer = 0
                Tablero[row][col] = "X"

                TXVPlayer.text = getString(R.string.playerO)
            }

            // Comprobar si alguien ganó
            verifyVictory()
        }
    }

    private fun verifyVictory() {

        if (verifyCols() || verifyRows() || verifyDiagonals()) {

            /*
             * currentPlayer ya cambió después de realizar la jugada.
             * Si ahora vale 0, la última ficha colocada fue X.
             * Si ahora vale 1, la última ficha colocada fue O.
             */
            if (currentPlayer == 0) {

                TXVPlayer.text = getString(R.string.playerXWin)

            } else {

                TXVPlayer.text = getString(R.string.playerOWin)
            }
        }
    }

    private fun verifyCols(): Boolean {

        for (i in 0 until cols) {

            if (
                Tablero[0][i].isNotEmpty() &&
                Tablero[0][i] == Tablero[1][i] &&
                Tablero[0][i] == Tablero[2][i]
            ) {
                return true
            }
        }

        return false
    }

    private fun verifyRows(): Boolean {

        for (i in 0 until rows) {

            if (
                Tablero[i][0].isNotEmpty() &&
                Tablero[i][0] == Tablero[i][1] &&
                Tablero[i][0] == Tablero[i][2]
            ) {
                return true
            }
        }

        return false
    }

    private fun verifyDiagonals(): Boolean {

        // Diagonal principal
        if (
            Tablero[0][0].isNotEmpty() &&
            Tablero[0][0] == Tablero[1][1] &&
            Tablero[0][0] == Tablero[2][2]
        ) {
            return true
        }

        // Diagonal secundaria
        if (
            Tablero[0][2].isNotEmpty() &&
            Tablero[0][2] == Tablero[1][1] &&
            Tablero[0][2] == Tablero[2][0]
        ) {
            return true
        }

        return false
    }
}