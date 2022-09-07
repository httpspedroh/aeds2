class Cell {

    public int element;
    public Cell down, up, left, right;
 
    public Cell() { this(Integer.MIN_VALUE); }
 
    public Cell(int element) { this(element, null, null, null, null); }
 
    public Cell(int element, Cell down, Cell up, Cell left, Cell right) {

        this.element = element;
        this.down = down;
        this.up = up;
        this.left = left;
        this.right = right;
    }
}
 
// ------------------------------------------------------------------------------------------------------------------------------------ //

class Matrix {

    private Cell start;
	private int lines, columns;

	public Matrix(int lines, int columns) {

        this.lines = lines;
		this.columns = columns;

        Cell[][] cells = new Cell[lines][columns];

        for(int x = 0; x < lines; x++) {

            for(int y = 0; y < columns; y++) cells[x][y] = new Cell();
        }

        for(int x = 0; x < lines; x++) {

            for(int y = 0; y < columns; y++) {

                if(x > 0) cells[x][y].up = cells[x - 1][y];
                if(y > 0) cells[x][y].left = cells[x][y - 1];
                if(x < lines - 1) cells[x][y].down = cells[x + 1][y];
                if(y < columns - 1) cells[x][y].right = cells[x][y + 1];
            }
        }

        start = cells[0][0];
	}

    public boolean insert(int element) throws Exception {

        Cell next = start;

        for(int x = 0; x < lines; x++) {

            for(int y = 0; y < columns; y++) {

                if(next.element == Integer.MIN_VALUE) {
                    
                    next.element = element;
                    return true;
                }

                next = next.right;
            }

            next = start.down;

            for(int z = 0; z < x; z++) next = next.down;
        }
       
        throw new Exception("x Insert error: Max size reached!");
    }

    public void print() {

        Cell next = start;

        for(int x = 0; x < lines; x++) {

            for(int y = 0; y < columns; y++) {

                System.out.print((next.element == Integer.MIN_VALUE ? "-" : next.element) + " ");

                next = next.right;
            }

            System.out.println("");

            next = start.down;

            for(int z = 0; z < x; z++) next = next.down;
        }
    }

    public void printMainDiagonal() {

        if(lines == columns) {
            
            Cell next = start;

            for(int i = 0; i < lines; i++) {

                if(i > 0) next = next.right.down;

                System.out.print(next.element + " ");
            }

            System.out.println("");
        }
    }

    public void printSecDiagonal() {

        if(lines == columns) {

            Cell next = start;

            for(int i = 0; i < columns - 1; i++) next = next.right;

            for(int i = 0; i < lines; i++) {

                if(i > 0) next = next.left.down;

                System.out.print(next.element + " ");
            }

            System.out.println("");
        }
    }

    public Matrix sum(Matrix matrix) {

        Matrix resp = new Matrix(lines, columns);
        Cell next[] = new Cell[3];

        next[0] = start;
        next[1] = matrix.start;
        next[2] = resp.start;

        for(int x = 0; x < lines; x++) {

            for(int y = 0; y < columns; y++) {

                next[2].element = next[0].element + next[1].element;
            
                for(int i = 0; i < 3; i++) next[i] = next[i].right;
            }

            next[0] = start.down;
            next[1] = matrix.start.down;
            next[2] = resp.start.down;

            for(int z = 0; z < x; z++) {

                for(int i = 0; i < 3; i++) next[i] = next[i].down;
            }
        }
        return resp;
    }

    public Matrix multiply(Matrix matrix) {

        Matrix resp = new Matrix(lines, columns);
        Cell next[] = new Cell[3];

        next[0] = start;
        next[1] = matrix.start;
        next[2] = resp.start;

        Cell line_begin = next[0];
            
        for(int x = 0; x < lines; x++) {

            Cell column_begin = next[1];

            for(int y = 0; y < columns; y++) {

                int sum = 0;

                for(int z = 0; z < lines; z++) {

                    sum += (next[0].element * next[1].element);

                    next[0] = next[0].right;
                    next[1] = next[1].down;
                }

                next[2].element = sum;
                sum = 0;

                next[0] = line_begin;
                next[1] = column_begin.right;
                
                if(next[2].right != null) next[2] = next[2].right;
            }

            // ------------------------------- //

            line_begin = next[0] = line_begin.down;
            next[1] = matrix.start;

            for(int y = 0; y < columns - 1; y++) next[2] = next[2].left;

            next[2] = next[2].down;
        }
        return resp;
    }

    // ------------------------------------------------------------------------------------------------ //

    public static void main(String[] args) {
    
        // ------------------------------------------------------------ //

        MyIO.setCharset("utf-8");

        int cases = MyIO.readInt();
        Matrix matrices[][] = new Matrix[cases][2];

        for(int x = 0; x < cases; x++) {

            for(int y = 0; y < 2; y++) {

                int lines, columns;

                lines = MyIO.readInt();
                columns = MyIO.readInt();
                matrices[x][y] = new Matrix(lines, columns);

                for(int l = 0; l < lines; l++) {

                    String line = MyIO.readLine();
                    String s_elements[] = line.split(" ");

                    for(int i = 0; i < s_elements.length; i++) {
                        
                        try { matrices[x][y].insert(Integer.parseInt(s_elements[i])); }
                        catch(java.lang.Exception e) { e.printStackTrace(); }
                    }
                }

                // ----------------------------------------------------------------------- //

                if(y == 0) {

                    matrices[x][0].printMainDiagonal();
                    matrices[x][0].printSecDiagonal();
                }
                else {

                    matrices[x][0].sum(matrices[x][1]).print();
                    matrices[x][0].multiply(matrices[x][1]).print();
                }
            }
        }
    }
}