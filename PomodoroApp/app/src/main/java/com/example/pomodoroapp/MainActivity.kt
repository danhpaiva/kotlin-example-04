package com.example.pomodoroapp

import android.content.IntentSender.OnFinished
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private var tempo: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val edtTempo = findViewById<EditText>(R.id.tiEdt_valorTempo)
        val botaoIniciar = findViewById<Button>(R.id.btn_iniciar)
        val botaoParar = findViewById<Button>(R.id.btn_parar)
        val resultado = findViewById<TextView>(R.id.tv_resultado)

        botaoIniciar.setOnClickListener {
            try {
                val numero = edtTempo.text.toString().toLong()

                tempo = object : CountDownTimer(numero * 60 * 1000, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        var segundos = millisUntilFinished / 1000
                        var minutos = segundos / 60
                        segundos = segundos % 60
                        resultado.text = String.format("%02d:%02d", minutos, segundos)
                        //resultado.text = millisUntilFinished.toString()
                    }

                    override fun onFinish() {
                        resultado.text = "O tempo acabou!"
                    }
                }

                tempo?.start()


            } catch (e: NumberFormatException) {
                Toast.makeText(
                    this, "Digite um número para começar a contagem", Toast.LENGTH_SHORT
                ).show()
            }

            botaoParar.setOnClickListener {
                tempo?.cancel()
            }
        }
    }
}