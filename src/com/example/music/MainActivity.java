package com.example.music;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.zzx.adapter.MusicListAdapter;
import com.zzx.domain.AppConstant;
import com.zzx.domain.Mp3Info;
import com.zzx.util.MediaUtil;

public class MainActivity extends Activity {
	private List<Mp3Info> mp3Infos = null;
	private ListView mMusiclist; // 音乐列表
	MusicListAdapter listAdapter; // 改为自定义列表适配器
	private int listPosition = 0; // 标识列表位置
	private TextView musicTitle;// 歌曲标题
	private ImageView musicAlbum; // 专辑封面
	private int currentTime; // 当前时间
	private int repeatState; // 循环标识
	private boolean isShuffle = false; // 随机播放
	private final int isCurrentRepeat = 1; // 单曲循环
	private final int isAllRepeat = 2; // 全部循环
	private final int isNoneRepeat = 3; // 无重复播放
	private boolean isNoneShuffle = true; // 顺序播放
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		musicTitle = (TextView) findViewById(R.id.music_title);
		//musicAlbum = (ImageView) findViewById(R.id.music_album);
		mp3Infos = MediaUtil.getMp3Infos(MainActivity.this); // 获取歌曲对象集合
		mMusiclist = (ListView) findViewById(R.id.music_list);
		listAdapter = new MusicListAdapter(this, mp3Infos);
		mMusiclist.setAdapter(listAdapter);
		mMusiclist.setOnItemClickListener(new MusicListItemClickListener());
		repeatState = isNoneRepeat; // 初始状态为无重复播放状态
	}
	/**
	 * 列表点击监听器
	 * @author wwj
	 */
	private class MusicListItemClickListener implements OnItemClickListener {
		/**
		 * 点击列表播放音乐
		 */
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			listPosition = position; // 获取列表点击的位置
			playMusic(listPosition); // 播放音乐
		}

	}
	/**
	 * 此方法通过传递列表点击位置来获取mp3Info对象
	 * 
	 * @param listPosition
	 */
	public void playMusic(int listPosition) {
		if (mp3Infos != null) {
			Mp3Info mp3Info = mp3Infos.get(listPosition);
			//musicTitle = (TextView) findViewById(R.id.music_title);
			//musicTitle.setText(mp3Info.getTitle()); // 这里显示标题
			//Bitmap bitmap = MediaUtil.getArtwork(this, mp3Info.getId(),
			//		mp3Info.getAlbumId(), true, true);// 获取专辑位图对象，为小图
			//musicAlbum.setImageBitmap(bitmap); // 这里显示专辑图片
			Intent intent = new Intent(MainActivity.this, PlayerActivity.class); // 定义Intent对象，跳转到PlayerActivity
			// 添加一系列要传递的数据
			System.out.println("**************"+mp3Info.getTitle());
			intent.putExtra("title", mp3Info.getTitle());
			intent.putExtra("url", mp3Info.getUrl());
			intent.putExtra("artist", mp3Info.getArtist());
			intent.putExtra("listPosition", listPosition);
			intent.putExtra("currentTime", currentTime);
			intent.putExtra("repeatState", repeatState);
			intent.putExtra("shuffleState", isShuffle);
			intent.putExtra("MSG", AppConstant.PlayerMsg.PLAY_MSG);
			startActivity(intent);
		}
	}
}
