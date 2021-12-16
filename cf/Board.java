package cf;

public class Board {

    enum State {
        TERMINAL,
        DRAW,
        NONTERMINAL
    }

    public State state;

    public char[][] start = {
            { '-', '-', '-', '-', '-', '-', '-' },
            { '-', '-', '-', '-', '-', '-', '-' },
            { '-', '-', '-', '-', '-', '-', '-' },
            { '-', '-', '-', '-', '-', '-', '-' },
            { '-', '-', '-', '-', '-', '-', '-' },
            { '-', '-', '-', '-', '-', '-', '-' }
    };
    public int[] slots = { 0, 0, 0, 0, 0, 0, 0 };

    public boolean makeMove(int column, char token) {
        if (slots[column] >= 6) {
            System.out.println("Invalid move");
            return false;
        }

        start[slots[column]][column] = token;
        slots[column]++;
        return true;
    }

    public State checkState() {
        int count = 0;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (start[i][j] != '-') {
                    if (i < 6 && j < 4) {
                        if (start[i][j] == start[i][j + 1] &&
                                start[i][j] == start[i][j + 2] &&
                                start[i][j] == start[i][j + 3])
                            return State.TERMINAL;
                    }
                    if (i < 3 && j < 7) {
                        if (start[i][j] == start[i + 1][j] &&
                                start[i][j] == start[i + 2][j] &&
                                start[i][j] == start[i + 3][j])
                            return State.TERMINAL;
                    }
                    if (i < 3 && j < 4) {
                        if (start[i][j] == start[i + 1][j + 1] &&
                                start[i][j] == start[i + 2][j + 2] &&
                                start[i][j] == start[i + 3][j + 3])
                            return State.TERMINAL;
                        if (start[i][6 - j] != '-')
                            if (start[i][6 - j] == start[i + 1][6 - j - 1] &&
                                    start[i][6 - j] == start[i + 2][6 - j - 2] &&
                                    start[i][6 - j] == start[i + 3][6 - j - 3])
                                return State.TERMINAL;
                    }
                } else
                    count++;
            }
        }

        if (count == 0) {
            return State.DRAW;
        }

        return State.NONTERMINAL;
    }

    public void draw2Darray() {
        String string = new String();

        for (int i = 0; i < 6; i++) {
            string += "\t" + start[5 - i][0] + " " +
                    start[5 - i][1] + " " +
                    start[5 - i][2] + " " +
                    start[5 - i][3] + " " +
                    start[5 - i][4] + " " +
                    start[5 - i][5] + " " +
                    start[5 - i][6] + '\n';
        }
        System.out.print(string);
        System.out.println("Col:\t1 2 3 4 5 6 7\n");
    }

}
