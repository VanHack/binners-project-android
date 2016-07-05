package ca.com.androidbinnersproject.activities.pickup;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ca.com.androidbinnersproject.R;
import ca.com.androidbinnersproject.models.Pickup;
import ca.com.androidbinnersproject.util.Logger;
import ca.com.androidbinnersproject.util.Util;

public class PickupBottlesFragment extends PickupBaseFragment {

	private Spinner spnNumberOfCans;

	public static final int PictureRequestCode = 1;

	private static final String PictureFolder = "BinnersApp";

	private List<String> listCansInfo;

	public PickupBottlesFragment() {
	}

	public static PickupBaseFragment newInstance(Context context, Pickup pickupModel) {
		PickupBaseFragment fragment = new PickupBottlesFragment();
		fragment.setPickupModel(pickupModel);
		fragment.setTitle(Util.getStringResource(context, R.string.pickup_activity_title_bottles));

		return fragment;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		listCansInfo = new ArrayList<>();

		fillCansList();
	}

	private void fillCansList() {
		listCansInfo.add("15 - 25 (about 2 grocery bags)");
		listCansInfo.add("25 - 35 (about 4 grocery bags)");
		listCansInfo.add("35 - 50 (1/2 garbage bag)");
		listCansInfo.add("50+ (1 black garbage bag)");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_pickup_bottles, container, false);

		FrameLayout takePictureButton = (FrameLayout) view.findViewById(R.id.pickup_bottles_takePicture);

		takePictureButton.setOnClickListener(new View.OnClickListener() {
			 @Override
			 public void onClick(View v) {
				 takePicture();
			 }
		});

		return view;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		spnNumberOfCans = (Spinner) view.findViewById(R.id.fragment_pickup_bottles_spnNumberOfCans);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, listCansInfo);

		spnNumberOfCans.setAdapter(adapter);
	}

	private void takePicture() {

		File mediaFile = getMediaFile();

		if(mediaFile == null)
			return;

		Uri fileUri = Uri.fromFile(mediaFile);

		Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

		startActivityForResult(cameraIntent, PictureRequestCode);
	}

	private File getMediaFile() {

		File imagesDir;
		String externalStorageState;

		if(!(externalStorageState = Environment.getExternalStorageState()).equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
			//TODO handle external storage not available
			Logger.Info("External storage not available, current state = " + externalStorageState);
			return null;
		}

		imagesDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), PictureFolder);

		if(!imagesDir.exists()) {
			if(!imagesDir.mkdirs()) {
				Logger.Error("Failed to create application pictures folder");
				return null;
			}
		}

		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CANADA).format(new Date());

		return new File(imagesDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
	}

	@Override
	protected boolean isValid() {
		return true;
	}
}
