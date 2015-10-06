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
import android.content.*;
import android.os.Bundle;
import android.util.*;
import android.view.*;
import android.widget.*;

public class EmpMainActivity extends Activity {
	
	Button btn;
	ListView lv;
	
	CustomAdapter adapter;
	
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        lv = (ListView)findViewById(R.id.listView1);
        
        
        btn = (Button)findViewById(R.id.button1);
        btn.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				
				Intent intent = new Intent(getApplicationContext(), InsertActivity.class);
				startActivity(intent);
			}
		});//end onClick()
        
        
    }//end onCreate
    
    public InputStream requestGet(String requestURL) {
    
    	Log.i("xxx", "requestGet start");
    	try {
    		
    		//1.
        	HttpClient client = new DefaultHttpClient();
        	
        	///////////////////////////////////////////
        	
//        	requestURL = requestURL + "?key=aaa&key2=홍길동";
//        	//폼데이터 저장
        	List<NameValuePair> list = new ArrayList<NameValuePair>();
//        	list.add(new BasicNameValuePair("key", "aaa"));
//        	list.add(new BasicNameValuePair("key1", "bbb"));
//        	list.add(new BasicNameValuePair("key2", "홍길동"));
//        	list.add(new BasicNameValuePair("key3", "ddd"));
//        	list.add(new BasicNameValuePair("key4", "eee"));
//        	requestURL = requestURL + "?" + URLEncodedUtils.format(list, "UTF-8");
        	
        	///////////////////////////////////////////
        	
        	
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
    
    public String streamToString(InputStream is) {
    	
    	StringBuffer buffer = new StringBuffer();
    	try {
			
    		BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
    		String str = reader.readLine();
    		while(str != null) {
    			buffer.append(str+"\n");
    			str = reader.readLine();
    		}
    		reader.close();
    		
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return buffer.toString();
    }//end streamToString()
    
    
    public ArrayList<EmpDTO> getXML(InputStream is) {
    	ArrayList<EmpDTO> list = new ArrayList<EmpDTO>();
    	Log.i("xxx", "getXML start!");
    	
    	try {
    		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
    		XmlPullParser parser = factory.newPullParser();
			
			parser.setInput(is, "UTF-8");
			
			int eventType = parser.getEventType();
			
			EmpDTO dto = null;
			
			while( eventType != XmlPullParser.END_DOCUMENT) {
				switch(eventType) {
				
				case XmlPullParser.START_TAG:
					
					String startTag = parser.getName();
					if(startTag.equals("record")){ dto = new EmpDTO(); }
					
					if(dto !=null ) {
						
						if(startTag.equals("deptno")){ dto.setDeptno(Integer.parseInt(parser.nextText())); }
						if(startTag.equals("dname")){ dto.setDname(parser.nextText()); }
						if(startTag.equals("loc")){ dto.setLoc(parser.nextText()); }
					} else { Log.i("xxx", "dto = null"); }
					break;
					
				case XmlPullParser.END_TAG:
					
					String endTag = parser.getName();
					if(endTag.equals("record")){ 

						list.add(dto);
					}
				}//end switch
				
				eventType = parser.next();
				
			}//end while
			
			 for( EmpDTO xx : list){
		        	Log.i("xxx",xx.getDeptno()+" "+xx.getDname()+" "+xx.getLoc());
		        }
			 
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException ie) {
			// TODO Auto-generated catch block
			ie.printStackTrace();
		}
    	return list;
    }

	@Override
	protected void onResume() {
		super.onResume();
		String requestURL = "http://(여기에서버IP주소입력):8080/99JSP_myEMP/select2.jsp";
		InputStream is = requestGet(requestURL);
//		String data = streamToString(is);
//		Log.i("xxx", data);
		ArrayList<EmpDTO> list = getXML(is);
		adapter = new CustomAdapter(getApplicationContext(),list,R.layout.custom_list);
        lv.setAdapter(adapter);
	}
    
}//end class