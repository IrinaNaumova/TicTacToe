import java.util.Random;
import java.util.Scanner;

public class TicTacToe {

    private static final char DOT_HUMAN = 'X';
    private static final char DOT_AI = 'O';
    private static final char DOT_EMPTY = '_';
    private static char[][] field;
    private static int fieldSizeX;
    private static int fieldSizeY;
    private static int winStreak = 3;
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final Random RANDOM = new Random();

    private static void initMap() {
        fieldSizeX = 5;
        fieldSizeY = 5;
        field = new char[fieldSizeY][fieldSizeX];
        for (int y = 0; y < fieldSizeY; y++) {
            for (int x = 0; x < fieldSizeX; x++) {
                field[y][x] = DOT_EMPTY;
            }
        }
    }

    private static void printMap() {
        for (int y = 0; y < fieldSizeY; y++) {
            System.out.print("|");
            for (int x = 0; x < fieldSizeX; x++) {
                System.out.print(field[y][x] + "|");
            }
            System.out.println();
        }

        for (int i = 0; i < fieldSizeX; i++) {
            System.out.print("---");
        }
        System.out.println();
    }

    private static void humanTurn() {
        int x;
        int y;
        do {
            System.out.println("Введите координаты X и Y (от 1 до 5) через пробел >>>");
            x = SCANNER.nextInt() - 1;
            y = SCANNER.nextInt() - 1;
        } while (!isCellValid(x, y) || !isCellEmpty(x, y));
        field[y][x] = DOT_HUMAN;
    }

    private static boolean isCellValid(int x, int y) {
        return x >= 0 && x < fieldSizeX &&
                y >= 0 && y < fieldSizeY;
    }

    private static boolean isCellEmpty(int x, int y) {
        return field[y][x] == DOT_EMPTY;
    }

    private static void aiTurn() {
        int x;
        int y;
        do {
            x = RANDOM.nextInt(fieldSizeX);
            y = RANDOM.nextInt(fieldSizeY);
        } while (!isCellEmpty(x, y));
        field[y][x] = DOT_AI;
    }

    private static boolean checkWin(char c) {
        for (int i = 0; i < fieldSizeX; i++)
            for (int j = 0; j < fieldSizeY; j++)
                if (field[i][j] == c)               //находим первый X или О на поле
                    return checkWinHorizontally(c, i, j) || checkWinVertically(c, i, j) ||
                            checkWinDiagonally(c, i, j) || checkWinDiagonally(c, i, j) ||
                            checkWinBackDiagonally(c, i, j);
        return false;
    }

    private static boolean checkWinHorizontally(char c, int y, int x) {
        int streak = 1;                             //длина цепочки из Х или О
        for (int i = x; i < fieldSizeX; i++){
            if (streak == winStreak) return true;
            if (i >=  fieldSizeX - 1) return false;
            if (streak > winStreak ) return false;
            if (field[y][i+1] != c ) return false;
            streak++;
        }
        return false;
    }

    private static boolean checkWinVertically(char c, int y, int x) {
        int streak = 1;             //длина цепочки

        for (int i = y; i < fieldSizeY; i++){
            if (streak == winStreak) return true;
            if (i >=  fieldSizeY - 1) return false;
            if (streak > winStreak ) return false;
            if (field[i+1][x] != c ) return false;
            streak++;
        }
        return false;
    }

    private static boolean checkWinDiagonally(char c, int y, int x) {
        int streak = 1;                             //длина цепочки из Х или О
        int j = y;
        for (int i = x; i < fieldSizeX; i++){ //прямая диагональ
            if (streak == winStreak) return true;
            if ((i >=  fieldSizeX - 1) || (j >=  fieldSizeY - 1)) return false;
            if (streak > winStreak ) return false;
            if (field[j+1][i+1] != c ) return false;
            streak++;
            j++;
        }
        return false;
    }

    private static boolean checkWinBackDiagonally(char c, int y, int x) {
        int streak = 1;                             //длина цепочки из Х или О
        int j = y;
        for (int i = x; i < fieldSizeX; i--){ //обратная диагональ
            if (streak == winStreak) return true;
            if ((i - 1 < 0) || j >=  fieldSizeX - 1) return false;
            if (streak > winStreak ) return false;
            if (field[j+1][i-1] != c ) return false;
            streak++;
            j++;
        }
        return false;
    }

    private static boolean isFieldFull() {
        for (int y = 0; y < fieldSizeY; y++) {
            for (int x = 0; x < fieldSizeX; x++) {
                if (field[y][x] == DOT_EMPTY)
                    return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        initMap();
        printMap();

        while(true) {
            humanTurn();
            printMap();
            if (checkWin(DOT_HUMAN)) {
                System.out.println("Выиграл игрок!");
                break;
            }
            if (isFieldFull()) {
                System.out.println("Ничья!");
                break;
            }
            aiTurn();
            printMap();
            if (checkWin(DOT_AI)) {
                System.out.println("Выиграл компьютер!");
                break;
            }
            if (isFieldFull()) {
                System.out.println("Ничья!");
                break;
            }
        }
    }
}
