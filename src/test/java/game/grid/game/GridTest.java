package game.grid.game;

import org.academiadecodigo.wordsgame.game.grid.game.Grid;
import org.junit.jupiter.api.*;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GridTest {

    private final String TEST_FILE_PATH = "src/test/resources/test_words.txt";
    private final String INVALID_FILE_PATH = "nonexistent.txt";
    private final int EXPECTED_ROWS = 2;
    private Grid grid;

    @BeforeEach
    public void setUp() {
        grid = new Grid(TEST_FILE_PATH);
    }

    @Test
    @DisplayName("Should return correct number of rows when reading valid file")
    public void checkRowSize_should_return_expected_rows_when_file_is_valid() {
        // given
        grid = new Grid(TEST_FILE_PATH);
        
        // when
        int actualRows = grid.getRows();
        
        // then
        assertEquals(EXPECTED_ROWS, actualRows);
    }

    @Test
    @DisplayName("Should use backup grid when file is invalid")
    public void checkRowSize_should_return_backup_rows_when_file_is_invalid() {
        // given
        grid = new Grid(INVALID_FILE_PATH);
        
        // when
        int actualRows = grid.getRows();
        
        // then
        assertEquals(8, actualRows);
    }

    @Test
    @DisplayName("Should populate word matrix correctly from test file")
    public void setWordsForMatrix_should_populate_matrix_correctly_from_test_file() {
        // given
        grid.setRows(EXPECTED_ROWS);
        String[][] expectedMatrix = {
                {"abacus    ", "barbecue  ", "chocolate ", "dolphin   ", "elephant  ", "festival  ", "gorilla   ", "harmony   ", "indigo    ", "jovial    "},
                {"kangaroo  ", "lavender  ", "mountain  ", "nectar    ", "octopus   ", "panther   ", "quality   ", "rainbow   ", "saffron   ", "tulip     "}
        };
        
        // when
        grid.setWordsForMatrix();
        
        // then
        Assertions.assertArrayEquals(expectedMatrix, Grid.getWordMatrix());
    }

    @Test
    @DisplayName("Should return formatted string representation of the matrix")
    public void drawMatrix_should_return_formatted_string_representation() {
        // given
        grid.setRows(EXPECTED_ROWS);
        grid.setWordsForMatrix();
        String expectedOutput = "abacus    barbecue  chocolate dolphin   elephant  festival  gorilla   harmony   indigo    jovial    \n" +
                "kangaroo  lavender  mountain  nectar    octopus   panther   quality   rainbow   saffron   tulip     \n";

        // when
        String result = grid.drawMatrix();

        // then
        assertEquals(expectedOutput, result);
    }

    @Nested
    @DisplayName("Player input validation scenarios")
    class PlayerInputValidation {
        
        @Test
        @DisplayName("Should return word length as score for valid input")
        public void checkPlayerInput_should_return_word_length_as_score_when_input_is_valid() throws IOException {
            // given
            grid.setRows(EXPECTED_ROWS);
            grid.setWordsForMatrix();
            String validWord = "indigo";
            int expectedScore = validWord.length();

            // when
            int actualScore = grid.checkPlayerInput(validWord);

            // then
            assertEquals(expectedScore, actualScore);
        }
        
        @Test
        @DisplayName("Should return correct score for first occurrence of word")
        public void checkPlayerInput_should_return_correct_score_for_first_word_occurrence() {
            // given
            grid.setWordsForMatrix();
            String validWord = "abacus";
            int expectedScore = 6;
            
            // when
            int actualScore = grid.checkPlayerInput(validWord);
            
            // then
            assertEquals(expectedScore, actualScore);
        }
        
        @Test
        @DisplayName("Should return zero score for already found word")
        public void checkPlayerInput_should_return_zero_score_for_already_found_word() {
            // given
            grid.setWordsForMatrix();
            String validWord = "abacus";
            grid.checkPlayerInput(validWord); // Mark word as found
            int expectedScore = 0;
            
            // when
            int actualScore = grid.checkPlayerInput(validWord);
            
            // then
            assertEquals(expectedScore, actualScore);
        }

        @Test
        @DisplayName("Should return zero score for invalid input")
        public void checkPlayerInput_should_return_zero_score_when_input_is_invalid() {
            // given
            grid.setRows(EXPECTED_ROWS);
            grid.setWordsForMatrix();
            String invalidWord = "invalid";
            int expectedScore = 0;

            // when
            int actualScore = grid.checkPlayerInput(invalidWord);

            // then
            assertEquals(expectedScore, actualScore);
        }
    }
}
