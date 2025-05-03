package com.example.connect4;

public class Column {
    // cell value = 0 for empty, 1 for pc, 2 for human
    byte length;
    byte filledCells;
    short cells;

    public Column(){
        length = 6;
        cells = 0;
        filledCells = 0;
    }
    public Column clone(){
        Column copy = new Column();
        copy.cells = this.cells;
        copy.length = this.length;
        copy.filledCells = this.filledCells;
        return copy;
    }
    public short getCells() {
        return this.cells;
    }

    public boolean add(int turn){
        if(filledCells == length || turn < 1 || turn > 2){
//            System.out.println("False col, turn = " + turn + " len = " + length + " filled = " + filledCells);
            return false;
        }
        short input = (short) (turn << filledCells*2);
        cells = (short) (cells | input);
        filledCells++;
        return true;
    }

    public int get(int index){
        if(index < 0 || index >= length){
            return -1;
        }
        int mask = 3 << index * 2;
        return (mask & (int)cells) >> (index * 2);
    }

    public boolean isFilled(){
        return filledCells == length;
    }
    public void print(){
        System.out.println("Length = " + length);
        System.out.println("Filled = " + filledCells);
        System.out.println("Cells = " + cells);
    }

    public static void main(String[] args) {
        Column column = new Column();
        column.print();
        column.add(1);
        column.add(2);
        column.add(1);
        column.add(2);
        column.add(1);
        System.out.println(column.get(0));
        System.out.println(column.get(1));
        System.out.println(column.get(2));
        System.out.println(column.get(3));
        System.out.println(column.get(4));
        Column c = column.clone();
        System.out.println("---------");
        System.out.println(c.get(0));
        System.out.println(c.get(1));
        System.out.println(c.get(2));
        System.out.println(c.get(3));
        System.out.println(c.get(4));
        System.out.println(column + "      " + c);

    }
}
