package edu.gatech.seclass.caculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	private EditText Scr;
	private int NumberBf;
	private String Operation;
	private ButtonClickListener btnClick;
	private int idx;  // index of the each input 
	private String errInfo;
	
	final static int DIGIT = 1;
	final static int OPERATOR = 2; 
	final static int RESULT = 3; 
	final static int UNDEF = 0;
	private int order[] = {UNDEF, UNDEF, UNDEF};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Scr = (EditText)findViewById(R.id.editText1);
		Scr.setEnabled(false);
		btnClick=new ButtonClickListener();
		idx = 1; 
		errInfo = "Error";
		
		int idList[]={R.id.button0,R.id.button1,R.id.button2,R.id.button3,R.id.button4,R.id.button5,R.id.button6,R.id.button7,R.id.button8,R.id.button9,R.id.buttonAdd,R.id.buttonC,R.id.buttonEq,R.id.buttonMul,R.id.buttonSub};
		
		for(int id:idList){
			View v=(View)findViewById(id);
			v.setOnClickListener(btnClick);}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void updateOrder(int ORDER) {
		if (order[2] == ORDER && DIGIT == ORDER) { // if the last input is digits.
			return;
		} else {
			for (int i = 0; i < 2; i++) {
				order[i] = order[i + 1];
			}
			order[2] = ORDER;
		}
	}
	
	public void mMath(String str){
		NumberBf=Integer.parseInt(Scr.getText().toString());
		Operation=str;
		Scr.setText("0");
	}
	public void getKeyboard(String str){
		String ScrCurrent=Scr.getText().toString();
			if (ScrCurrent.equals("0"))
				ScrCurrent="";
			ScrCurrent+=str;
			Scr.setText(ScrCurrent);
			}
	public void mResult(){
		int NumberAf=Integer.parseInt(Scr.getText().toString());
		int result=0;
		if (Operation.equals("+")){
			result=NumberAf+NumberBf;
		}
		if (Operation.equals("-")){
			result=-(NumberAf-NumberBf);
		}	
		if (Operation.equals("*")){
			result=NumberAf*NumberBf;
		}
		Scr.setText(String.valueOf(result));
		
	}
	public class ButtonClickListener implements OnClickListener{		
		public void onClick(View v){			
			switch(v.getId()){
			case R.id.buttonC:
				Scr.setText("0");
			    NumberBf=0;
			    Operation="";
			    for(int i=0; i<3; i++){
			    	order[i] = UNDEF; 
			    }
			    break;
			    
			case R.id.buttonAdd:// the last input should be either digit or result
				if (RESULT == order[2] || DIGIT == order[2]){
					mMath("+");
					updateOrder(OPERATOR);  // update the input label
				}else{
					Scr.setText(errInfo);
				}					
				break;
				
			case R.id.buttonSub:// the last input should be either digit or result
				if (RESULT == order[2] || DIGIT == order[2]){
					mMath("-");
					updateOrder(OPERATOR);  // update the input label
				}else{
					Scr.setText(errInfo);
				}	
				break;
			case R.id.buttonMul: // the last input should be either digit or result
				if (RESULT == order[2] || DIGIT == order[2]){
					mMath("*");
					updateOrder(OPERATOR);  // update the input label
				}else{
					Scr.setText(errInfo);
				}	
				break;
			case R.id.buttonEq: // the first should be either digit or result, the 2nd must operator, and the 3rd must be digit
				if ((DIGIT == order[0] || RESULT == order[0])  && DIGIT == order[2] && OPERATOR == order[1]){
					mResult();
					updateOrder(RESULT);   // update the input label
				}else{
					Scr.setText(errInfo);
				}	
				
				break;
			default:
				String numb=((Button) v).getText().toString();
				getKeyboard(numb);
				updateOrder(DIGIT);
				break;
			}
		
		}
	}
}

