package com.example.myphonenumber.activity;
 
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Range;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.IntRange;
import androidx.annotation.Nullable;

import com.example.myphonenumber.Adapter.MessageBoxListAdapter;
import com.example.myphonenumber.entity.MessageBean;
import com.example.myphonenumber.R;

public class MessageBoxList extends Activity {
	private ListView talkView;
	private List<MessageBean> messages = null;
	private AsyncQueryHandler asyncQuery;
	private String address;
	private SimpleDateFormat sdf;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message_list_view);
		sdf = new SimpleDateFormat("MM-dd HH:mm");

//		Bundle bundle = getIntent().getExtras();
//		MyMap serializableMap = (MyMap) bundle.get("message");
//		Map<String, String> map = serializableMap.getMap();
		String threadid = getIntent().getStringExtra("threadid");
		init(threadid);
		Log.v("messageBoxList",messages.size()+"");
	}
 
	private void init(String thread) {
		asyncQuery = new MessageAsynQueryHandler(getContentResolver());
		talkView = (ListView) findViewById(R.id.message_list);
		messages = new ArrayList<MessageBean>();
		Uri uri = Uri.parse("content://sms");
		String[] projection = new String[] { "date", "address", "person", "body", "type" }; // 查询的列
		asyncQuery.startQuery(0, null, uri, projection, "thread_id = " + thread, null, "date asc");

	}
 
	/**
	 * 异步查询数据库的类
	 * 查找对应用户的消息对话，
	 */
	private class MessageAsynQueryHandler extends AsyncQueryHandler {
 
		public MessageAsynQueryHandler(ContentResolver cr) {
			super(cr);
		}
		@SuppressLint("Range")//cursor 会报出value > 0 的错误 ，对于cursor查找应该对应的index必须大于等于0，但为了规范，是通过getColumIndex查找，报出错误，所以加上警告提示
		@Override
		protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
			if (cursor != null && cursor.getCount() > 0) {
				cursor.moveToFirst();
				for (int i = 0; i < cursor.getCount(); i++) {
					cursor.moveToPosition(i);
					String date = sdf.format(new Date(cursor.getLong(cursor.getColumnIndex("date"))));
					if (cursor.getInt(cursor.getColumnIndex("type")) == 1) {// 他说的信息
						MessageBean d = new MessageBean(
								cursor.getString(cursor.getColumnIndex("address")),
								date,
								cursor.getString(cursor.getColumnIndex("body")),R.layout.message_list_say_he_item);
						messages.add(d);
					} else { // 我说的信息
						MessageBean d = new MessageBean(cursor.getString(cursor.getColumnIndex("address")),date,cursor.getString(cursor.getColumnIndex("body")), R.layout.message_list_say_me_item);
						messages.add(d);
					}
				}
				if (messages.size() > 0) {
					talkView.setAdapter(new MessageBoxListAdapter(
							MessageBoxList.this, messages));
					talkView.setDivider(null);
					talkView.setSelection(messages.size());
				} else {
					Log.v("messageBoxList","没有数据显示");
					Toast.makeText(MessageBoxList.this, "没有短信进行操作",Toast.LENGTH_SHORT).show();
				}

			}
			Log.d("messageBoxList",cursor.getCount()+"");
			super.onQueryComplete(token, cookie, cursor);
		}
	}
	//自定义类
	public class MyMap implements Serializable {

		private Map<String, String> map;
		private static  final long serialVersionUID = 1L;
		public Map<String,String> getMap() {
			return map;
		}

		public void setMap(Map<String, String> map) {
			this.map = map;
		}
	}
}