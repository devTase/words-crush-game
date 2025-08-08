package misc;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import org.academiadecodigo.wordsgame.misc.Colors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ColorsTest {

    @Test
    @DisplayName("Should ensure all color constants are not null and start with escape sequence")
    void allColorConstants_should_be_not_null_and_start_with_escape_sequence() throws IllegalAccessException {
        // Given
        Field[] fields = Colors.class.getDeclaredFields();
        String expectedPrefix = "\033[";

        // When & Then
        for (Field field : fields) {
            if (field.getType() == String.class
                    && field.getName().equals(field.getName().toUpperCase())) {
                field.setAccessible(true);
                String colorValue = (String) field.get(null);

                assertNotNull(colorValue, "Color constant " + field.getName() + " should not be null");
                assertTrue(
                        colorValue.startsWith(expectedPrefix),
                        "Color constant " + field.getName() + " should start with \\033[");
            }
        }
    }

    @Test
    @DisplayName("Should verify specific color constants integrity")
    void specificColorConstants_should_have_correct_format() {
        // Given & When & Then
        assertNotNull(Colors.RESET);
        assertTrue(Colors.RESET.startsWith("\033["));

        assertNotNull(Colors.RED);
        assertTrue(Colors.RED.startsWith("\033["));

        assertNotNull(Colors.GREEN);
        assertTrue(Colors.GREEN.startsWith("\033["));

        assertNotNull(Colors.BLUE);
        assertTrue(Colors.BLUE.startsWith("\033["));

        assertNotNull(Colors.YELLOW);
        assertTrue(Colors.YELLOW.startsWith("\033["));

        assertNotNull(Colors.BLACK_BOLD);
        assertTrue(Colors.BLACK_BOLD.startsWith("\033["));

        assertNotNull(Colors.RED_BACKGROUND);
        assertTrue(Colors.RED_BACKGROUND.startsWith("\033["));

        assertNotNull(Colors.CYAN_BRIGHT);
        assertTrue(Colors.CYAN_BRIGHT.startsWith("\033["));
    }

    @Test
    @DisplayName("Should verify RESET constant has correct value")
    void resetConstant_should_have_correct_value() {
        // Given & When & Then
        assertEquals("\033[0m", Colors.RESET);
    }
}
