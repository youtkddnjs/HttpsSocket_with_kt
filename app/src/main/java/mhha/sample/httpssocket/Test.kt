package mhha.sample.httpssocket

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket

fun main() {
    Thread{
        //172.30.1.63
        val port = 8080
        val server = ServerSocket(port)

        while (true) {
            val socket = server.accept()

//        socket.getInputStream() // 클라이언트로부터 들어오는 스트림 == 클라이언트의 socket.outputStream
//        socket.getOutputStream() // 클라이언트에게 데이터를 주는 스트림 == 클라인어트의 socket.inputStream

            val reader = BufferedReader(InputStreamReader(socket.getInputStream()))
            val printer = PrintWriter(socket.getOutputStream())

            var inputData: String? = "-1"  // 임의로 -1 값 줌

            while (inputData != null && inputData != "") {
                inputData = reader.readLine()  // 한줄 씩 읽기(엔터 입력 기준)
            }
            System.out.println("Server Read Data $inputData")

            printer.println("HTTP/1.1 200 OK") //정상 수신
            printer.println("Content-Type: text/html\r\n") // header 부분

            printer.println("<h1>Hello World!!</h1>")
            printer.println("\r\n")
            printer.flush() //잔여 데이터 제거
            printer.close() // outputsteam 종료

            reader.close() // inputstream 종료
            socket.close() // 마지막으로 소켓 종료
        }

    }.start()
}//fun main()

// 커맨드
// javac -encoding UTF-8 .\web.java
// java web
// 코드
//import java.io.*;
//import java.net.ServerSocket;
//import java.net.Socket;
//
//public class web {
//    public static void main(String[] args) {
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    int port = 8080;
//                    ServerSocket server = new ServerSocket(port);
//
//                    while (true) {
//                        Socket socket = server.accept();
//
//                        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                        PrintWriter printer = new PrintWriter(socket.getOutputStream());
//
//                        String inputData = "-1";  // 임의로 -1 값 줌
//
//                        while (inputData != null && !inputData.isEmpty()) {
//                            inputData = reader.readLine();
//                        }
//                        System.out.println("Server Read Data " + inputData);
//
//                        printer.println("HTTP/1.1 200 OK"); // 정상 수신
//                        printer.println("Content-Type: text/html\r\n"); // header 부분
//
//                        printer.println("{\"message\": \"Hello World\"}");
//                        printer.println("\r\n");
//                        printer.flush(); // 잔여 데이터 제거
//                        printer.close(); // outputstream 종료
//
//                        reader.close(); // inputstream 종료
//                        socket.close(); // 마지막으로 소켓 종료
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        thread.start();
//    }
//}