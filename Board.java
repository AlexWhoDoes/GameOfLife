package life;


import java.util.Random;

public class Board {
    private char[][] map;

    public void setMap(char[][] map) {
        this.map = map;
    }

    public char[][] getMap() {
        return map;
    }

    public char getElement(int i, int j) {
        return map[i][j];
    }

    public Board(int size) {
        map = new char[size][size];
        initialization();
    }

    public void initialization() {
        Random random = new Random();
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[0].length; x++) {
                map[y][x] = random.nextBoolean() ? 'O' : ' ';
            }
        }
    }

    public int countAlive (char[][] board) {
        int count = 0;
        for (char[] chars : board) {
            for (int j = 0; j < board[0].length; j++) {
                if (chars[j] == 'O') count++;
            }
        }

        return count;
    }

    private boolean isAlive(char cell) {
        return cell == 'O';
    }

    private int getAliveNeighbors(int x, int y, char[][] currentGen) {
        int alive = 0;
        int len = currentGen.length;
        for (int i = y - 1; i < y + 2; i++) {
            for (int j = x - 1; j < x + 2; j++) {
                // cycling universe using [(len + i) % len] : if (i == -1) -> index = len - 1
                if ((i != y || j != x) && isAlive(currentGen[(len + i) % len][(len + j) % len])) {
                    alive++;
                }
            }
        }
        return alive;
    }

    public void nextGeneration(char[][] currentGen) {
        char[][] nextGen = new char[currentGen.length][currentGen[0].length];
        for (int y = 0; y < currentGen.length; y++) {
            for (int x = 0; x < currentGen[0].length; x++) {
                int aliveNeighbors = getAliveNeighbors(x, y, currentGen);
                boolean isAlive = isAlive(currentGen[y][x]);
                if (isAlive && (aliveNeighbors < 2 || aliveNeighbors > 3)) {
                    nextGen[y][x] = ' ';
                } else if (!isAlive && aliveNeighbors == 3) {
                    nextGen[y][x] = 'O';
                } else {
                    nextGen[y][x] = currentGen[y][x];
                }
            }
        }
        setMap(nextGen);
    }

}
