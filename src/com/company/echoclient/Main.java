package com.company.echoclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // connects to server and creates IO objects - listens to 127.0.0.1:5000
	    try (Socket socket = new Socket("localhost", 5000);
                BufferedReader echoes = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter stringToEcho = new PrintWriter(socket.getOutputStream(), true)) {

            socket.setSoTimeout(5000); // sets timeout 5s

            Scanner scanner = new Scanner(System.in);
            String echoString, response;

            do {
                // Read in users input string
                System.out.println("Enter String to be echoed by the server : ");
                echoString = scanner.nextLine();

                // send string to server (via outputstream)
                stringToEcho.println(echoString);

                if (!echoString.toLowerCase().equals("exit")) {
                    response = echoes.readLine();   // blocks and waits for response
                    System.out.println(response);
                }

            } while (!echoString.toLowerCase().equals("exit"));

        } catch (IOException e) {
            System.out.println("Client exception encountered " + e.getMessage());
            e.printStackTrace();
        }
    }
}
