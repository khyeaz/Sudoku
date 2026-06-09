package com.sudoku.game;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EndtoEndTest {
    SudokuGame sudokuGame;
    static PrintStream originalOut;
    static ByteArrayOutputStream newOut;
    static String LS = System.lineSeparator();

    @BeforeAll
    public static void hijackSysOut() {
        originalOut =  System.out;
        newOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(newOut));
    }

    @AfterAll
    public static void restoreSysOut() {
        System.setOut(originalOut);
    }


    @BeforeEach
    @SuppressWarnings("unused")
    void initialise() {
        sudokuGame = new SudokuGame();
    }

    @AfterEach
    public void resetOut() {
        newOut.reset();
    }

    @Test
    void testRunPassed() {
        //String solved = "617248539\n395716842\n842593176\n284369715\n539471268\n761825493\n158934627\n926157384\n473682951";
        String puzzle   = "_17248539\n3957168_2\n8425931_6\n28_369715\n539_71268\n76_825493\n1589346_7\n9261573_4\n473_82951";
        String commands = "A1 6" + LS + "B8 4" + LS + "C8 7" + LS + "D3 4" + LS + "E4 4" + LS + "F3 1" + LS + "G8 2" + LS + "H8 8" + LS + "I4 6" + LS + LS +"quit";

        InputStream stdin = System.in;
        try {
            System.setIn(new ByteArrayInputStream(commands.getBytes()));
            sudokuGame.run(0, puzzle);

            String allOut = newOut.toString();
            String[] split = allOut.split(LS);
            int moveAcceptedCount = 0;
            for (String string : split) {
                if (IOHandler.ACCEPTED.equals(string)) {
                    moveAcceptedCount++;
                }
            }
            assertEquals(9, moveAcceptedCount, "All moves should be accepted");
            assertTrue(allOut.contains(IOHandler.COMPLETE));
            assertFalse(allOut.contains(IOHandler.UNEXPECTED_ERROR));
            assertFalse(allOut.contains(IOHandler.INVALID_COMMAND));
        } catch (Exception e) {
            fail("Should not encounter exception");
        } finally {
            System.setIn(stdin);
        }
    }

    @Test
    void testHint() {
        //String solved = "961527843\n284963175\n537184629\n429751386\n176438952\n853296417\n692815734\n715349268\n348672591";
        String puzzle   = "961527843\n284963175\n53718462_\n429751386\n176438952\n853296417\n692815734\n715349268\n348672591";
        String commands = "hint" + LS + "quit";
        String expected = "Hint: Cell C9 = 9";

        InputStream stdin = System.in;
        try {
            System.setIn(new ByteArrayInputStream(commands.getBytes()));
            sudokuGame.run(0, puzzle);

            String allOut = newOut.toString();
            assertTrue(allOut.contains(expected));

        } catch (Exception e) {
            fail("Should not encounter exception");
        } finally {
            System.setIn(stdin);
        }
    }

    @Test
    void testCheckPassed() {
        //String solved = "617248539\n395716842\n842593176\n284369715\n539471268\n761825493\n158934627\n926157384\n473682951";
        String puzzle   = "_17248539\n3957168_2\n8425931_6\n28_369715\n539_71268\n76_825493\n1589346_7\n9261573_4\n473_82951";
        String commands = "A1 6" + LS + "B8 4" + LS + "C8 7" + LS + "D3 4" + LS + "E4 4" + LS + "F3 1" + LS + "G8 2" + LS + "H8 8" + LS + "check" + LS + "quit";


        InputStream stdin = System.in;
        try {
            System.setIn(new ByteArrayInputStream(commands.getBytes()));
            sudokuGame.run(0, puzzle);

            String allOut = newOut.toString();
            assertTrue(allOut.contains(IOHandler.CHECK_PASSED));
            assertFalse(allOut.contains(IOHandler.CHECK_FAILED1));
            assertFalse(allOut.contains(IOHandler.CHECK_FAILED2));
        } catch (Exception e) {
            fail("Should not encounter exception");
        } finally {
            System.setIn(stdin);
        }
    }

    @Test
    void testCheckFailed() {
        //String solved = "581672439\n792843651\n364591782\n438957216\n256184973\n179326845\n845219367\n913768524\n627435198";
        String puzzle = "___6__4__\n7____36__\n____91_8_\n_________\n_5_18___3\n___3_6_45\n_4_2___6_\n9_3______\n_2____1__";
        String commands = "A1 6" + LS + "check" + LS + "quit";


        InputStream stdin = System.in;
        try {
            System.setIn(new ByteArrayInputStream(commands.getBytes()));
            sudokuGame.run(0, puzzle);

            String allOut = newOut.toString();
            assertFalse(allOut.contains(IOHandler.CHECK_PASSED));
            assertTrue(allOut.contains(IOHandler.CHECK_FAILED1 + 6 + IOHandler.CHECK_FAILED2 + "Row A"));
        } catch (Exception e) {
            fail("Should not encounter exception");
        } finally {
            System.setIn(stdin);
        }
    }

    @Test
    void testClearSuccess() {
        //String solved = "581672439\n792843651\n364591782\n438957216\n256184973\n179326845\n845219367\n913768524\n627435198";
        String puzzle   = "___6__4__\n7____36__\n____91_8_\n_________\n_5_18___3\n___3_6_45\n_4_2___6_\n9_3______\n_2____1__";
        String commands = "A1 5" + LS + "A1 clear" + LS + "quit";


        InputStream stdin = System.in;
        try {
            System.setIn(new ByteArrayInputStream(commands.getBytes()));
            sudokuGame.run(0, puzzle);

            String allOut = newOut.toString();
            assertTrue(allOut.contains(IOHandler.CLEARED + "A1"));
            assertFalse(allOut.contains(IOHandler.PREFILLED1));
        } catch (Exception e) {
            fail("Should not encounter exception");
        } finally {
            System.setIn(stdin);
        }
    }

    @Test
    void testClearFailed() {
        //String solved = "581672439\n792843651\n364591782\n438957216\n256184973\n179326845\n845219367\n913768524\n627435198";
        String puzzle   = "___6__4__\n7____36__\n____91_8_\n_________\n_5_18___3\n___3_6_45\n_4_2___6_\n9_3______\n_2____1__";
        String commands = "A4 clear" + LS + "quit";


        InputStream stdin = System.in;
        try {
            System.setIn(new ByteArrayInputStream(commands.getBytes()));
            sudokuGame.run(0, puzzle);

            String allOut = newOut.toString();
            assertFalse(allOut.contains(IOHandler.CLEARED + "A1"));
            assertTrue(allOut.contains(IOHandler.PREFILLED1 + "A4" + IOHandler.PREFILLED2));
        } catch (Exception e) {
            fail("Should not encounter exception");
        } finally {
            System.setIn(stdin);
        }
    }

    @Test
    void testFillFailed() {
        //String solved = "581672439\n792843651\n364591782\n438957216\n256184973\n179326845\n845219367\n913768524\n627435198";
        String puzzle   = "___6__4__\n7____36__\n____91_8_\n_________\n_5_18___3\n___3_6_45\n_4_2___6_\n9_3______\n_2____1__";
        String commands = "A4 3" + LS + "quit";


        InputStream stdin = System.in;
        try {
            System.setIn(new ByteArrayInputStream(commands.getBytes()));
            sudokuGame.run(0, puzzle);

            String allOut = newOut.toString();
            assertFalse(allOut.contains(IOHandler.ACCEPTED));
            assertTrue(allOut.contains(IOHandler.PREFILLED1 + "A4" + IOHandler.PREFILLED2));
        } catch (Exception e) {
            fail("Should not encounter exception");
        } finally {
            System.setIn(stdin);
        }
    }

    @Test
    void testSecondround() {
        //String solved = "581672439\n792843651\n364591782\n438957216\n256184973\n179326845\n845219367\n913768524\n627435198";
        String puzzle   = "_81672439\n792843651\n364591782\n438957216\n256184973\n179326845\n845219367\n913768524\n627435198";
        String commands = "A1 5" + LS + LS + "quit";


        InputStream stdin = System.in;
        try {
            System.setIn(new ByteArrayInputStream(commands.getBytes()));
            sudokuGame.run(0, puzzle);

            String allOut = newOut.toString();

            String[] split = allOut.split(LS);
            int newGameCount = 0;
            for (String string : split) {
                if (IOHandler.START.equals(string)) {
                    newGameCount++;
                }
            }
            assertEquals(2, newGameCount);
            assertTrue(allOut.contains(IOHandler.COMPLETE));
        } catch (Exception e) {
            fail("Should not encounter exception");
        } finally {
            System.setIn(stdin);
        }
    }
}
