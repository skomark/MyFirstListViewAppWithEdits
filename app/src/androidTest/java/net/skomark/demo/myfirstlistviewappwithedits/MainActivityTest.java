package net.skomark.demo.myfirstlistviewappwithedits;

  import android.test.ActivityInstrumentationTestCase2;
  import android.widget.EditText;
  import android.widget.ListView;

  import com.robotium.solo.Solo;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {
  private Solo solo;

  public MainActivityTest() {
    super(MainActivity.class);
  }

  protected void setUp() throws Exception {
    solo = new Solo(getInstrumentation(), getActivity());
  }

  protected void tearDown() throws Exception {
    solo.finishOpenedActivities();
  }

  public void testMainActivity() {
    int timeOut = 2000;

    // Wait for main activity to load.
    assertTrue("MainActivity failed to load.",
      solo.waitForActivity(MainActivity.class, timeOut));

    // Wait until main activity completes its onCreate method.
    solo.waitForLogMessage("MainActivity created.");

    // Access the ListView control.
    ListView listView = solo.getCurrentViews(ListView.class).get(0);
    // Retrieve number of items in list.
    int repeat = listView.getAdapter().getCount();

    // Test ok button for all items in list.
    for (int i = 1; i <= repeat; i++) {
      solo.clickInList(i);

      // Wait for the EditItemActivity.
      assertTrue("EditItemActivity failed to load.",
        solo.waitForActivity(EditItemActivity.class, timeOut));

      String newText = String.format("Edit item %02d", i);
      // Change the EditText contents to the new string.
      EditText editText = (EditText) solo.getView(R.id.edit_item_edit_text);
      solo.clickOnView(editText);
      solo.clearEditText(editText);
      solo.enterText(editText, newText);
      // Press the OK button.
      solo.clickOnView(solo.getView(R.id.edit_item_btn_ok));

      // Wait for main activity to take back control.
      assertTrue("MainActivity failed to load after EditItemActivity finished.",
        solo.waitForActivity(MainActivity.class, timeOut));

      // Ensure list now contains the new string.
      assertTrue(newText + "wasn't found in the list.", solo.searchText(newText));
    }

    // Test cancel button for all items in list.
    for (int i = 1; i <= repeat; i++) {
      solo.clickInList(i);


      assertTrue("EditItemActivity failed to load.",
        solo.waitForActivity(EditItemActivity.class, timeOut));

      String newText = String.format("Cancel %02d", i);
      EditText editText = (EditText) solo.getView(R.id.edit_item_edit_text);
      solo.clickOnView(editText);
      solo.clearEditText(editText);
      solo.enterText(editText, newText);
      solo.clickOnView(solo.getView(R.id.edit_item_btn_cancel));

      assertTrue("MainActivity failed to load after EditItemActivity finished.",
        solo.waitForActivity(MainActivity.class, timeOut));

      assertTrue(newText + " was found in the list.", !solo.searchText(newText));
    }
  }
}