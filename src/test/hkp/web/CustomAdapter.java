package test.hkp.web;

import java.util.*;

import android.content.*;
import android.view.*;
import android.widget.*;

public class CustomAdapter extends BaseAdapter {
	
	Context ctx;
	ArrayList<EmpDTO> list;
	int layout;
	LayoutInflater inf;

	public CustomAdapter(Context ctx, ArrayList<EmpDTO> list, int layout) {
		super();
		this.ctx = ctx;
		this.list = list;
		this.layout = layout;
		inf = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView == null) { convertView = inf.inflate(layout, null); }
		
		TextView txt_deptno = (TextView)convertView.findViewById(R.id.txt_deptno);
		TextView txt_dname = (TextView)convertView.findViewById(R.id.txt_dname);
		TextView txt_loc = (TextView)convertView.findViewById(R.id.txt_loc);
		
		txt_deptno.setText("deptno : "+list.get(position).getDeptno());
		txt_dname.setText("dname : "+list.get(position).getDname());
		txt_loc.setText("loc : "+list.get(position).getLoc());
		
		return convertView;
	}

}
