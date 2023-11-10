package com.example.tictactoe

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var resetButton: Button
    lateinit var oyuncuOne:Button
    lateinit var oyuncuTwo:Button

    // Oyun tahtasını temsil eden bir dizi
    private val board = Array(3) { arrayOfNulls<Button>(3) }

    // Oyuncu sırasını takip etmek için bir değişken
    private var currentPlayer = "X"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        oyuncuOne=findViewById(R.id.oyuncu1)
        oyuncuTwo=findViewById(R.id.oyuncu2)

        initializeBoard()

        resetButton=findViewById(R.id.resetButton)
        resetButton.setOnClickListener {
            resetBoard()
        }



    }

    // Oyun tahtasını başlatan fonksiyon
    private fun initializeBoard() {
        for (i in 0 until 3) {
            for (j in 0 until 3) {
                val buttonId = resources.getIdentifier("button_$i$j", "id", packageName)
                board[i][j] = findViewById(buttonId)
                board[i][j]?.setOnClickListener { onCellClicked(it) }
            }
        }
    }

    // Hücreye tıklandığında çağrılan fonksiyon
    private fun onCellClicked(view: View) {
        val button = view as Button
        if (button.text == "") {
            button.text = currentPlayer
            if (checkForWinner()) {
                showWinner()
            } else {
                switchPlayer()
                findPlayer()
            }
        }
    }

    // Oyunun kazananını kontrol eden fonksiyon
    private fun checkForWinner(): Boolean {
        // Satırları kontrol et
        for (i in 0 until 3) {
            if (board[i][0]?.text == currentPlayer &&
                board[i][1]?.text == currentPlayer &&
                board[i][2]?.text == currentPlayer
            ) {
                return true
            }
        }

        // Sütunları kontrol et
        for (i in 0 until 3) {
            if (board[0][i]?.text == currentPlayer &&
                board[1][i]?.text == currentPlayer &&
                board[2][i]?.text == currentPlayer
            ) {
                return true
            }
        }

        // Çaprazları kontrol et
        if (board[0][0]?.text == currentPlayer &&
            board[1][1]?.text == currentPlayer &&
            board[2][2]?.text == currentPlayer
        ) {
            return true
        }

        if (board[0][2]?.text == currentPlayer &&
            board[1][1]?.text == currentPlayer &&
            board[2][0]?.text == currentPlayer
        ) {
            return true
        }

        return false
    }
    // Kazananı ekrana gösteren fonksiyon
    private fun showWinner() {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setTitle("Game Over")
        dialogBuilder.setMessage("Player $currentPlayer wins!")
        dialogBuilder.setPositiveButton("Restart") { _, _ ->
            resetBoard()
        }
        dialogBuilder.setNegativeButton("Exit") { _, _ ->
            finish()
        }
        val alertDialog: AlertDialog = dialogBuilder.create()
        alertDialog.show()
        //Toast.makeText(this, "Player $currentPlayer wins!", Toast.LENGTH_SHORT).show()
        resetBoard()
    }

    // Oyuncu sırasını değiştiren fonksiyon
    private fun switchPlayer() {
        currentPlayer = if (currentPlayer == "X") "O" else "X"
    }

    private fun findPlayer() {
        if(currentPlayer == "X"){
            oyuncuOne.setBackgroundColor(Color.DKGRAY)
            oyuncuTwo.setBackgroundColor(Color.GRAY)

        }else{
            oyuncuTwo.setBackgroundColor(Color.DKGRAY)
            oyuncuOne.setBackgroundColor(Color.GRAY)
        }
    }

    // Oyun tahtasını sıfırlayan fonksiyon
    private fun resetBoard() {
        oyuncuOne.setBackgroundColor(Color.DKGRAY)
        oyuncuTwo.setBackgroundColor(Color.GRAY)
        for (i in 0 until 3) {
            for (j in 0 until 3) {
                board[i][j]?.text = ""
            }
        }
        currentPlayer = "X"
    }
}
