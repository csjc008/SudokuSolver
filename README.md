# Sudoku Solver

## About the program
Written in Java, solves any sudoku very fast. 

Sample programs are FuncTest1.java~FuncTestn.java.

Currently the program will print the initial matrix and the result matrix. Will only print just one result.

For example, the very hard 1 sudoku will print:

0 0 0 0 0 0 8 0 0  
4 0 0 2 0 8 0 5 1  
0 8 3 9 0 0 0 0 7  
0 4 0 5 0 0 0 8 2  
0 0 5 0 0 0 4 0 0  
8 7 0 0 0 9 0 3 0  
2 0 0 0 0 7 1 6 0  
3 6 0 1 0 5 0 0 4  
0 0 4 0 0 0 0 0 0  

1 2 7 3 5 4 8 9 6  
4 9 6 2 7 8 3 5 1  
5 8 3 9 6 1 2 4 7  
9 4 1 5 3 6 7 8 2  
6 3 5 7 8 2 4 1 9  
8 7 2 4 1 9 6 3 5  
2 5 9 8 4 7 1 6 3  
3 6 8 1 2 5 9 7 4  
7 1 4 6 9 3 5 2 8  

## About the definition file
The program reads input from a definition file.

The file defines as follows:

Each line defines a initial number on the matrix

main-index-x[0-2] main-index-y[0-2] sub-index-x[0-2] sub-index-y[0-2] number[1-9]
