package cn.msxf0.calculater;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import parsii.eval.Expression;
import parsii.eval.Parser;
import parsii.eval.Scope;
import parsii.tokenizer.ParseException;


public class MainActivity extends Activity implements View.OnClickListener {
    private StringBuffer strbuf;
    private TextView tv_answer;
    private TextView tv_input;
    private Button btn_calc;
    private Scope scope;
    private LinearLayout liner;
    private int flagcalc;
    List<String> list;
    @Override
    protected void onResume() {

        Snackbar snackbar = Snackbar.make(liner, "长按答案可以清空哦～", Snackbar.LENGTH_INDEFINITE);
        View snackbarView = snackbar.getView();
        snackbar.setAction("了解", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        TextView tv_snackbar = (TextView) snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
        Button bt_snackbar = (Button) snackbarView.findViewById(com.google.android.material.R.id.snackbar_action);
        bt_snackbar.setTextColor(Color.rgb(0xff, 0xff, 0xff));
        tv_snackbar.setTextColor(Color.rgb(0xD8, 0x1B, 0x60));
        snackbar.show();
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        strbuf = new StringBuffer();
        tv_answer = findViewById(R.id.answer);
        btn_calc = findViewById(R.id.calc);
        liner = findViewById(R.id.liner);
        tv_input = findViewById(R.id.input);
        flagcalc = 0;
        list = new ArrayList<>();
        list.add("+");
        list.add("-");
        list.add("*");
        list.add("/");



        tv_input.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                strbuf = new StringBuffer();
                tv_answer.setText("0");
                tv_input.setText("0");
                System.out.println("a");;
                flagcalc = 0;
                return false;
            }
        });
        btn_calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calc(1);
                flagcalc = 1;

            }
        });


    }

    @Override
    public void onClick(View view) {
        Button btn = (Button) view;

        if (list.contains(btn.getText()) && flagcalc ==1){
            strbuf.append(btn.getText());
        }else if (flagcalc ==1 ){
            strbuf = new StringBuffer(btn.getText());

        }else {
            strbuf.append(btn.getText());
        }
        flagcalc = 0;
        tv_input.setText(strbuf);
        calc(0);

    }

    private void calc(int flag){
        scope = new Scope();
        try {
            Expression expr = Parser.parse(strbuf.toString(), scope);
            Double answer = new Double(expr.evaluate());

            Integer answer_int =new Integer(answer.intValue());
            if(answer.doubleValue() == answer_int){
                if(flag==1){
                    tv_answer.setText("");
                    tv_input.setText(answer_int.toString());
                    strbuf = new StringBuffer(answer_int.toString());
                }else {
                    tv_answer.setText("= "+answer_int.toString());
                    //strbuf = new StringBuffer(answer_int.toString());
                }


            }else{
                if(flag ==1){
                    tv_answer.setText("");
                    tv_input.setText("= "+answer.toString());
                    strbuf = new StringBuffer(answer.toString());
                }else {
                    tv_answer.setText("= "+answer.toString());
                    //strbuf = new StringBuffer(answer.toString());
                }

            }



        } catch (ParseException e) {
            //strbuf = new StringBuffer();
            tv_answer.setText("非法输入");

            e.printStackTrace();
        }
        if(tv_answer.getText().equals("")){
            //tv_answer.setVisibility(View.GONE);
            tv_input.setTextSize(TypedValue.COMPLEX_UNIT_PT,22);
        }else {
            //tv_answer.setVisibility(View.VISIBLE);
            tv_input.setTextSize(TypedValue.COMPLEX_UNIT_PT,16);


        }
    }
}

