/**
 * Test Conventions - Living Guideline
 * 
 * This class serves as documentation for our testing conventions and standards.
 * All test classes should follow these guidelines for consistency and maintainability.
 * 
 * @author devTASE
 * @version 1.0
 * @since 2024
 */
public class TestConventions {

    /**
     * RULE 1: @DisplayName on EVERY test (MANDATORY)
     * 
     * Every single test method MUST have a @DisplayName annotation for better 
     * understanding in the console output. The display name should clearly 
     * describe what behavior is being tested.
     * 
     * Example:
     * <pre>
     * {@code
     * @Test
     * @DisplayName("Should return empty list when no users exist")
     * void getUserList_should_return_empty_list_when_no_users_exist() {
     *     // test implementation
     * }
     * }
     * </pre>
     */
    
    /**
     * RULE 2: Given-When-Then Structure (MANDATORY)
     * 
     * All tests MUST follow the Given-When-Then pattern with clear comments:
     * - Given: Setup the initial state and preconditions
     * - When: Execute the action being tested  
     * - Then: Verify the expected outcome
     * 
     * Example:
     * <pre>
     * {@code
     * @Test
     * @DisplayName("Should calculate total price when adding valid items")
     * void calculateTotal_should_return_correct_sum_when_valid_items_added() {
     *     // Given
     *     ShoppingCart cart = new ShoppingCart();
     *     Item item1 = new Item("Book", 10.50);
     *     Item item2 = new Item("Pen", 2.30);
     *     
     *     // When
     *     cart.addItem(item1);
     *     cart.addItem(item2);
     *     BigDecimal total = cart.calculateTotal();
     *     
     *     // Then
     *     assertEquals(new BigDecimal("12.80"), total);
     * }
     * }
     * </pre>
     */
    
    /**
     * RULE 3: Constructor Injection for SUT (System Under Test)
     * 
     * Use constructor injection to inject dependencies into the SUT.
     * This makes testing easier and promotes good design by making 
     * dependencies explicit.
     * 
     * Example:
     * <pre>
     * {@code
     * class UserServiceTest {
     *     
     *     private final UserRepository userRepository = mock(UserRepository.class);
     *     private final EmailService emailService = mock(EmailService.class);
     *     private final UserService userService = new UserService(userRepository, emailService);
     *     
     *     @Test
     *     @DisplayName("Should send welcome email when user is created successfully")
     *     void createUser_should_send_welcome_email_when_user_created_successfully() {
     *         // Given
     *         CreateUserRequest request = new CreateUserRequest("john@example.com", "John");
     *         User savedUser = new User(1L, "john@example.com", "John");
     *         when(userRepository.save(any(User.class))).thenReturn(savedUser);
     *         
     *         // When
     *         userService.createUser(request);
     *         
     *         // Then
     *         verify(emailService).sendWelcomeEmail("john@example.com", "John");
     *     }
     * }
     * }
     * </pre>
     */
    
    /**
     * RULE 4: One Assert Per Behavior (MANDATORY)
     * 
     * Each test should validate ONE behavior and have ONE reason to fail.
     * If you need multiple asserts, they should all be validating the same 
     * logical behavior from different angles.
     * 
     * ❌ BAD Example - Testing multiple behaviors:
     * <pre>
     * {@code
     * @Test
     * @DisplayName("Should handle user operations")
     * void userOperations() {
     *     User user = userService.createUser("john@example.com");
     *     assertEquals("john@example.com", user.getEmail()); // behavior 1
     *     
     *     userService.deleteUser(user.getId());
     *     assertFalse(userService.userExists(user.getId())); // behavior 2 
     * }
     * }
     * </pre>
     * 
     * ✅ GOOD Example - One behavior, multiple validations:
     * <pre>
     * {@code
     * @Test
     * @DisplayName("Should create user with correct properties when valid email provided")
     * void createUser_should_set_correct_properties_when_valid_email_provided() {
     *     // Given
     *     String email = "john@example.com";
     *     
     *     // When
     *     User user = userService.createUser(email);
     *     
     *     // Then - All asserts validate the same behavior (user creation)
     *     assertNotNull(user);
     *     assertEquals(email, user.getEmail());
     *     assertNotNull(user.getId());
     *     assertTrue(user.isActive());
     * }
     * }
     * </pre>
     */
    
    /**
     * ADDITIONAL CONVENTIONS:
     * 
     * - Test methods should be named: methodName_should_expectedBehavior_when_condition()
     * - Use @Nested for organizing related test scenarios
     * - Mock only collaborations, never the class being tested
     * - Tests should be fast, isolated, and predictable
     * - No network, disk, database, or filesystem calls in unit tests
     * - Use ParameterizedTest when testing different inputs for same behavior
     * - Tests are living documentation - they should be readable and clear
     * - Use Mockito for mocking in unit tests
     * - Integration tests should end with *IT.java
     * - Use Karate framework for integration tests when applicable
     */
}
