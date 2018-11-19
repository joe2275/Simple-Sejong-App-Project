package com.example.whgml.sejongapps.Activity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.whgml.sejongapps.Helper.PrimeNumber;
import com.example.whgml.sejongapps.Helper.WebCrawler;
import com.example.whgml.sejongapps.R;

import org.w3c.dom.Text;

public class PrimeActivity extends Fragment implements View.OnClickListener {

    private Button isPrimeBtn;
    private TextView primeText;
    private TextView oddText;
    private TextView dividedText;
    private TextView contentText;
    private EditText inputText;
    private int value;
    private final String primeNo = " is Not a Prime";
    private final String primeYes = " is a Prime";
    private final String dividedBy = "Divided By : ";
    private final String oddNo = " is Even Number";
    private final String oddYes = " is Odd Number";
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.prime_activity);
//        InitElement();
//    }
    private View myView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.prime_activity, container, false);
        InitElement();
        return myView;
    }


    private void InitElement() {
        isPrimeBtn = (Button) myView.findViewById(R.id.isPrimeBtn);
        primeText = (TextView) myView.findViewById(R.id.primeResultText);
        oddText = (TextView) myView.findViewById(R.id.oddResultText);
        dividedText = (TextView) myView.findViewById(R.id.dividedResultText);
        inputText = (EditText) myView.findViewById(R.id.numberInput);
        contentText = (TextView) myView.findViewById(R.id.contentText);
        isPrimeBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.isPrimeBtn)
        {
            String valueString = inputText.getText().toString();
            value = Integer.parseInt(valueString);
            performIsPrime();
            performIsOdd();
            performDividedBy();
            performCrawling();
        }
    }

    private void performCrawling()
    {
        WebCrawler crawler = new WebCrawler(value);
        String content = crawler.getContent();
        contentText.setText(content);
    }

    private void performIsPrime()
    {
        if(PrimeNumber.isPrime(value))
        {
            primeText.setText(value + primeYes);
        }
        else
        {
            primeText.setText(value + primeNo);
        }
    }

    private void performIsOdd()
    {
        if(PrimeNumber.isOdd(value))
        {
            oddText.setText(value + oddYes);
        }
        else
        {
            oddText.setText(value + oddNo);
        }
    }

    private void performDividedBy()
    {
        int[] numList = PrimeNumber.dividedBy(value);
        StringBuilder numAdderString = new StringBuilder(dividedBy);
        int index = 0;
        for(int n : numList)
        {
            if(index != 0)
            {
                numAdderString.append(", ");
            }
            numAdderString.append(n);
            index++;
        }

        dividedText.setText(numAdderString.toString());
    }
}
