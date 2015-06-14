package net.skomark.demo.myfirstlistviewappwithedits;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditItemActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_item);

    setTitle(getString(R.string.edit_item_title));

    // Retrieve data passed by the main activity.
    Intent data = getIntent();
    final String value = data.getStringExtra("ItemValue");
    final int position = data.getIntExtra("ItemPosition", -1);

    final Button buttonOk = (Button) findViewById(R.id.edit_item_btn_ok);
    final Button buttonCancel = (Button) findViewById(R.id.edit_item_btn_cancel);
    final EditText editText = (EditText) findViewById(R.id.edit_item_edit_text);

    if (value != null){
      editText.setText(value);
    }

    // Disable the OK button until the contents of the EditText change.
    buttonOk.setEnabled(false);
    buttonOk.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // Pass data back to main activity.
        Intent data = new Intent();
        data.putExtra("ItemPosition", position);
        data.putExtra("ItemValue", editText.getText().toString());
        // Notify calling activity the user accepted changes.
        setResult(RESULT_OK, data);
        // End execution.
        finish();
      }
    });

    buttonCancel.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // Notify calling activity the user discarded changes.
        setResult(RESULT_CANCELED);
        // End execution.
        finish();
      }
    });

    editText.addTextChangedListener(new TextWatcher() {
      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        // Enable the OK button if new text is different to the original.
        buttonOk.setEnabled(editText.getText().toString() != value);
      }

      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {
      }

      @Override
      public void afterTextChanged(Editable s) {
      }
    });
  }
}
