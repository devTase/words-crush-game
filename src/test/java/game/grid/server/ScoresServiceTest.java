package game.grid.server;

import org.academiadecodigo.wordsgame.game.ProjectProperties;
import org.academiadecodigo.wordsgame.game.grid.server.ScoresService;
import org.academiadecodigo.wordsgame.misc.Messages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ScoresServiceTest {

    private ScoresService scoresService;
    private ProjectProperties mockProperties;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockProperties = mock(ProjectProperties.class);
        scoresService = new ScoresService();
    }

    @Nested
    @DisplayName("Get Scores From Properties Tests")
    class GetScoresFromPropertiesTests {

        @Test
        @DisplayName("Should read stubbed ProjectProperties and return scores array")
        void getScoresFromProperties_should_read_stubbed_project_properties() {
            // Given
            try (MockedStatic<ProjectProperties> mockedStatic = mockStatic(ProjectProperties.class)) {
                mockedStatic.when(ProjectProperties::getInstance).thenReturn(mockProperties);
                
                when(mockProperties.getProperty("server.grid.score.0")).thenReturn("10");
                when(mockProperties.getProperty("server.grid.score.1")).thenReturn("25");
                when(mockProperties.getProperty("server.grid.score.2")).thenReturn("50");
                when(mockProperties.getProperty("server.grid.score.3")).thenReturn("100");
                when(mockProperties.getProperty("server.grid.score.4")).thenReturn("200");
                when(mockProperties.getProperty("server.grid.score.5")).thenReturn("500");
                
                ScoresService testScoresService = new ScoresService();
                
                // When
                Integer[] scores = testScoresService.getScoresFromProperties();
                
                // Then
                assertEquals(6, scores.length);
                assertEquals(10, scores[0]);
                assertEquals(25, scores[1]);
                assertEquals(50, scores[2]);
                assertEquals(100, scores[3]);
                assertEquals(200, scores[4]);
                assertEquals(500, scores[5]);
                
                verify(mockProperties).getProperty("server.grid.score.0");
                verify(mockProperties).getProperty("server.grid.score.1");
                verify(mockProperties).getProperty("server.grid.score.2");
                verify(mockProperties).getProperty("server.grid.score.3");
                verify(mockProperties).getProperty("server.grid.score.4");
                verify(mockProperties).getProperty("server.grid.score.5");
            }
        }
    }

    @Nested
    @DisplayName("Get Nearest Value Tests")
    class GetNearestValueTests {

        @ParameterizedTest
        @MethodSource("nearestValueTestCases")
        @DisplayName("Should return nearest value for given score")
        void getNearestValue_should_return_correct_nearest_value(int score, Integer[] scoreArray, int expected) {
            // When
            int result = scoresService.getNearestValue(score, scoreArray);
            
            // Then
            assertEquals(expected, result);
        }

        static Stream<Arguments> nearestValueTestCases() {
            return Stream.of(
                Arguments.of(5, new Integer[]{10, 25, 50, 100}, 0),
                Arguments.of(15, new Integer[]{10, 25, 50, 100}, 10),
                Arguments.of(30, new Integer[]{10, 25, 50, 100}, 25),
                Arguments.of(75, new Integer[]{10, 25, 50, 100}, 50),
                Arguments.of(150, new Integer[]{10, 25, 50, 100}, 100),
                Arguments.of(10, new Integer[]{10, 25, 50, 100}, 10),
                Arguments.of(25, new Integer[]{10, 25, 50, 100}, 25),
                Arguments.of(0, new Integer[]{10, 25, 50, 100}, 0)
            );
        }
    }

    @Nested
    @DisplayName("Get Score Text Tests")
    class GetScoreTextTests {

        @Test
        @DisplayName("Should return correct Messages value using mock")
        void getScoreText_should_return_correct_messages_value() {
            // Given
            int option = 3;
            String expectedMessage = "P.Score > Excellent | ";
            
            try (MockedStatic<Messages> mockedMessages = mockStatic(Messages.class)) {
                mockedMessages.when(() -> Messages.getScoreMessage(option)).thenReturn(expectedMessage);
                
                // When
                String result = scoresService.getScoreText(option);
                
                // Then
                assertEquals(expectedMessage, result);
                mockedMessages.verify(() -> Messages.getScoreMessage(option));
            }
        }

        @ParameterizedTest
        @MethodSource("scoreTextTestCases")
        @DisplayName("Should return correct score text for different options")
        void getScoreText_should_return_correct_text_for_different_options(int option, String mockMessage) {
            // Given
            try (MockedStatic<Messages> mockedMessages = mockStatic(Messages.class)) {
                mockedMessages.when(() -> Messages.getScoreMessage(option)).thenReturn(mockMessage);
                
                // When
                String result = scoresService.getScoreText(option);
                
                // Then
                assertEquals(mockMessage, result);
                mockedMessages.verify(() -> Messages.getScoreMessage(option));
            }
        }

        static Stream<Arguments> scoreTextTestCases() {
            return Stream.of(
                Arguments.of(0, "P.Score > Beginner | "),
                Arguments.of(1, "P.Score > Amateur | "),
                Arguments.of(2, "P.Score > Good | "),
                Arguments.of(3, "P.Score > Very Good | "),
                Arguments.of(4, "P.Score > Excellent | "),
                Arguments.of(5, "P.Score > Master | "),
                Arguments.of(6, "P.Score > Legend | ")
            );
        }
    }
}
