
@RunWith(AndroidJUnit4.class)
public class MatrixIntrumentedTest {
   @Rule
   public ActivityTestRule<ControlPanel> activityTestRule =
           new ActivityTestRule<>(ControlPanel.class);

   @Test
   public void verifyMatrix() {
       onView(withText("Matrix")).check(matches(isDisplayed()));
   }

   @Test
   public void verifyLogin() {
       onView(withId(R.id.fab)).perform(click());

       onView(withText("Police")).perform(click());

       onView(withText("SEND")).check(matches(isDisplayed()));
   }

}

