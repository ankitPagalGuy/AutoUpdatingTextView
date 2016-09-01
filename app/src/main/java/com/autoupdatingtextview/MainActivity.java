package com.autoupdatingtextview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;
import rx.Subscription;
import rx.functions.Action1;
import rx.subjects.PublishSubject;

public class MainActivity extends AppCompatActivity {

  @Bind(R.id.number1) EditText number1;
  @Bind(R.id.number2) EditText number2;
  @Bind(R.id.result)  TextView  result;

  Subscription subscription;
  PublishSubject<Float> resultEmitterSubject;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    resultEmitterSubject = PublishSubject.create();
    subscription = resultEmitterSubject.asObservable().subscribe(new Action1<Float>() {
      @Override
      public void call(Float aFloat) {
        result.setText(String.valueOf(aFloat));
      }
    });

    onNumberChanged();
    number2.requestFocus();
  }

  @OnTextChanged({R.id.number1, R.id.number2})
  public void onNumberChanged(){
    float num1 = 0;
    float num2 = 0;

    if (!isEmpty(number1.getText().toString())) {
      num1 = Float.parseFloat(number1.getText().toString());
    }

    if (!isEmpty(number2.getText().toString())) {
      num2 = Float.parseFloat(number2.getText().toString());
    }

    resultEmitterSubject.onNext(num1 + num2);
  }

  public static boolean isEmpty(@Nullable CharSequence str) {
    if (str == null || str.length() == 0)
      return true;
    else
      return false;
  }
}
