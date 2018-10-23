package com.example.whgml.sejongapps.Model;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.whgml.sejongapps.Helper.PrimeNumber;
import com.example.whgml.sejongapps.Helper.WebCrawler;
import com.example.whgml.sejongapps.R;

import org.w3c.dom.Text;

public class PrimeActivity extends AppCompatActivity implements View.OnClickListener {

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prime_activity);
        InitElement();
    }

    private void InitElement() {
        isPrimeBtn = (Button) findViewById(R.id.isPrimeBtn);
        primeText = (TextView) findViewById(R.id.primeResultText);
        oddText = (TextView) findViewById(R.id.oddResultText);
        dividedText = (TextView) findViewById(R.id.dividedResultText);
        inputText = (EditText) findViewById(R.id.numberInput);
        contentText = (TextView) findViewById(R.id.contentText);
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
