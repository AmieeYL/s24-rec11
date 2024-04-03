package edu.cmu.cs.cs214.rec11.plugin;

import edu.cmu.cs.cs214.rec11.framework.core.GameFramework;
import edu.cmu.cs.cs214.rec11.framework.core.GamePlugin;
import edu.cmu.cs.cs214.rec11.games.TicTacToe;
import edu.cmu.cs.cs214.rec11.games.TicTacToe.Player;

/**
 * A TicTacToe game plug-in.
 */
public class TicTacToePlugin implements GamePlugin<String>{

    private static final String GAME_NAME = "TicTacToe";

    private GameFramework framework;
    private TicTacToe game;

    @Override
    public String getGameName() {
        return GAME_NAME;
    }

    @Override
    public int getGridWidth() {
        return TicTacToe.SIZE;
    }

    @Override
    public int getGridHeight() {
        return TicTacToe.SIZE;
    }

    @Override
    public void onRegister(GameFramework f) {
        this.framework = f;
    }

    @Override
    public void onNewGame() {
        this.game = new TicTacToe(); // Reset the game for a new session
    }

    @Override
    public void onNewMove() { } 

    @Override
    public boolean isMoveValid(int x, int y) {
        return game.isValidPlay(x, y);
    }

    @Override
    public boolean isMoveOver() {
        // Each move in TicTacToe concludes the player's turn.
        return true;
    }
    
    @Override
    public void onMovePlayed(int x, int y) {
        game.play(x, y); // Execute the move
        updateBoard();

        if (game.isOver()) {
            String winnerMessage = game.winner() == null ? "The game is a draw!" : "Player " + game.winner() + " wins!";
            framework.setFooterText(winnerMessage);
        } else {
            framework.setFooterText("Player " + game.currentPlayer() + "'s turn");
        }
    }

    @Override
    public boolean isGameOver() {
        return game.isOver();
    }

    @Override
    public String getGameOverMessage() {
        Player winner = game.winner();
        if (winner != null) {
            return "Player " + winner + " wins!";
        } else {
            return "The game is a draw!";
        }
    }

    @Override
    public void onGameClosed() { }

    @Override
    public String currentPlayer() {
        return game.currentPlayer().toString(); // Return the current player as a String
    }

    private void updateBoard() {
        for (int x = 0; x < TicTacToe.SIZE; x++) {
            for (int y = 0; y < TicTacToe.SIZE; y++) {
                Player player = game.getSquare(x, y);
                String symbol = player == null ? "" : player.toString();
                framework.setSquare(x, y, symbol);
            }
        }
    }

}
