// Generated by view binder compiler. Do not edit!
package com.example.materialui.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.materialui.R;
import com.google.android.material.carousel.MaskableFrameLayout;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class CarouselItemBinding implements ViewBinding {
  @NonNull
  private final MaskableFrameLayout rootView;

  @NonNull
  public final ImageView carouselImageView;

  @NonNull
  public final MaskableFrameLayout carouselItemContainer;

  private CarouselItemBinding(@NonNull MaskableFrameLayout rootView,
      @NonNull ImageView carouselImageView, @NonNull MaskableFrameLayout carouselItemContainer) {
    this.rootView = rootView;
    this.carouselImageView = carouselImageView;
    this.carouselItemContainer = carouselItemContainer;
  }

  @Override
  @NonNull
  public MaskableFrameLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static CarouselItemBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static CarouselItemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.carousel_item, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static CarouselItemBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.carousel_image_view;
      ImageView carouselImageView = ViewBindings.findChildViewById(rootView, id);
      if (carouselImageView == null) {
        break missingId;
      }

      MaskableFrameLayout carouselItemContainer = (MaskableFrameLayout) rootView;

      return new CarouselItemBinding((MaskableFrameLayout) rootView, carouselImageView,
          carouselItemContainer);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
