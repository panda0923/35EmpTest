package test.hkp.web;

import java.io.*;
import java.util.*;

import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.*;
import org.apache.http.impl.client.*;
import org.apache.http.message.*;
import org.xmlpull.v1.*;
import android.app.Activity;
import android.os.Bundle;
import android.util.*;
import android.view.*;
import android.widget.*;

public class InsertActivity extends Activity {
	
	EditText edit_deptno,edit_dname,edit_loc;
	Button btn_insert;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_layout);
        
        edit_deptno = (EditText)findViewById(R.id.edit_deptno);
        edit_dname = (EditText)findViewById(R.id.edit_dname);
        edit_loc = (EditText)findViewById(R.id.edit_loc);
        
        btn_insert = (Button)findViewById(R.id.btn_insert);
        btn_insert.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				
				String deptno = edit_deptno.getText().toString();
				String dname = edit_dname.getText().toString();
				String loc = edit_loc.getText().toString();
				String requestURL = "http://(여기에서버IP주소입력):8080/99JSP_myEMP/insertdo2.jsp";
				InputStream is= requestGet(requestURL,deptno,dname,loc);
						
				finish();
			}
		});//end onClick()
        
    }//end onCreate
    
    public InputStream requestGet(String requestURL, String deptno, String dname, String loc) {
        
    	Log.i("xxx", "requestGet start");
    	try {
    		
    		//1.
        	HttpClient client = new DefaultHttpClient();
        	
        	//폼데이터 저장
        	List<NameValuePair> dataList = new ArrayList<NameValuePair>();
        	dataList.add(new BasicNameValuePair("deptno", deptno));
        	dataList.add(new BasicNameValuePair("dname", dname));
        	dataList.add(new BasicNameValuePair("loc", loc));
        	requestURL = requestURL + "?" + URLEncodedUtils.format(dataList, "UTF-8");
        	
        	HttpGet get = new HttpGet(requestURL);
        	
        	//2. 요청
        	HttpResponse response = client.execute(get);
    		
        	//3. 결과 받기
        	HttpEntity entity = response.getEntity();
        	InputStream is = entity.getContent();
        	return is;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }//end requestGet()
    
}//end class