package server;

import java.io.*;

public class OnlineClientIOcache {

    private BufferedReader inputStream;
    private BufferedWriter outputStream;

    public OnlineClientIOcache(BufferedReader inputStream, BufferedWriter outputStream){
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    public BufferedReader getInputStream(){
        return inputStream;
    }

    public BufferedWriter getOutputStream() {
        return outputStream;
    }
}
