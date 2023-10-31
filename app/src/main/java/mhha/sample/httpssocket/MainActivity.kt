package mhha.sample.httpssocket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.google.gson.Gson
import mhha.sample.httpssocket.databinding.ActivityMainBinding
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.HttpURLConnection
import java.net.Socket
import java.net.URL

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    val clinet = OkHttpClient()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var serverHost ="172.30.1.61"

        binding.serverHostEditText.addTextChangedListener {
            serverHost = it.toString()
        }

        binding.confirmButton.setOnClickListener {
            val request : Request = Request.Builder()
                .url("http://${serverHost}:8080")
//                .url("httpsw://172.30.1.61:8080")
//            .url("http://10.0.2.2:8080")
                .build()

            // 콜백 작성
            val callback = object: Callback {

                override fun onFailure(call: Call, e: IOException) {
                    //요청 자체가 실패 하거나 통신 과정에 실패 시
                    runOnUiThread { Toast.makeText(this@MainActivity,"수신 실패",Toast.LENGTH_SHORT).show() }
                    Log.e("httpssocketApp", "${e}")
                }//override fun onFailure(call: Call, e: IOException)

                override fun onResponse(call: Call, response: Response) {
                    //서버에서 실패 요청을 보내거나 데이터가 없을 때

                    if(response.isSuccessful){
                        val resopnse = response.body?.string()

                        val messageClass = Gson().fromJson(resopnse, Message::class.java)

//                        Log.e("httpssocketApp", "${messageClass.a}")
                        runOnUiThread {

                            binding.informationTextView.isVisible = true
                            binding.informationTextView.text = messageClass.a

                            binding.serverHostEditText.visibility = View.GONE
                            binding.confirmButton.visibility = View.GONE
                        }

                    }else{
                        runOnUiThread { Toast.makeText(this@MainActivity,"수신 실패",Toast.LENGTH_SHORT).show() }
                    }

                }//override fun onResponse(call: Call, response: Response)
            } //val callback = object: Callback
            clinet.newCall(request).enqueue(callback)
        } //binding.confirmButton.setOnClickListener

//        try {
////            val responese = clinet.newCall(request).execute() //android.os.NetworkOnMainThreadException 에러로 인해 콜백으로 사용 할 것
//            val responese = clinet.newCall(request).enqueue(callback)
////            Log.e("httpssocketApp", "${responese.body?.string()}")
//        }catch (e: Exception ){
//            Log.e("httpssocketApp", "${e}")
//        }

        //172.30.1.61
//
//        Thread{
//            try {
//                val socket = Socket("172.30.1.61", 8080)
//                val printer = PrintWriter(socket.getOutputStream())
//                val reader = BufferedReader(InputStreamReader(socket.getInputStream()))
//
//                printer.println("GET / HTTP/1.1")
//                printer.println("Host: 172.30.1.61:5500")
//                printer.println("User-Agent: android")
//                printer.println("\r\n")
//                printer.flush()
//
//
//                var input: String? = "-1"
//                while(input != null){
//                    input = reader.readLine()
//                    Log.e("httpssocketApp", input)
//                }
//                reader.close()
//                printer.close()
//                socket.close()
//            }catch (e : Exception){
//                Log.d("AppDebug", "$e")
//            }
//        }.start()


    } //override fun onCreate(savedInstanceState: Bundle?)



} //class MainActivity : AppCompatActivity()