package org.george.chess.util.stockfish;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.util.List;

public class StockfishRunner {
    private static StockfishRunner instance = null;
    private static final String STOCKFISH_PATH = "C:\\Users\\George\\Downloads\\stockfish-windows-x86-64-avx512\\stockfish\\stockfish.exe";
    private static final String SET_POSITION_PREFIX_STRING = "position startpos moves ";
    private static final String NO_MOVE = "(none)";
    private static final String SET_CORES = "setoption name Threads value ";

    public static StockfishRunner get(){
        if(StockfishRunner.instance == null){
            StockfishRunner.instance = new StockfishRunner();
        }
        return StockfishRunner.instance;
    }

    private Process engineProcess;
    private BufferedReader processReader;
    private OutputStreamWriter processWriter;

    private StockfishRunner() {
        ProcessBuilder processBuilder = new ProcessBuilder(STOCKFISH_PATH);
        try{
            engineProcess = processBuilder.start();
            System.out.println("Successfully started stockfish");
        } catch(Exception e){
            System.out.println("Error starting stockfish");
            e.printStackTrace();
        }
        processReader = new BufferedReader(new InputStreamReader(engineProcess.getInputStream()));
        processWriter = new OutputStreamWriter(engineProcess.getOutputStream());
    }

    public void setThreads(int numCores){
        writeCommand(SET_CORES + numCores);
    }

    private void writeCommand(String command) {
        try{
            processWriter.write(command + "\n");
            processWriter.flush();
        } catch(Exception e){
            System.out.println("Error sending command to stockfish");
            e.printStackTrace();
        }
    }

    private String readResponse() {
        String line;
        try{
            while ((line = processReader.readLine()) != null) {
                if(line.startsWith("bestmove")){
                    return line.split(" ")[1];
                }
            }
        } catch(Exception e){
            System.out.println("Error reading response from stockfish");
            e.printStackTrace();
        }
        return "";
    }

    public String getBestMove(final List<String> moves){
        if(moves.size() == 0){ //TODO: Replace this with actual best move for white
            return "e2e4";
        }
        final StringBuilder builder = new StringBuilder(SET_POSITION_PREFIX_STRING).append(moves.get(0));
        for(int i = 1; i < moves.size(); i++){
            builder.append(" ");
            builder.append(moves.get(i));
        }
        if(moves.getLast().equals(NO_MOVE)){
            return "";
        }

        writeCommand(builder.toString());
        writeCommand("go movetime 5000");

        return readResponse();
    }

    public void close() throws IOException {
        try{
            writeCommand("quit");
            processReader.close();
            processWriter.close();
            engineProcess.destroy();
        } catch(Exception e){
            System.out.println("Error destroying Stockfish process");
            e.printStackTrace();
        }
    }

    

}
