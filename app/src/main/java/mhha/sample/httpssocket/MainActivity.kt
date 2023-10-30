package mhha.sample.httpssocket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import mhha.sample.httpssocket.databinding.ActivityMainBinding
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.HttpURLConnection
import java.net.Socket
import java.net.URL

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //172.30.1.61

        Thread{
            try {
                val socket = Socket("172.30.1.61", 8080)
                val printer = PrintWriter(socket.getOutputStream())
                val reader = BufferedReader(InputStreamReader(socket.getInputStream()))

                printer.println("GET / HTTP/1.1")
                printer.println("Host: 172.30.1.61:5500")
                printer.println("User-Agent: android")
                printer.println("\r\n")
                printer.flush()


                var input: String? = "-1"
                while(input != null){
                    input = reader.readLine()
                    Log.e("httpssocketApp", input)
                }
                reader.close()
                printer.close()
                socket.close()
            }catch (e : Exception){
                Log.d("AppDebug", "$e")
            }
        }.start()


    } //override fun onCreate(savedInstanceState: Bundle?)



} //class MainActivity : AppCompatActivity()