       IDENTIFICATION DIVISION.
       PROGRAM-ID. day-12-cobol.

       ENVIRONMENT DIVISION.
         INPUT-OUTPUT SECTION.
           FILE-CONTROL.
           SELECT AOC ASSIGN TO "./input/day-12.txt"
           ORGANIZATION IS LINE SEQUENTIAL.

       DATA DIVISION.
         FILE SECTION.
         FD AOC.
         01 AOC-FILE.
           05 AOC-NAME PIC X(10).

       WORKING-STORAGE SECTION.
         01 WS-FILE-STRUCTURE.
           05 WS-NAME PIC X(10).
         01 WS-EOF PIC A(1).

       PROCEDURE DIVISION.
           OPEN INPUT AOC.
               READ AOC NEXT RECORD INTO WS-FILE-STRUCTURE
                 AT END DISPLAY 'End of File'
                 NOT AT END DISPLAY WS-FILE-STRUCTURE
               END-READ.
           CLOSE AOC.
           STOP RUN.
