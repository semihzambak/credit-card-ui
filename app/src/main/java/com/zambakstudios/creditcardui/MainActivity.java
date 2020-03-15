package com.zambakstudios.creditcardui;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView cardNumberText, cardUserText, cardMMText, cardYYText, cardCvvText;
    private EditText cardNumber, cardUser, cardMM, cardYY, cardCvv;

    private RelativeLayout card, front, back;
    private boolean isFront;

    String month = "01";
    String year = "20";

    private void init() {
        cardNumberText = findViewById(R.id.card_number_text);
        cardUserText = findViewById(R.id.card_user_text);
        cardMMText = findViewById(R.id.card_mm_text);
        cardYYText = findViewById(R.id.card_yy_text);
        cardCvvText = findViewById(R.id.cvv_text);

        cardNumber = findViewById(R.id.card_number_edit);
        cardUser = findViewById(R.id.card_user_edit);
        cardMM = findViewById(R.id.card_mm_edit);
        cardYY = findViewById(R.id.card_yy_edit);
        cardCvv = findViewById(R.id.card_cvv_edit);

        card = findViewById(R.id.credit_card);
        front = findViewById(R.id.front);
        back = findViewById(R.id.back);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        isFront = true;

        cardNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                focusChange(hasFocus, cardNumberText, false);
            }
        });
        cardNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                cardNumberText.setText(cardNumberFormatter(s.toString()));
            }
        });

        cardUser.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                focusChange(hasFocus, cardUserText, false);
            }
        });

        cardUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 15)
                    cardUserText.setText(s.toString().substring(0, 15) + "...");
                else
                    cardUserText.setText(s.toString());
            }
        });

        cardMM.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                focusChange(hasFocus, cardMMText, false);
            }
        });

        cardMM.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    if (s.length() <= 2) {
                        int monthTemp = Integer.parseInt(s.toString());
                        if (monthTemp < 10 && monthTemp > 0) {
                            month = "0" + String.valueOf(monthTemp);
                        } else if (monthTemp >= 10 && monthTemp <= 12) {
                            month = String.valueOf(monthTemp);
                        } else {
                            month = "0" + (monthTemp / 10);
                        }
                    }
                    cardMMText.setText(month);
                }
            }
        });

        cardYY.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                focusChange(hasFocus, cardYYText, false);
            }
        });
        cardYY.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    if (s.length() <= 2) {
                        int yearTemp = Integer.parseInt(s.toString());
                        if (yearTemp >= 20) {
                            year = String.valueOf(yearTemp);
                        } else {
                            year = "20";
                        }
                    }
                    cardYYText.setText(year);
                }
            }
        });

        cardCvv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                focusChange(hasFocus, cardCvvText, true);
            }
        });

        cardCvv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                cardCvvText.setText(s.toString());
            }
        });

        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardFlip();
            }
        });
    }

    public void focusChange(boolean hasFocus, TextView tv, boolean isBack) {
        if (hasFocus) {
            if (isFront == isBack) {
                cardFlip();
            }
            tv.setBackgroundResource(R.drawable.credit_card_input);
        } else {
            tv.setBackgroundResource(0);
        }
    }


    public void cardFlip() {
        final ObjectAnimator oa1 = ObjectAnimator.ofFloat(card, "scaleX", 1f, 0f);
        final ObjectAnimator oa2 = ObjectAnimator.ofFloat(card, "scaleX", 0f, 1f);
        oa1.setInterpolator(new DecelerateInterpolator());
        oa2.setInterpolator(new AccelerateDecelerateInterpolator());
        oa1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (isFront) {
                    front.setVisibility(View.INVISIBLE);
                    back.setVisibility(View.VISIBLE);
                    isFront = false;
                } else {
                    front.setVisibility(View.VISIBLE);
                    back.setVisibility(View.INVISIBLE);
                    isFront = true;
                }
                oa2.start();
            }
        });
        oa1.start();
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        View view = getCurrentFocus();
        boolean ret = super.dispatchTouchEvent(event);

        if (view instanceof EditText) {
            try {
                View w = getCurrentFocus();
                int scrcords[] = new int[2];
                w.getLocationOnScreen(scrcords);
                float x = event.getRawX() + w.getLeft() - scrcords[0];
                float y = event.getRawY() + w.getTop() - scrcords[1];

                if (event.getAction() == MotionEvent.ACTION_UP && (x < w.getLeft() || x >= w.getRight() || y < w.getTop() || y > w.getBottom())) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (getWindow() != null && getWindow().getCurrentFocus() != null) {
                        imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
                        view.clearFocus();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    public String cardNumberFormatter(String s) {
        String tmp = "";
        for (int i = 0; i < s.length(); i++) {
            if (i % 4 == 0 && i != 0) {
                tmp += " ";
            }
            tmp += s.substring(i, i + 1);
        }
        return tmp;
    }
}