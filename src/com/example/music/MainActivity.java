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
	private ListView mMusiclist; // �����б�
	MusicListAdapter listAdapter; // ��Ϊ�Զ����б�������
	private int listPosition = 0; // ��ʶ�б�λ��
	private TextView musicTitle;// ��������
	private ImageView musicAlbum; // ר������
	private int currentTime; // ��ǰʱ��
	private int repeatState; // ѭ����ʶ
	private boolean isShuffle = false; // �������
	private final int isCurrentRepeat = 1; // ����ѭ��
	private final int isAllRepeat = 2; // ȫ��ѭ��
	private final int isNoneRepeat = 3; // ���ظ�����
	private boolean isNoneShuffle = true; // ˳�򲥷�
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		musicTitle = (TextView) findViewById(R.id.music_title);
		//musicAlbum = (ImageView) findViewById(R.id.music_album);
		mp3Infos = MediaUtil.getMp3Infos(MainActivity.this); // ��ȡ�������󼯺�
		mMusiclist = (ListView) findViewById(R.id.music_list);
		listAdapter = new MusicListAdapter(this, mp3Infos);
		mMusiclist.setAdapter(listAdapter);
		mMusiclist.setOnItemClickListener(new MusicListItemClickListener());
		repeatState = isNoneRepeat; // ��ʼ״̬Ϊ���ظ�����״̬
	}
	/**
	 * �б���������
	 * @author wwj
	 */
	private class MusicListItemClickListener implements OnItemClickListener {
		/**
		 * ����б�������
		 */
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			listPosition = position; // ��ȡ�б�����λ��
			playMusic(listPosition); // ��������
		}

	}
	/**
	 * �˷���ͨ�������б���λ������ȡmp3Info����
	 * 
	 * @param listPosition
	 */
	public void playMusic(int listPosition) {
		if (mp3Infos != null) {
			Mp3Info mp3Info = mp3Infos.get(listPosition);
			//musicTitle = (TextView) findViewById(R.id.music_title);
			//musicTitle.setText(mp3Info.getTitle()); // ������ʾ����
			//Bitmap bitmap = MediaUtil.getArtwork(this, mp3Info.getId(),
			//		mp3Info.getAlbumId(), true, true);// ��ȡר��λͼ����ΪСͼ
			//musicAlbum.setImageBitmap(bitmap); // ������ʾר��ͼƬ
			Intent intent = new Intent(MainActivity.this, PlayerActivity.class); // ����Intent������ת��PlayerActivity
			// ���һϵ��Ҫ���ݵ�����
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
