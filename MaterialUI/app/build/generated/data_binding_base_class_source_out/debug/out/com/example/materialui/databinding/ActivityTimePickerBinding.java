// Generated by view binder compiler. Do not edit!
package com.example.materialui.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.materialui.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityTimePickerBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final MaterialButton btnTimepicker;

  @NonNull
  public final TextInputEditText timeEditText;

  @NonNull
  public final TextInputLayout timeInputLayout;

  private ActivityTimePickerBinding(@NonNull RelativeLayout rootView,
      @NonNull MaterialButton btnTimepicker, @NonNull TextInputEditText timeEditText,
      @NonNull TextInputLayout timeInputLayout) {
    this.rootView = rootView;
    this.btnTimepicker = btnTimepicker;
    this.timeEditText = timeEditText;
    this.timeInputLayout = timeInputLayout;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityTimePickerBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityTimePickerBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_time_picker, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityTimePickerBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btn_timepicker;
      MaterialButton btnTimepicker = ViewBindings.findChildViewById(rootView, id);
      if (btnTimepicker == null) {
        break missingId;
      }

      id = R.id.time_edit_text;
      TextInputEditText timeEditText = ViewBindings.findChildViewById(rootView, id);
      if (timeEditText == null) {
        break missingId;
      }

      id = R.id.time_input_layout;
      TextInputLayout timeInputLayout = ViewBindings.findChildViewById(rootView, id);
      if (timeInputLayout == null) {
        break missingId;
      }

      return new ActivityTimePickerBinding((RelativeLayout) rootView, btnTimepicker, timeEditText,
          timeInputLayout);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
